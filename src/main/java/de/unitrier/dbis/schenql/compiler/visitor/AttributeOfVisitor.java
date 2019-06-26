package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;

class AttributeOfVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitAttributeOf(SchenqlParser.AttributeOfContext ctx, Query sqlQuery) {
        QueryVisitor qv = new QueryVisitor();
        ctx.STRING().forEach(attribute -> sqlQuery.addSelect(attribute.getText()));
        qv.visitQuery(ctx.query(), sqlQuery);
    }
}
