package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.Select;

import java.util.Arrays;

class ConferenceQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitConferenceQuery(SchenqlParser.ConferenceQueryContext ctx, Query sqlQuery, Select[] selectFields) {
        if (ctx.CONFERENCE() != null) {
            Arrays.stream(selectFields).forEach(sqlQuery::addSelect);
            sqlQuery.distinct();
            sqlQuery.addFrom("conference");

            ConferenceConditionVisitor plv = new ConferenceConditionVisitor();
            ctx.conferenceCondition()
                    .forEach(conditionCtx -> {
                        plv.visitConferenceCondition(conditionCtx, sqlQuery);
                    });
        }
    }
}
