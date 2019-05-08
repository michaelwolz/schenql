package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

import java.util.ArrayList;

public class KeywordVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitKeywords(SchenqlParser.KeywordsContext ctx) {
        if (ctx.STRING().size() > 1) {
            ArrayList<String> keywords = new ArrayList<>();
            ctx.STRING().forEach(keyword -> keywords.add(keyword.getText()));
            return " IN (" + String.join(",", keywords) + ")";
        } else {
            return " = " + ctx.STRING(0).getText();
        }
    }
}
