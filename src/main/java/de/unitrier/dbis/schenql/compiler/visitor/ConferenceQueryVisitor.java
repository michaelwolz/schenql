package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.Select;

import java.util.Arrays;

class ConferenceQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitConferenceQuery(SchenqlParser.ConferenceQueryContext ctx, Query sqlQuery) {
        if (ctx.CONFERENCE() != null) {
            if (sqlQuery.selectIsEmpty())
                Arrays.stream(DefaultFields.conference).forEach(selectField ->
                        sqlQuery.addSelect("conference", selectField));

            sqlQuery.distinct();
            sqlQuery.addFrom("conference");

            ConferenceConditionVisitor ccv = new ConferenceConditionVisitor();
            ctx.conferenceCondition()
                    .forEach(conditionCtx ->
                            ccv.visitConferenceCondition(conditionCtx, sqlQuery));
        }
    }
}
