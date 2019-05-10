package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

public class InstitutionVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitInstitution(SchenqlParser.InstitutionContext ctx) {
        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor jqv = new InstitutionQueryVisitor();
            return jqv.visitInstitutionQuery(ctx.institutionQuery());
        } else {
            return "SELECT `institution`.`key` FROM `institution`" +
                    "JOIN `institution_name`" +
                    "ON `institution_name`.`.institutionKey` = `institution`.`key`" +
                    "WHERE `institution_name`.`name` " +
                    Helper.sqlStringComparison(ctx.STRING().getText());
        }
    }
}
