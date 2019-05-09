package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class InstitutionQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitJournalQuery(SchenqlParser.JournalQueryContext ctx) {
        return super.visitJournalQuery(ctx);
    }
}
