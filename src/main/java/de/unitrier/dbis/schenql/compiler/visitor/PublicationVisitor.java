package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class PublicationVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublication(SchenqlParser.PublicationContext ctx) {
        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            return "(" + pqv.visitPublicationQuery(ctx.publicationQuery()) + ")";
        } else {
            return ctx.STRING().getText();
        }
    }
}
