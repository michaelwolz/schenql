package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

public class PublicationVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublication(SchenqlParser.PublicationContext ctx) {
        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.PublicationConditionContext
                    || ctx.getParent().getRuleContext() instanceof SchenqlParser.PersonConditionContext) {
                return pqv.visitPublicationQuery(ctx.publicationQuery(), new String[]{"`publication`.`dblpKey`"});
            }
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.JournalConditionContext) {
                return pqv.visitPublicationQuery(ctx.publicationQuery(), new String[]{"`publication`.`journal_dblpKey`"});
            }
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.ConferenceConditionContext) {
                return pqv.visitPublicationQuery(ctx.publicationQuery(), new String[]{"`publication`.`conference_dblpKey`"});
            }
            return pqv.visitPublicationQuery(ctx.publicationQuery());
        } else if (ctx.DBLP_KEY() != null) {
            return ctx.DBLP_KEY().getText();
        } else {
            // TODO: This is not nice
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.JournalConditionContext) {
                return "SELECT `publication`.`journal_dblpKey` FROM `publication` WHERE " +
                        "`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText());
            }
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.ConferenceConditionContext) {
                return "SELECT `publication`.`conference_dblpKey` FROM `publication` WHERE " +
                        "`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText());
            }
            return "SELECT `publication`.`dblpKey` FROM `publication` WHERE " +
                    "`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText());

            // Is it necessary to have a full text match when using titles as literal?
/*            return "SELECT `fulltext_match`.`dblpKey` FROM (SELECT `publication`.`dblpKey`, " +
                    "MATCH(`publication`.`title`) AGAINST(\"" + ctx.STRING().getText() + "\") as `relevance`" +
                    "FROM `publication`" +
                    "WHERE MATCH(`publication`.`title`) AGAINST(\"" + ctx.STRING().getText() + "\")" +
                    "ORDER BY `relevance` DESC) as `fulltext_match`";*/
        }
    }
}
