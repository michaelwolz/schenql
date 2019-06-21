package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.sqlquerybuilder.Query;

public class ConferenceVisitor extends SchenqlParserBaseVisitor<Void> {
    public void visitConference(SchenqlParser.ConferenceContext ctx, Query sqlQuery) {
        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor jqv = new ConferenceQueryVisitor();
            jqv.visitConferenceQuery(ctx.conferenceQuery(), sqlQuery, null);
        } else {
            // defaultQuery(ctx.STRING().getText());
        }
    }

//    static String defaultQuery(String acronym)  {
//        "SELECT `conference`.`dblpKey` FROM `conference` "
//                + "WHERE `conference`.`acronym` = \"" + acronym + "\"";
//    }
}
