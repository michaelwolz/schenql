package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class PublicationAggregateFunctionVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublicationAggregateFunction(SchenqlParser.PublicationAggregateFunctionContext ctx) {
        if (ctx.MOST_CITED() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
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
