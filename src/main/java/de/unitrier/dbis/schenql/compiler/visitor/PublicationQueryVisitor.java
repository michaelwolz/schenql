package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryCondition;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class PublicationQueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPublicationQuery(SchenqlParser.PublicationQueryContext ctx) {
        return visitPublicationQuery(ctx, DefaultFields.publication);
    }

    String visitPublicationQuery(SchenqlParser.PublicationQueryContext ctx, String[] selectFields) {
        if (ctx.PUBLICATION() != null) {
            PublicationConditionVisitor plv = new PublicationConditionVisitor();

            // Getting conditions from child nodes
            List<QueryCondition> queryConditions = ctx.publicationCondition()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Add specialization
            QueryCondition ql = new QueryCondition();
            switch (ctx.PUBLICATION().getText().substring(0, 4).toUpperCase()) {
                case "BOOK":
                    ql.setCondition("`publication`.`type` = \"book\"");
                    queryConditions.add(ql);
                    break;
                case "ARTI":
                    ql.setCondition("`publication`.`type` = \"article\"");
                    queryConditions.add(ql);
                    break;
                case "PHDT":
                    ql.setCondition("`publication`.`type` = \"phdthesis\"");
                    queryConditions.add(ql);
                    break;
                case "MAST":
                    ql.setCondition("`publication`.`type` = \"masterthesis\"");
                    queryConditions.add(ql);
                    break;
                case "INPR":
                    ql.setCondition("`publication`.`type` = \"inproceedings\"");
                    queryConditions.add(ql);
                    break;
            }

            // Build select statement
            return "SELECT DISTINCT " + String.join(", ", selectFields) + " FROM `publication`" +
                    Helper.addConditions(queryConditions);
        }

        if (ctx.publicationFunction() != null) {
            PublicationFunctionVisitor pfv = new PublicationFunctionVisitor();
            return pfv.visitPublicationFunction(ctx.publicationFunction());
        }

        return null;
    }
}
