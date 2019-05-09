package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class JournalVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitJournal(SchenqlParser.JournalContext ctx) {
        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            return "(" + jqv.visitJournalQuery(ctx.journalQuery()) + ")";
        } else {
            return ctx.STRING().getText();
        }
    }
}
