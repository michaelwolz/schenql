package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

public class ConferenceVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitConference(SchenqlParser.ConferenceContext ctx) {
        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor jqv = new ConferenceQueryVisitor();
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.PersonLimitationContext) {
                return jqv.visitConferenceQuery(ctx.conferenceQuery(), new String[]{"`conference`.`dblpKey`"});
            }
            return jqv.visitConferenceQuery(ctx.conferenceQuery());
        } else {
            return defaultQuery(ctx.STRING().getText());
        }
    }

    static String defaultQuery(String acronym)  {
        return "SELECT `conference`.`dblpKey` FROM `conference` "
                + "WHERE `conference`.`acronym` = \"" + acronym + "\"";
    }
}
