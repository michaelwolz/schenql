package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

public class JournalVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitJournal(SchenqlParser.JournalContext ctx) {
        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            return jqv.visitJournalQuery(ctx.journalQuery());
        } else {
            return "SELECT `journal`.`dblpKey` FROM `journal` "
                    + "JOIN `journal_name` " +
                    "ON `journal_name`.`journalKey` = `journal`.`dblpKey` " +
                    "WHERE `journal_name`.`name` " +
                    Helper.sqlStringComparison(ctx.STRING().getText());
        }
    }
}
