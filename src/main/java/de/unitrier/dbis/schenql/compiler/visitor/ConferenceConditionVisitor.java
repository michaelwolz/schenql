package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.Condition;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class ConferenceConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitConferenceCondition(SchenqlParser.ConferenceConditionContext ctx, Query sqlQuery) {
        Condition condition = null;

        if (ctx.ACRONYM() != null) {
            condition = new BooleanCondition(
                    "conference",
                    "acronym",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()
            );
        } else if (ctx.ABOUT() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );
            sqlQuery.addJoin(
                    "publication_has_keyword",
                    "dblpKey",
                    "publication",
                    "dblpKey"
            );

            KeywordVisitor kv = new KeywordVisitor();
            Query subQuery = new Query();
            kv.visitKeyword(ctx.keyword(), subQuery);
            condition = new SubQueryCondition(
                    "publication_has_keyword",
                    "keyword",
                    subQuery
            );
        } else if (ctx.AFTER() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );
            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.GT,
                    ctx.YEAR().getText()
            );
        } else if (ctx.BEFORE() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );
            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.LT,
                    ctx.YEAR().getText()
            );
        } else if (ctx.IN_YEAR() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );
            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.EQUALS,
                    ctx.YEAR().getText()
            );
        } else if (ctx.OF() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "conference_dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            condition = new SubQueryCondition(
                    "conference",
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
