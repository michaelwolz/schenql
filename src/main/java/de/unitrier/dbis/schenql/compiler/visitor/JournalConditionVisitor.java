package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryCondition;

public class JournalConditionVisitor extends SchenqlParserBaseVisitor<QueryCondition> {
    @Override
    public QueryCondition visitJournalCondition(SchenqlParser.JournalConditionContext ctx) {
        QueryCondition ql = new QueryCondition();

        if (ctx.NAMED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`journal_name`",
                            "`journalKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setCondition("`journal`.`name` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.ACRONYM() != null) {
            // Exact match for acronyms
            ql.setCondition("`journal`.`acronym` = \"" + ctx.STRING().getText() + "\"");
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
            ql.setCondition("`publication_has_keyword`.`keyword` IN (" + kv.visitKeyword(ctx.keyword()) + ")");
            return ql;
        }

        if (ctx.AFTER() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setCondition("`publication`.`year` > " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.BEFORE() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setCondition("`publication`.`year` < " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.IN_YEAR() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setCondition("`publication`.`year` = " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.VOLUME() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });
            ql.setCondition("`publication`.`volume` = " + ctx.STRING().getText());
            return ql;
        }

        if (ctx.OF() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication`",
                            "`journal_dblpKey`",
                            "`journal`.`dblpKey`"
                    )
            });

            PublicationVisitor pv = new PublicationVisitor();
            ql.setCondition("`journal`.`dblpKey` IN ( " + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        return null;
    }
}
