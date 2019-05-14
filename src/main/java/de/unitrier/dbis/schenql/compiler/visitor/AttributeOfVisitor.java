package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class AttributeOfVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitAttributeOf(SchenqlParser.AttributeOfContext ctx) {
        QueryVisitor qv = new QueryVisitor();
        return qv.visitQuery(ctx.query(), new String[]{ctx.STRING().getText()});
    }
}
