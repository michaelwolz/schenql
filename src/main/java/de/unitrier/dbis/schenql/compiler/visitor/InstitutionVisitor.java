package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class InstitutionVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitInstitution(SchenqlParser.InstitutionContext ctx) {
        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor jqv = new InstitutionQueryVisitor();
            return jqv.visitInstitutionQuery(ctx.institutionQuery());
        } else {
            return ctx.STRING().getText();
        }
    }
}
