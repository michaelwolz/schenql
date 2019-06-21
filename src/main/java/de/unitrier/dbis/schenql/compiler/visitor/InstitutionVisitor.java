package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;

class InstitutionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitInstitution(SchenqlParser.InstitutionContext ctx, Query sqlQuery) {
        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor iqv = new InstitutionQueryVisitor();
            iqv.visitInstitutionQuery(ctx.institutionQuery(), sqlQuery, null);
        } else {
            "SELECT `institution`.`key` FROM `institution`" +
                    "JOIN `institution_name`" +
                    "ON `institution_name`.`.institutionKey` = `institution`.`key`" +
                    "WHERE `institution_name`.`name` ";
                    //Helper.sqlStringComparison(ctx.STRING().getText());
        }
    }
}
