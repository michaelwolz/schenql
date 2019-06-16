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

        /*
        TODO: We don't actually have information on conference names at the moment
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
        }*/

        if (ctx.ACRONYM() != null) {
            // Exact match for acronyms
            ql.setLimitation("`conference`.`acronym` = \"" + ctx.STRING().getText() + "\"");
            return ql;
        }

        if (ctx.ABOUT() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conference_dblpKey`",
                            "`conference`.`dblpKey`"),
                    new Join("`publication_has_keyword`",
                            "`dblpKey`",
                            "`publication`.`dblpKey`"
                    )
            });

            KeywordVisitor kv = new KeywordVisitor();
            ql.setLimitation("`publication_has_keyword`.`keyword` IN (" + kv.visitKeyword(ctx.keyword()) + ")");
            return ql;
        }

        if (ctx.AFTER() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conference_dblpKey`",
                            "`conference`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` > " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.BEFORE() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conference_dblpKey`",
                            "`conference`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` < " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.IN_YEAR() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conference_dblpKey`",
                            "`conference`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` = " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.OF() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`conference_dblpKey`",
                            "`conference`.`dblpKey`"
                    )
            });


            PublicationVisitor pv = new PublicationVisitor();
            ql.setLimitation("`conference`.`dblpKey` IN ( " + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        return null;
    }
}
