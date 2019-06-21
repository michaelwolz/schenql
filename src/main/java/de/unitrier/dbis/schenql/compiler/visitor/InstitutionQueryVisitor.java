package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryCondition;
import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Arrays;

class InstitutionQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitInstitutionQuery(SchenqlParser.InstitutionQueryContext ctx, Query sqlQuery, String[] selectFields) {
        if (ctx.INSTITUTION() != null) {
            Arrays.stream(selectFields).forEach(sqlQuery::addSelect);
            sqlQuery.distinct();
            sqlQuery.addFrom("institution");

            InstitutionConditionVisitor icv = new InstitutionConditionVisitor();
            ctx.institutionCondition()
                    .forEach(conditionCtx -> {
                        icv.visitInstitutionCondition(conditionCtx, sqlQuery);
                    });
        }
    }
}
