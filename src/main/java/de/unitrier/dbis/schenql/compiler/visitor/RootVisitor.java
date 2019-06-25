package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Schenql;
import de.unitrier.dbis.sqlquerybuilder.Query;

public class RootVisitor extends SchenqlParserBaseVisitor<Query> {
    @Override
    public Query visitRoot(SchenqlParser.RootContext ctx) {
        Query sqlQuery = new Query();

        if (ctx.query() != null) {
            QueryVisitor qv = new QueryVisitor();
            qv.visitQuery(ctx.query(), sqlQuery);

            // Limit output
            if (ctx.query().LIMIT() != null)
                sqlQuery.addLimit(Integer.parseInt(ctx.query().NUMBER().getText()));
            else if (sqlQuery.getLimit() == null)
                sqlQuery.addLimit(Schenql.DEFAULT_QUERY_LIMIT);
        }

        if (ctx.functionCall() != null) {
            FunctionCallVisitor av = new FunctionCallVisitor();
            av.visitFunctionCall(ctx.functionCall(), sqlQuery);
        }

        if (ctx.attributeOf() != null) {
            AttributeOfVisitor aov = new AttributeOfVisitor();
            aov.visitAttributeOf(ctx.attributeOf());

            // Limit output
            if (ctx.attributeOf().query().LIMIT() != null) {
                sqlQuery.addLimit(Integer.parseInt(ctx.attributeOf().query().NUMBER().getText()));
            } else {
                sqlQuery.addLimit(Schenql.DEFAULT_QUERY_LIMIT);
            }
        }

        return sqlQuery;
    }
}
