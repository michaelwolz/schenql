package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

import java.util.ArrayList;

public class KeywordVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitKeyword(SchenqlParser.KeywordContext ctx) {
        if (ctx.keywordQuery() != null) {
            KeywordQueryVisitor pqv = new KeywordQueryVisitor();
            return pqv.visitKeywordQuery(ctx.keywordQuery());
        } else {
            String keywordStr;
            if (ctx.STRING().size() > 1) {
                ArrayList<String> keywords = new ArrayList<>();
                ctx.STRING().forEach(keyword -> keywords.add("\"" + keyword.getText() + "\""));
                keywordStr = String.join(",", keywords);
            } else {
                keywordStr = "\"" + ctx.STRING(0).getText() + "\"";
            }
            return "SELECT `keyword`.`keyword` FROM `keyword` WHERE " +
                    "`keyword`.`keyword` IN (" + keywordStr + ")";
        }
    }
}