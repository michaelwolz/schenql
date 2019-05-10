package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class InstitutionQueryVisitor extends SchenqlParserBaseVisitor<String> {
    public String visitInstitutionQuery(SchenqlParser.InstitutionQueryContext ctx) {
        return visitInstitutionQuery(ctx, DefaultFields.institution);
    }

    public String visitInstitutionQuery(SchenqlParser.InstitutionQueryContext ctx, String[] selectFields) {
        if (ctx.INSTITUTION() != null) {
            InstitutionLimitationVisitor plv = new InstitutionLimitationVisitor();

            // Getting limitations from child nodes
            List<QueryLimitation> queryLimitations = ctx.institutionLimitation()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Build select statement
            return "SELECT " + String.join(", ", selectFields) + " FROM `institution`" +
                    Helper.addLimitations(queryLimitations);
        }
        return null;
    }
}
