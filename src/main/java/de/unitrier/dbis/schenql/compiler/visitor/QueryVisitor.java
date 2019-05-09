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

        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            query += pqv.visitPersonQuery(ctx.personQuery());
        }

        return query;
    }
}
