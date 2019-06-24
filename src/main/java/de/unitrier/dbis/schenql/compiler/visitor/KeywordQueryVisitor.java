package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Arrays;

class KeywordQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitKeywordQuery(SchenqlParser.KeywordQueryContext ctx, Query sqlQuery) {
        if (ctx.KEYWORD() != null) {
            if (sqlQuery.selectIsEmpty()) {
                Arrays.stream(DefaultFields.keyword).forEach(selectField -> {
                    sqlQuery.addSelect("keyword", selectField);
                });
            }

            sqlQuery.distinct();
            sqlQuery.addFrom("keyword");

            KeywordConditionVisitor kcv = new KeywordConditionVisitor();
            ctx.keywordCondition()
                    .forEach(conditionCtx -> {
                        kcv.visitKeywordCondition(conditionCtx, sqlQuery);
                    });
        }
    }
}
