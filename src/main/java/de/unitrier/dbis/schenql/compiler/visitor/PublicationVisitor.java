package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.FulltextCondition;

class PublicationVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPublication(SchenqlParser.PublicationContext ctx, Query sqlQuery) {
        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            pqv.visitPublicationQuery(ctx.publicationQuery(), sqlQuery);
        } else if (ctx.DBLP_KEY() != null) {
            sqlQuery.addFrom("publication");
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "dblpKey",
                            BooleanOperator.EQUALS,
                            ctx.DBLP_KEY().getText()
                    )
            );
        } else {
            sqlQuery.addFrom("publication");
            if (ctx.TILDE() != null) {
                sqlQuery.addCondition(
                        new FulltextCondition(
                                "publication",
                                "title",
                                ctx.STRING().getText()
                        )
                );
            } else {
                sqlQuery.addCondition(
                        new BooleanCondition(
                                "publication",
                                "title",
                                BooleanOperator.EQUALS,
                                ctx.STRING().getText()
                        )
                );
            }
        }
    }
}
