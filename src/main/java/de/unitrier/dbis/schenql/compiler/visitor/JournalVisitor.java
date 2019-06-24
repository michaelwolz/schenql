package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;

class JournalVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitJournal(SchenqlParser.JournalContext ctx, Query sqlQuery) {
        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            jqv.visitJournalQuery(ctx.journalQuery(), sqlQuery);
        } else {
            sqlQuery.addFrom("journal");
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "journal",
                            "acronym",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }
    }
}
