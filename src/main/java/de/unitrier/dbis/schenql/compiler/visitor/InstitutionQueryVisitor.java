package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Arrays;

class InstitutionQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitInstitutionQuery(SchenqlParser.InstitutionQueryContext ctx, Query sqlQuery) {
        if (ctx.INSTITUTION() != null) {
            if (sqlQuery.selectIsEmpty()) {
                Arrays.stream(DefaultFields.institution).forEach(selectField -> {
                    sqlQuery.addSelect("institution", selectField);
                });
            }

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
