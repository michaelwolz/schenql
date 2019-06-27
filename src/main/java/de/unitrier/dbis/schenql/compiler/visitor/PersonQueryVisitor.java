package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.DefaultFields;
import de.unitrier.dbis.sqlquerybuilder.Query;

import java.util.Arrays;

class PersonQueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPersonQuery(SchenqlParser.PersonQueryContext ctx, Query sqlQuery) {
        if (ctx.PERSON() != null) {
            if (sqlQuery.selectIsEmpty())
                Arrays.stream(DefaultFields.person).forEach(selectField ->
                        sqlQuery.addSelect("person", selectField)
                );

            sqlQuery.distinct();
            sqlQuery.addFrom("person");

            switch (ctx.PERSON().getText().substring(0, 4).toUpperCase()) {
                case "AUTH":
                    sqlQuery.addJoin(
                            "person_authored_publication",
                            "personKey",
                            "person",
                            "dblpKey"
                    );
                    break;
                case "EDIT":
                    sqlQuery.addJoin(
                            "person_edited_publication",
                            "personKey",
                            "person",
                            "dblpKey"
                    );
                    break;
            }
            PersonConditionVisitor pcv = new PersonConditionVisitor();
            ctx.personCondition()
                    .forEach(conditionCtx ->
                            pcv.visitPersonCondition(conditionCtx, sqlQuery)
                    );
        }
    }
}
