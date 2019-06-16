package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

public class JournalLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitJournalLimitation(SchenqlParser.JournalLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        if (ctx.NAMED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`journal_name`",
                            "`journalKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setLimitation("`journal`.`name` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.ACRONYM() != null) {
            // Exact match for acronyms
            ql.setLimitation("`journal`.`acronym` = \"" + ctx.STRING().getText() + "\"");
            return ql;
        }

        if (ctx.ABOUT() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"),
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
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` > " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.BEFORE() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` < " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.IN_YEAR() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`year` = " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.VOLUME() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setLimitation("`publication`.`volume` = " + ctx.STRING().getText());
            return ql;
        }

        return null;
    }
}
