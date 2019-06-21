package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryCondition;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class KeywordQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitKeywordQuery(SchenqlParser.KeywordQueryContext ctx) {
        return visitKeywordQuery(ctx, DefaultFields.keyword);
    }

    public String visitKeywordQuery(SchenqlParser.KeywordQueryContext ctx, String[] selectFields) {
        if (ctx.KEYWORD() != null) {
            KeywordConditionVisitor klv = new KeywordConditionVisitor();

            // Getting conditions from child nodes
            List<QueryCondition> queryConditions = ctx.keywordCondition()
                    .stream()
                    .map(ql -> ql.accept(klv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Build select statement
            return "SELECT DISTINCT " + String.join(", ", selectFields) + " FROM `keyword`" +
                    Helper.addConditions(queryConditions);
        }
        return null;
    }
}
