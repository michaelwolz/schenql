package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

public class PersonLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitPersonLimitation(SchenqlParser.PersonLimitationContext ctx) {
        return super.visitPersonLimitation(ctx);
    }
}
