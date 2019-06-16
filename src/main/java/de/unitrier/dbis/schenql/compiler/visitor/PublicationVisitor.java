package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

public class PublicationVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublication(SchenqlParser.PublicationContext ctx) {
        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.PublicationLimitationContext
                    || ctx.getParent().getRuleContext() instanceof SchenqlParser.PersonLimitationContext) {
                return pqv.visitPublicationQuery(ctx.publicationQuery(), new String[]{"`publication`.`dblpKey`"});
            }
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.JournalLimitationContext) {
                return pqv.visitPublicationQuery(ctx.publicationQuery(),  new String[]{"`publication`.`journal_dblpKey`"});
            }
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.ConferenceLimitationContext) {
                return pqv.visitPublicationQuery(ctx.publicationQuery(),  new String[]{"`publication`.`conference_dblpKey`"});
            }
            return pqv.visitPublicationQuery(ctx.publicationQuery());
        } else if (ctx.DBLP_KEY() != null) {
            return ctx.DBLP_KEY().getText();
        } else {
//            return "SELECT `publication`.`dblpKey` FROM `publication` WHERE " +
//                    "MATCH(`publication`.`title`) AGAINST(\"" + ctx.STRING().getText() + "\")";
            return "SELECT `publication`.`dblpKey` FROM `publication` WHERE " +
                    "`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText());
        }
    }
}
