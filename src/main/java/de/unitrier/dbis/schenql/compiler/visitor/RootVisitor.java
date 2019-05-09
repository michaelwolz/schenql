package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Schenql;

public class RootVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitRoot(SchenqlParser.RootContext ctx) {
        String query = "";

        if (ctx.query() != null) {
            QueryVisitor qv = new QueryVisitor();
            query += qv.visitQuery(ctx.query());

            // Limit output
            if (ctx.query().LIMIT() != null) {
                query += " LIMIT " + ctx.query().NUMBER();
            } else {
                query += " LIMIT " + Schenql.DEFAULT_QUERY_LIMIT;
            }
        }

        if (ctx.aggregateFunction() != null) {
            AggregateFunctionVisitor av = new AggregateFunctionVisitor();
            query += av.visitAggregateFunction(ctx.aggregateFunction());
        }

        query += ";";
        return query;
    }
}
