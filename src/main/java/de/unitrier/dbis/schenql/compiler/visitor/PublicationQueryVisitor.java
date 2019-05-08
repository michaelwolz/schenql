package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class PublicationQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublicationQuery(SchenqlParser.PublicationQueryContext ctx) {
        if (ctx.PUBLICATION() != null) {
            PublicationLimitationVisitor plv = new PublicationLimitationVisitor();

            // Getting limitations from child nodes
            List<QueryLimitation> queryLimitations = ctx.publicationLimitation()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // First-Class-Citizen
            StringBuilder q = new StringBuilder();
            q.append("SELECT * FROM `publication`");

            // Processing joins and limitations
            ArrayList<Join> joins = new ArrayList<Join>();
            ArrayList<String> limitations = new ArrayList<>();
            queryLimitations.forEach(
                    ql -> {
                        if (ql.getJoins() != null)
                            joins.addAll(Arrays.asList(ql.getJoins()));
                        limitations.add(ql.getLimitation());
                    }
            );

            // Adding joins
            joins.forEach(
                    join -> {
                        q.append(" INNER JOIN ");
                        q.append(join.getTableName());
                        q.append(" ON ");
                        q.append(join.getTableName()).append(".").append(join.getKey());
                        q.append(" = ");
                        q.append(join.getJoinKey());
                    }
            );

            // Adding limitations
            if (limitations.size() > 0) {
                q.append(" WHERE ");
                q.append(String.join(" AND ", limitations));
            }

            return q.toString();
        }

        if (ctx.publicationAggregateFunction() != null) {
            return visitPublicationAggregateFunction(ctx.publicationAggregateFunction());
        }
        return null;
    }
}
