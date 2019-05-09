package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

public class InstitutionLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitJournalLimitation(SchenqlParser.JournalLimitationContext ctx) {
        return super.visitJournalLimitation(ctx);
    }
}
