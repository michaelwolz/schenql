package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.ArrayList;

public class PublicationLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitPublicationLimitation(SchenqlParser.PublicationLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

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

        // Published by TODO: Data is not sufficient for this, just using a approach for this
        if (ctx.PUBLISHED_BY() != null) {
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
                    ),
                    new Join(
                            "`person_works_for_institution`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    )
            });

            InstitutionVisitor iv = new InstitutionVisitor();
            ql.setLimitation("`person_works_for_institution`.`institutionKey` IN " +
                    iv.visitInstitution(ctx.institution()));
            return ql;
        }

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
        if (ctx.BEFORE() != null) {
            ql.setLimitation("`publication`.`year` < " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.AFTER() != null) {
            ql.setLimitation("`publication`.`year` > " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.IN_YEAR() != null) {
            ql.setLimitation("`publication`.`year` = " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.APPEARED_IN() != null) {
            if (ctx.journal() != null) {
                ql.setJoins(new Join[]{
                        new Join("`journal_name`",
                                "`journal_key`",
                                "`publication`.`dblpKey`"
                        )
                });

                JournalVisitor jv = new JournalVisitor();
                ql.setLimitation("`journal_name`.`journal_dblpKey` IN (" + jv.visitJournal(ctx.journal()) + ")");
            } else if (ctx.conference() != null) {
                ConferenceVisitor jv = new ConferenceVisitor();
                ql.setLimitation("`publication`.`conference_dblpKey` IN (" + jv.visitConference(ctx.conference()) + ")");
            } else if (ctx.DBLP_KEY() != null) {
                ql.setLimitation("`publication`.`conference_dblpKey` = " + ctx.DBLP_KEY().getText() +
                        " OR `publication`.`journal_dblpKey` = " + ctx.DBLP_KEY().getText());
            } else if (ctx.STRING() != null) {
                ql.setJoins(new Join[]{
                        new Join("`journal`",
                                "`dblpKey`",
                                "`publication`.`dblpKey`"
                        ),
                        new Join("`conference`",
                                "`dblpKey`",
                                "`publication`.`dblpKey`")
                });

                ql.setLimitation("`journal`.`acronym` = "+ ctx.STRING().getText() +
                        " OR `conference`.`acronym` = " + ctx.STRING().getText());
            }
            return ql;
        }

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
            ql.setLimitation("`publication_references`.`pub_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

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
            ql.setLimitation("`publication_references`.`pub2_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.TITLE() != null) {
//            ql.setLimitation("MATCH (`publication`.`title`) " +
//                    "AGAINST(\"" + ctx.STRING().getText() +
//                    "\" IN NATURAL LANGUAGE MODE)");
            ql.setLimitation("`publication`.`title` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        return null;
    }
}
