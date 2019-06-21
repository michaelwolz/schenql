package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryCondition;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class JournalQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitJournalQuery(SchenqlParser.JournalQueryContext ctx) {
        return visitJournalQuery(ctx, DefaultFields.journal);
    }

    public String visitJournalQuery(SchenqlParser.JournalQueryContext ctx, String[] selectFields) {
        if (ctx.JOURNAL() != null) {
            JournalConditionVisitor plv = new JournalConditionVisitor();

            // Getting conditions from child nodes
            List<QueryCondition> queryConditions = ctx.journalCondition()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Build select statement
            return "SELECT DISTINCT " + String.join(", ", selectFields) + " FROM `journal`" +
                    Helper.addConditions(queryConditions);
        }
        return null;
    }
}
