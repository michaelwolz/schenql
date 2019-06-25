package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.AggregateFunction;
import de.unitrier.dbis.sqlquerybuilder.Query;

class FunctionCallVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitFunctionCall(SchenqlParser.FunctionCallContext ctx, Query sqlQuery) {
        if (ctx.COUNT() != null) {
            AggregateFunction af = new AggregateFunction(
                    "*",
                    "COUNT"
            );
            af.setAlias("count");
            sqlQuery.addSelect(af);

            Query subQuery = new Query();
            QueryVisitor qv = new QueryVisitor();
            qv.visitQuery(ctx.query(), subQuery);

            sqlQuery.addFrom(subQuery, "sub");
        }
    }
}
