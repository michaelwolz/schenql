package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryCondition;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.Condition;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class ConferenceConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitConferenceCondition(SchenqlParser.ConferenceConditionContext ctx, Query sqlQuery) {
        if (ctx.ACRONYM() != null) {
            BooleanCondition condition = new BooleanCondition(
                    "conference",
                    "acronym",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()
            );
            sqlQuery.addCondition(condition);
        }

//        if (ctx.ABOUT() != null) {
//            sqlQuery.addJoin(
//                    "publication",
//                    "conference_dblpKey",
//                    "conference",
//                    "dblpKey"
//            );
//            sqlQuery.addJoin(
//                    "publication_has_keyword",
//                    "dblpKey",
//                    "publication",
//                    "dblpKey"
//            );
//
//            KeywordVisitor kv = new KeywordVisitor();
//            Query subQuery = new Query();
//            SubQueryCondition subQueryCondition = new SubQueryCondition(
//                    "publication_has_keyword",
//                    "keyword",
//                    subQuery
//            );
//        }

        if (ctx.AFTER() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.GT,
                            ctx.YEAR().getText()
                    )
            );
        }

        if (ctx.BEFORE() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.LT,
                            ctx.YEAR().getText()
                    )
            );
        }

        if (ctx.IN_YEAR() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "conference_dblpKey",
                    "conference",
                    "dblpKey"
            );
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.EQUALS,
                            ctx.YEAR().getText()
                    )
            );
        }

//        if (ctx.OF() != null) {
//            sqlQuery.addJoin(
//                    "publication",
//                    "conference_dblpKey",
//                    "conference",
//                    "dblpKey"
//            );
//
//            PublicationVisitor pv = new PublicationVisitor();
//            Query subQuery = new Query();
//            SubQueryCondition subQueryCondition = new SubQueryCondition(
//                    "conference",
//                    "dblpKey",
//                    subQuery
//            );
//        }
    }
}
