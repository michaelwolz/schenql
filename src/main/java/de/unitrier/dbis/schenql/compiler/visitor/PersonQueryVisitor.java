package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class PersonQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPersonQuery(SchenqlParser.PersonQueryContext ctx) {
        return visitPersonQuery(ctx, DefaultFields.person);
    }

    public String visitPersonQuery(SchenqlParser.PersonQueryContext ctx, String[] selectFields) {
        if (ctx.PERSON() != null) {
            PersonLimitationVisitor plv = new PersonLimitationVisitor();

            // Getting limitations from child nodes
            List<QueryLimitation> queryLimitations = ctx.personLimitation()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Build select statement
            return "SELECT " + String.join(", ", selectFields) + " FROM `person`" +
                    Helper.addLimitations(queryLimitations);
        }
        return null;
    }
}
