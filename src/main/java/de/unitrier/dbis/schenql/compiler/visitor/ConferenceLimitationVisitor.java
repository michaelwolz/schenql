package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

public class ConferenceLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitConferenceLimitation(SchenqlParser.ConferenceLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        if (ctx.NAMED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`conference_name`",
                            "`conferenceKey`",
                            "`conference`.`dblpKey`"
                    )
            });
            ql.setLimitation("`conference`.`name` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.ACRONYM() != null) {
            // Exact match for acronyms
            ql.setLimitation("`conference`.`acronym` = " + ctx.STRING().getText());
            return ql;
        }

        if (ctx.ABOUT() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conferenceKey`",
                            "`conference`.`dblpKey`"),
                    new Join("`publication_has_keyword`",
                            "`dblpKey`",
                            "`publication`.`dblpKey`"
                    )
            });

            KeywordVisitor kv = new KeywordVisitor();
            ql.setLimitation("`publication_has_keyword`.`keyword` IN (" + kv.visitKeywords(ctx.keywords()) + ")");
            return ql;
        }

        if (ctx.AFTER() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conferenceKey`",
                            "`conference`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` > " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.BEFORE() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conferenceKey`",
                            "`conference`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` < " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.IN_YEAR() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conferenceKey`",
                            "`conference`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` = " + ctx.YEAR().getText());
            return ql;
        }

        return null;
    }
}
