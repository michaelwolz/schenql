package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

public class ConferenceVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitConference(SchenqlParser.ConferenceContext ctx) {
        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor jqv = new ConferenceQueryVisitor();
            return jqv.visitConferenceQuery(ctx.conferenceQuery());
        } else {
            return "SELECT `conference`.`dblpKey` FROM `conference` "
                    + "WHERE `conference`.`acronym` = " + ctx.STRING().getText();
        }
    }
}
