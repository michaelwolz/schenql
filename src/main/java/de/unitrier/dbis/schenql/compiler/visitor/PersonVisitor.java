package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;

public class PersonVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitPerson(SchenqlParser.PersonContext ctx) {
        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            if (ctx.getParent().getRuleContext() instanceof SchenqlParser.PublicationLimitationContext) {
                return pqv.visitPersonQuery(ctx.personQuery(), new String[]{"`person`.`dblpKey`"});
            }
            return pqv.visitPersonQuery(ctx.personQuery());
        } else {
            return "SELECT `person`.`dblpKey` FROM `person` "
                    + "INNER JOIN `person_names` " +
                    "ON `person_names`.`personKey` = `person`.`dblpKey` " +
                    "WHERE `person_names`.`name` " +
                    Helper.sqlStringComparison(ctx.STRING().getText());
        }
    }
}
