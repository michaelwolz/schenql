package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;

class InstitutionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitInstitution(SchenqlParser.InstitutionContext ctx, Query sqlQuery) {
        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor iqv = new InstitutionQueryVisitor();
            iqv.visitInstitutionQuery(ctx.institutionQuery(), sqlQuery);
        } else {
            sqlQuery.addFrom("institution");
            sqlQuery.addJoin(
                    "institution_name",
                    "institutionKey",
                    "institution",
                    "key"
            );
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "institution_name",
                            "name",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }
    }
}
