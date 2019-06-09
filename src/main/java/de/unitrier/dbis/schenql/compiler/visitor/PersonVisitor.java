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
        } else if (ctx.DBLP_KEY() != null) {
            return ctx.DBLP_KEY().getText();
        } else if (ctx.ORCID_VALUE() != null) {
            return "SELECT DISTINCT `person`.`dblpKey` FROM `person` " +
                    "WHERE `person`.`orcid` = \"" + ctx.ORCID_VALUE().getText() + "\"";
        } else {
            return "SELECT DISTINCT `person`.`dblpKey` FROM `person` "
                    + "JOIN `person_names` " +
                    "ON `person_names`.`personKey` = `person`.`dblpKey` " +
                    "WHERE `person_names`.`name` " +
                    Helper.sqlStringComparison(ctx.STRING().getText());
        }
    }
}
