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
            String limit = Integer.toString(Schenql.DEFAULT_QUERY_LIMIT);

            if (ctx.query().LIMIT() != null) {
                limit = ctx.query().NUMBER().getText();
            }
            query += " LIMIT " + limit;
        }

        if (ctx.functionCall() != null) {
            FunctionCallVisitor av = new FunctionCallVisitor();
            query += av.visitFunctionCall(ctx.functionCall());
        }

        if (ctx.attributeOf() != null) {
            AttributeOfVisitor aov = new AttributeOfVisitor();
            query += aov.visitAttributeOf(ctx.attributeOf());

            // Limit output
            if (ctx.attributeOf().query().LIMIT() != null) {
                query += " LIMIT " + ctx.attributeOf().query().NUMBER();
            } else {
                query += " LIMIT " + Schenql.DEFAULT_QUERY_LIMIT;
            }
        }

        query += ";";
        return query;
    }
}
