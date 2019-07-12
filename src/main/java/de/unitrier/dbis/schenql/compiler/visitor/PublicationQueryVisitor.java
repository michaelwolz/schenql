package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;

import java.util.Arrays;

class PublicationQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPublicationQuery(SchenqlParser.PublicationQueryContext ctx, Query sqlQuery) {
        if (ctx.PUBLICATION() != null) {
            if (sqlQuery.selectIsEmpty())
                Arrays.stream(DefaultFields.publication).forEach(selectField ->
                        sqlQuery.addSelect("publication", selectField));

            sqlQuery.distinct();
            sqlQuery.addFrom("publication");

            switch (ctx.PUBLICATION().getText().substring(0, 4).toUpperCase()) {
                case "BOOK":
                    sqlQuery.addCondition(
                            new BooleanCondition("publication",
                                    "type",
                                    BooleanOperator.EQUALS,
                                    "book")
                    );
                    break;
                case "ARTI":
                    sqlQuery.addCondition(
                            new BooleanCondition("publication",
                                    "type",
                                    BooleanOperator.EQUALS,
                                    "article")
                    );
                    break;
                case "PHDT":
                    sqlQuery.addCondition(
                            new BooleanCondition("publication",
                                    "type",
                                    BooleanOperator.EQUALS,
                                    "phdthesis")
                    );
                    break;
                case "MAST":
                    sqlQuery.addCondition(
                            new BooleanCondition("publication",
                                    "type",
                                    BooleanOperator.EQUALS,
                                    "masterthesis")
                    );
                    break;
                case "INPR":
                    sqlQuery.addCondition(
                            new BooleanCondition("publication",
                                    "type",
                                    BooleanOperator.EQUALS,
                                    "inproceedings")
                    );
                    break;
            }

            PublicationConditionVisitor pcv = new PublicationConditionVisitor();
            ctx.publicationCondition()
                    .forEach(conditionCtx ->
                        pcv.visitPublicationCondition(conditionCtx, sqlQuery)
                    );
        }

        if (ctx.publicationFunction() != null) {
            PublicationFunctionVisitor pfv = new PublicationFunctionVisitor();
            pfv.visitPublicationFunction(ctx.publicationFunction(), sqlQuery);
        }
    }
}
