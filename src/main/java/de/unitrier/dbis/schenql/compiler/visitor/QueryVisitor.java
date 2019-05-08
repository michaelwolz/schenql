package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class QueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitQuery(SchenqlParser.QueryContext ctx) {
        String query = "";

        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            query += pqv.visitPublicationQuery(ctx.publicationQuery());
        }

        // Limit output
        if (ctx.LIMIT() != null) {
            query += " LIMIT " + ctx.NUMBER();
        }

        query += ";";
        return query;
    }
}
