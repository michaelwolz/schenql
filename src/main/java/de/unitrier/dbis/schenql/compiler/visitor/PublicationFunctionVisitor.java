package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.OrderBy;
import de.unitrier.dbis.sqlquerybuilder.Query;

class PublicationFunctionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPublicationFunction(SchenqlParser.PublicationFunctionContext ctx, Query sqlQuery) {
        if (ctx.MOST_CITED() != null) {
            sqlQuery.addSelect("publication", "dblpKey");
            if ((ctx.getParent().getParent().getRuleContext() instanceof SchenqlParser.QueryContext)) {
                sqlQuery.addSelect("publication", "title");
                sqlQuery.addSelect("publication", "year");
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
            OrderBy ob = new OrderBy("COUNT(*)");
            ob.setSortOrder("DESC");
            sqlQuery.addOrderBy(ob);
            sqlQuery.addLimit(5);
        }
    }
}
