package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.InCondition;

class KeywordVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitKeyword(SchenqlParser.KeywordContext ctx, Query sqlQuery) {
        if (ctx.keywordQuery() != null) {
            KeywordQueryVisitor kqv = new KeywordQueryVisitor();
            kqv.visitKeywordQuery(ctx.keywordQuery(), sqlQuery);
        } else {
            sqlQuery.addFrom("keyword");
            InCondition keywords = new InCondition("keyword", "keyword");
            ctx.STRING().forEach(keyword -> keywords.addValue(keyword.getText()));
            sqlQuery.addCondition(keywords);
        }
    }
}