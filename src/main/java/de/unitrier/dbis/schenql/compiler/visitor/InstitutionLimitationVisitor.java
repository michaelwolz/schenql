package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

public class InstitutionLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitInstitutionLimitation(SchenqlParser.InstitutionLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        if (ctx.NAMED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`institution_name`",
                            "`institutionKey`",
                            "`institution`.`key`"
                    )
            });

            ql.setLimitation("`institution_name`.`name` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.CITY() != null) {
            ql.setLimitation("`institution`.`city` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.COUNTRY() != null) {
            ql.setLimitation("`institution`.`country` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.MEMBERS() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_works_for_institution`",
                            "`institutionKey`",
                            "`institution`.`key`"
                    ),
                    new Join(
                            "`person`",
                            "`dblpKey`",
                            "`person_works_for_institution`.`personKey`"
                    )
            });

            PersonVisitor pv = new PersonVisitor();
            ql.setLimitation("`person`.`dblpKey` IN (" + pv.visitPerson(ctx.person()) + ")");
            return ql;
        }

        return null;
    }
}
