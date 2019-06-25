package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;

import static de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator.EQUALS;
import static de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator.SOUNDSLIKE;

class PersonVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPerson(SchenqlParser.PersonContext ctx, Query sqlQuery) {
        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            pqv.visitPersonQuery(ctx.personQuery(), sqlQuery);
        } else if (ctx.DBLP_KEY() != null) {
            sqlQuery.distinct();
            sqlQuery.addFrom("person");
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "person",
                            "dblpKey",
                            EQUALS,
                            ctx.DBLP_KEY().getText()
                    )
            );
        } else if (ctx.ORCID_VALUE() != null) {
            sqlQuery.addFrom("person");
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "person",
                            "orcid",
                            EQUALS,
                            ctx.ORCID_VALUE().getText()
                    )
            );
        } else {
            sqlQuery.addFrom("person");
            sqlQuery.addJoin(
                    "person_names",
                    "personKey",
                    "person",
                    "dblpKey"
            );

            BooleanCondition cond = new BooleanCondition("person_names", "name");
            cond.setConditionValue(ctx.STRING().getText());
            if (ctx.TILDE() != null) {
                cond.setOperator(SOUNDSLIKE);
            } else {
                cond.setOperator(EQUALS);
            }
            sqlQuery.addCondition(cond);
        }
    }
}
