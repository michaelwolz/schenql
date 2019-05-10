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

        if (ctx.IN_YEAR() != null) {
            return ql;
        }

        if (ctx.CITY() != null) {
            return ql;
        }

        if (ctx.COUNTRY() != null) {
            return ql;
        }

        if (ctx.MEMBERS() != null) {
            return ql;
        }

        return null;
    }
}
