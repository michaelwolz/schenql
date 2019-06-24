package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;

class PublicationVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPublication(SchenqlParser.PublicationContext ctx, Query sqlQuery) {
        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            pqv.visitPublicationQuery(ctx.publicationQuery(), sqlQuery);
        } else if (ctx.DBLP_KEY() != null) {
            sqlQuery.distinct();
            sqlQuery.addSelect("publication", "title");
            sqlQuery.addFrom("publication");
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "dblpKey",
                            BooleanOperator.EQUALS,
                            ctx.DBLP_KEY().getText()
                    )
            );
        } else {
//            // TODO: This is not nice
//            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.JournalConditionContext) {
//                return "SELECT `publication`.`journal_dblpKey` FROM `publication` WHERE " +
//                        "`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText());
//            }
//            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.ConferenceConditionContext) {
//                return "SELECT `publication`.`conference_dblpKey` FROM `publication` WHERE " +
//                        "`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText());
//            }
//            return "SELECT `publication`.`dblpKey` FROM `publication` WHERE " +
//                    "`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText());
//
//            // Is it necessary to have a full text match when using titles as literal?
///*            return "SELECT `fulltext_match`.`dblpKey` FROM (SELECT `publication`.`dblpKey`, " +
//                    "MATCH(`publication`.`title`) AGAINST(\"" + ctx.STRING().getText() + "\") as `relevance`" +
//                    "FROM `publication`" +
//                    "WHERE MATCH(`publication`.`title`) AGAINST(\"" + ctx.STRING().getText() + "\")" +
//                    "ORDER BY `relevance` DESC) as `fulltext_match`";*/
        }
    }
}
