package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class RootVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitRoot(SchenqlParser.RootContext ctx) {
        String query;
        QueryVisitor qv = new QueryVisitor();
        query = qv.visitQuery(ctx.query());
        return query;
    }
}
