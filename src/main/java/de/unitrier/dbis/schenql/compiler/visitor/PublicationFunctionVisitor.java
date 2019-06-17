package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class PublicationFunctionVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublicationFunction(SchenqlParser.PublicationFunctionContext ctx) {
        if (ctx.MOST_CITED() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            if (!(ctx.getParent().getParent().getRuleContext() instanceof SchenqlParser.QueryContext)) {
                return "SELECT * FROM (SELECT DISTINCT `sub`.`dblpKey` FROM (" +
                        pqv.visitPublicationQuery(ctx.publicationQuery(), new String[]{
                                "`publication`.`dblpKey`"
                        }) +
                        ") as `sub` " +
                        "JOIN `publication_references` " +
                        "ON `publication_references`.`pub2_id` = `sub`.`dblpKey` " +
                        "GROUP BY `sub`.`dblpKey` ORDER BY COUNT(`sub`.`dblpKey`) DESC LIMIT 5) as optim";
            }

            return "SELECT `sub`.`title`, `sub`.`year`, COUNT(`sub`.`dblpKey`) as `citations` FROM (" +
                    pqv.visitPublicationQuery(ctx.publicationQuery(), new String[]{
                            "`publication`.`dblpKey`",
                            "`publication`.`title`",
                            "`publication`.`year`",
                    }) +
                    ") as `sub` " +
                    "JOIN `publication_references` " +
                    "ON `publication_references`.`pub2_id` = `sub`.`dblpKey` " +
                    "GROUP BY `sub`.`dblpKey` ORDER BY `citations` DESC";
        }
        return null;
    }
}
