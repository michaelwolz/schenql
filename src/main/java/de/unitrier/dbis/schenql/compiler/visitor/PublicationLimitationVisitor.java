package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.ArrayList;

public class PublicationLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitPublicationLimitation(SchenqlParser.PublicationLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        // Written By
        if (ctx.WRITTEN_BY() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_authored_publication`",
                            "`publicationKey`",
                            "`publication`.`dblpKey`"
                    ),
                    new Join(
                            "`person`",
                            "`dblpKey`",
                            "`person_authored_publication`.`personKey`"
                    )
            });
            PersonVisitor pv = new PersonVisitor();
            ql.setLimitation("`person`.`dblpKey` IN (" + pv.visitPerson(ctx.person()) + ")");
            return ql;
        }
        // TODO: What if WRITTEN BY and EDITED BY is used in the same query?
        // Edited By
        if (ctx.EDITED_BY() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_edited_publication`",
                            "`publicationKey`",
                            "`publication`.`dblpKey`"
                    ),
                    new Join(
                            "`person`",
                            "`dblpKey`",
                            "`person_edited_publication`.`personKey`"
                    )
            });
            PersonVisitor pv = new PersonVisitor();
            ql.setLimitation("`person`.`dblpKey` IN (" + pv.visitPerson(ctx.person()) + ")");
            return ql;
        }

        // Published by
//        if (ctx.PUBLISHED_BY() != null) {
//            ql.setJoins(new Join[] {
//                    new Join(
//
//                    )
//            });
//        }

        // About
        if (ctx.ABOUT() != null) {
            ql.setJoins(new Join[]{
                    new Join("`publication_has_keyword`",
                            "`dblpKey`",
                            "`publication`.`dblpKey`"
                    )
            });

            KeywordVisitor kv = new KeywordVisitor();
            ql.setLimitation("`publication_has_keyword`.`keyword` IN (" + kv.visitKeywords(ctx.keywords()) + ")");
            return ql;
        }

        // TODO: Full-Text-Search for abstracts with ON keyword (needs to be added to the grammar too)

        // Before
        if (ctx.BEFORE() != null) {
            ql.setLimitation("`publication`.`year` <= " + ctx.YEAR().getText());
            return ql;
        }

        // After
        if (ctx.AFTER() != null) {
            ql.setLimitation("`publication`.`year` >= " + ctx.YEAR().getText());
            return ql;
        }

        // In Year
        if (ctx.IN_YEAR() != null) {
            ql.setLimitation("`publication`.`year` = " + ctx.YEAR().getText());
            return ql;
        }

        // Appeared In
        if (ctx.APPEARED_IN() != null) {
            ql.setJoins(new Join[]{
                    new Join("`journal_name`",
                            "`journal_key`",
                            "`publication`.`dblpKey`"
                    )
            });

            JournalVisitor jv = new JournalVisitor();
            ql.setLimitation("`journal_name`.`journalKey` IN (" + jv.visitJournal(ctx.journal()) + ")");
        }

        // Cited By
        if (ctx.CITED_BY() != null) {
            PublicationVisitor pv = new PublicationVisitor();
            ql.setJoins(new Join[]{
                    new Join("`publication_references`",
                            "`pub2_id`",
                            "`publication`.`dblpKey`"
                    )
            });
            ql.setGroupBy(new ArrayList<>() {
                {
                    add("`publication`.`dblpKey`");
                }
            });
            ql.setLimitation("`publication_references`.`pub2_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        // Cites
        if (ctx.CITES() != null) {
            PublicationVisitor pv = new PublicationVisitor();
            ql.setJoins(new Join[]{
                    new Join("`publication_references`",
                            "`pub_id`",
                            "`publication`.`dblpKey`"
                    )
            });
            ql.setGroupBy(new ArrayList<>() {
                {
                    add("`publication`.`dblpKey`");
                }
            });
            ql.setLimitation("`publication_references`.`pub_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        // Title
        if (ctx.TITLE() != null) {
            ql.setLimitation("MATCH (`publication`.`title`) " +
                    "AGAINST(\"" + ctx.TITLE().getText() +
                    "\" IN NATURAL LANGUAGE MODE)");
            return ql;
        }

        return null;
    }
}
