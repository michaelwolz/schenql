package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.AggregateFunction;
import de.unitrier.dbis.sqlquerybuilder.OrderBy;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.InCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class PersonFunctionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPersonFunction(SchenqlParser.PersonFunctionContext ctx, Query sqlQuery) {
        if (ctx.COAUTHORS() != null) {
            if ((ctx.getParent().getParent().getRuleContext() instanceof SchenqlParser.QueryContext)) {
                sqlQuery.addSelect("person", "primaryName");
                AggregateFunction score = new AggregateFunction("person", "primaryName", "COUNT");
                score.setAlias("score");
                sqlQuery.addSelect(score);
            }
            sqlQuery.addFrom("person");

            sqlQuery.addJoin(
                    "person_authored_publication",
                    "personKey",
                    "person",
                    "dblpKey"
            );

            Query personSubQuery = new Query();
            personSubQuery.addSelect("person", "dblpKey");
            PersonVisitor pqv = new PersonVisitor();
            pqv.visitPerson(ctx.person(), personSubQuery);

            Query publicationSubQuery = new Query();
            publicationSubQuery.addSelect("person_authored_publication", "publicationKey");
            publicationSubQuery.addFrom("person_authored_publication");
            publicationSubQuery.addCondition(
                    new SubQueryCondition(
                            "person_authored_publication",
                            "personKey",
                            personSubQuery
                    )
            );

            sqlQuery.addCondition(
                    new SubQueryCondition(
                            "person_authored_publication",
                            "publicationKey",
                            publicationSubQuery
                    )
            );

            // Removing the queried person(s)
            SubQueryCondition sqc = new SubQueryCondition("person", "dblpKey", personSubQuery);
            sqc.negate();
            sqlQuery.addCondition(sqc);

            // Weighting the result
            if ((ctx.getParent().getParent().getRuleContext() instanceof SchenqlParser.QueryContext)) {
                sqlQuery.addGroupBy("person", "dblpKey");
                OrderBy ob = new OrderBy("score");
                ob.setSortOrder("DESC");
                sqlQuery.addOrderBy(ob);
            }
        }
    }
}
