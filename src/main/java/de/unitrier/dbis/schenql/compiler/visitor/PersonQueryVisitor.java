package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.QueryCondition;

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
            PersonConditionVisitor plv = new PersonConditionVisitor();

            // Getting conditions from child nodes
            List<QueryCondition> queryConditions = ctx.personCondition()
                    .stream()
                    .map(ql -> ql.accept(plv))
                    .filter(Objects::nonNull)
                    .collect(toList());

            // Add specialization - TODO: This is wrong. It only checks if the person was an author or editor in some way
            QueryCondition ql = new QueryCondition();
            switch (ctx.PERSON().getText().substring(0, 4).toUpperCase()) {
                case "AUTH":
                    ql.setCondition("`person`.`dblpKey` IN " +
                            "(SELECT `person_authored_publication`.`personKey` FROM `person_authored_publication`)");
                    queryConditions.add(ql);
                    break;
                case "EDIT":
                    ql.setCondition("`person`.`dblpKey` IN " +
                            "(SELECT `person_edited_publication`.`personKey` FROM `person_edited_publication`)");
                    queryConditions.add(ql);
                    break;
            }

            // Build select statement
            return "SELECT DISTINCT " + String.join(", ", selectFields) + " FROM `person`" +
                    Helper.addConditions(queryConditions);
        }
        return null;
    }
}
