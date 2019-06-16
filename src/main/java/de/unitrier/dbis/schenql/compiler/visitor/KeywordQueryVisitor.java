package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

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
            KeywordLimitationVisitor klv = new KeywordLimitationVisitor();

            // Getting limitations from child nodes
            List<QueryLimitation> queryLimitations = ctx.keywordLimitation()
                    .stream()
                    .map(ql -> ql.accept(klv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Build select statement
            return "SELECT DISTINCT " + String.join(", ", selectFields) + " FROM `keyword`" +
                    Helper.addLimitations(queryLimitations);
        }
        return null;
    }
}
