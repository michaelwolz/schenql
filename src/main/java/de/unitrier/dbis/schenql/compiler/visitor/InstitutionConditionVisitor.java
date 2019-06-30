package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.Condition;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class InstitutionConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitInstitutionCondition(SchenqlParser.InstitutionConditionContext ctx, Query sqlQuery) {
        Condition condition = null;

        if (ctx.NAMED() != null) {
            sqlQuery.addJoin(
                    "institution_name",
                    "institutionKey",
                    "institution",
                    "key"
            );
            condition = new BooleanCondition(
                    "institution_name",
                    "name",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()

            );
        } else if (ctx.CITY() != null) {
            condition = new BooleanCondition(
                    "institution",
                    "city",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()

            );
        } else if (ctx.COUNTRY() != null) {
            condition = new BooleanCondition(
                    "institution",
                    "country",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()

            );
        } else if (ctx.MEMBERS() != null) {
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
            condition = new SubQueryCondition(
                    "person",
                    "dblpKey",
                    subQuery

            );
        }

        if (condition != null) {
            if (ctx.logicalOperator() != null) {
                if (ctx.logicalOperator().or() != null) {
                    condition.or();
                }
                if (ctx.logicalOperator().not() != null) {
                    condition.negate();
                }
            }
            sqlQuery.addCondition(condition);
        }
    }
}
