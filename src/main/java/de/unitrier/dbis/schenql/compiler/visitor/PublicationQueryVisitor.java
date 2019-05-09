package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class PublicationQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublicationQuery(SchenqlParser.PublicationQueryContext ctx) {
        return visitPublicationQuery(ctx, DefaultFields.publication);
    }

    public String visitPublicationQuery(SchenqlParser.PublicationQueryContext ctx, String[] selectFields) {
        if (ctx.PUBLICATION() != null) {
            PublicationLimitationVisitor plv = new PublicationLimitationVisitor();

            // Getting limitations from child nodes
            List<QueryLimitation> queryLimitations = ctx.publicationLimitation()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Build select statement
            return "SELECT " + String.join(", ", selectFields) + " FROM `publication`" +
                    Helper.addLimitations(queryLimitations);
        }

        /*if (ctx.publicationAggregateFunction() != null) {
            return visitPublicationAggregateFunction(ctx.publicationAggregateFunction());
        }*/
        return null;
    }
}
