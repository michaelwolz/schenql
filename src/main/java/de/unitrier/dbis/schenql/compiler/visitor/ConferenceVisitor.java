package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;

class ConferenceVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitConference(SchenqlParser.ConferenceContext ctx, Query sqlQuery) {
        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor jqv = new ConferenceQueryVisitor();
            jqv.visitConferenceQuery(ctx.conferenceQuery(), sqlQuery);
        } else {
            sqlQuery.addFrom("conference");
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "conference",
                            "acronym",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }
    }
}