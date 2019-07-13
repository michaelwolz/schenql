package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.ExtendedFields;
import de.unitrier.dbis.schenql.compiler.Schenql;
import de.unitrier.dbis.sqlquerybuilder.AggregateFunction;
import de.unitrier.dbis.sqlquerybuilder.OrderBy;
import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Arrays;

class PublicationFunctionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPublicationFunction(SchenqlParser.PublicationFunctionContext ctx, Query sqlQuery) {
        if (ctx.MOST_CITED() != null) {
            if ((ctx.getParent().getParent().getRuleContext() instanceof SchenqlParser.QueryContext)) {
                if (Schenql.apiMode)
                    Arrays.stream(ExtendedFields.publication).forEach(selectField ->
                            sqlQuery.addSelect("publication", selectField));
                else
                    Arrays.stream(DefaultFields.publication).forEach(selectField ->
                            sqlQuery.addSelect("publication", selectField));
                AggregateFunction af = new AggregateFunction(
                        "*",
                        "COUNT"
                );
                af.setAlias("count");
                sqlQuery.addSelect(af);
            }

            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            pqv.visitPublicationQuery(ctx.publicationQuery(), sqlQuery);

            sqlQuery.addJoin(
                    "publication_references",
                    "pub2_id",
                    "publication",
                    "dblpKey"
            );

            sqlQuery.addGroupBy("publication", "title");
            OrderBy ob = new OrderBy(
                    new AggregateFunction(
                            "*",
                            "COUNT"
                    )
            );
            ob.setSortOrder("DESC");
            sqlQuery.addOrderBy(ob);
            sqlQuery.addLimit(5);
        }
    }
}
