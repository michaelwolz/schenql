package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class InstitutionConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitInstitutionCondition(SchenqlParser.InstitutionConditionContext ctx, Query sqlQuery) {
        if (ctx.NAMED() != null) {
            sqlQuery.addJoin(
                    "institution_name",
                    "institutionKey",
                    "institution",
                    "key"
            );
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "institution_name",
                            "name",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }

        if (ctx.CITY() != null) {
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "institution",
                            "city",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }

        if (ctx.COUNTRY() != null) {
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "institution",
                            "country",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }

        if (ctx.MEMBERS() != null) {
            sqlQuery.addJoin(
                    "person_works_for_institution",
                    "institutionKey",
                    "institution",
                    "key"
            );
            sqlQuery.addJoin(
                    "person",
                    "dblpKey",
                    "person_works_for_institution",
                    "personKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            PersonVisitor pv = new PersonVisitor();
            sqlQuery.addCondition(
                    new SubQueryCondition(
                            "person",
                            "dblpKey",
                            subQuery
                    )
            );
        }
    }
}
