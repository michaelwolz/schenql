package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class ConferenceQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitConferenceQuery(SchenqlParser.ConferenceQueryContext ctx) {
        return visitConferenceQuery(ctx, DefaultFields.conference);
    }

    public String visitConferenceQuery(SchenqlParser.ConferenceQueryContext ctx, String[] selectFields) {
        if (ctx.CONFERENCE() != null) {
            ConferenceLimitationVisitor plv = new ConferenceLimitationVisitor();

            // Getting limitations from child nodes
            List<QueryLimitation> queryLimitations = ctx.conferenceLimitation()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Build select statement
            return "SELECT DISTINCT " + String.join(", ", selectFields) + " FROM `conference`" +
                    Helper.addLimitations(queryLimitations);
        }
        return null;
    }
}
