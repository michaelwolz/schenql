package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Arrays;

class JournalQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitJournalQuery(SchenqlParser.JournalQueryContext ctx, Query sqlQuery) {
        if (ctx.JOURNAL() != null) {
            if (sqlQuery.selectIsEmpty()) {
                Arrays.stream(DefaultFields.journal).forEach(selectField -> {
                    sqlQuery.addSelect("journal", selectField);
                });
            }

            sqlQuery.distinct();
            sqlQuery.addFrom("journal");

            JournalConditionVisitor jcv = new JournalConditionVisitor();
            ctx.journalCondition()
                    .forEach(conditionCtx -> {
                        jcv.visitJournalCondition(conditionCtx, sqlQuery);
                    });
        }
    }
}
