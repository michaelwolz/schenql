package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.ExtendedFields;
import de.unitrier.dbis.schenql.compiler.Schenql;
import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Arrays;

class JournalQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitJournalQuery(SchenqlParser.JournalQueryContext ctx, Query sqlQuery) {
        if (ctx.JOURNAL() != null) {
            if (sqlQuery.selectIsEmpty())
                if (Schenql.apiMode)
                    Arrays.stream(ExtendedFields.journal).forEach(selectField ->
                            sqlQuery.addSelect("journal", selectField));
                else
                    Arrays.stream(DefaultFields.journal).forEach(selectField ->
                            sqlQuery.addSelect("journal", selectField));
	    
	    // Adding journal name for as default field hardcoded!  
	    sqlQuery.addSelect("journal_name", "name");
            sqlQuery.distinct();
            sqlQuery.addFrom("journal");
	    sqlQuery.addJoin(
		"journal_name",
		"journalKey",
		"journal",
		"dblpKey"
	    );
	    // Limit the results to one name! This should be changed in future
	    sqlQuery.addGroupBy("journal", "acronym");

            JournalConditionVisitor jcv = new JournalConditionVisitor();
            ctx.journalCondition()
                    .forEach(conditionCtx ->
                            jcv.visitJournalCondition(conditionCtx, sqlQuery));
        }
    }
}
