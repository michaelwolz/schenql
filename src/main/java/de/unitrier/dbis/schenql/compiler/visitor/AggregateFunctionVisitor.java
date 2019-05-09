package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

import javax.management.Query;

public class AggregateFunctionVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitAggregateFunction(SchenqlParser.AggregateFunctionContext ctx) {
        if (ctx.COUNT() != null) {
            QueryVisitor qv = new QueryVisitor();
            return "SELECT COUNT(*) as \"Count\" FROM (" + qv.visitQuery(ctx.query()) + ") as count";
        }
        return null;
    }
}
