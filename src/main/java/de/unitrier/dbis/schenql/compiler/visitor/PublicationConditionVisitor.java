package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryCondition;

import java.util.ArrayList;

public class PublicationConditionVisitor extends SchenqlParserBaseVisitor<QueryCondition> {
    @Override
    public QueryCondition visitPublicationCondition(SchenqlParser.PublicationConditionContext ctx) {
        QueryCondition ql = new QueryCondition();

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
            ql.setCondition("`person`.`dblpKey` IN (" + pv.visitPerson(ctx.person()) + ")");
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
            ql.setCondition("`person`.`dblpKey` IN (" + pv.visitPerson(ctx.person()) + ")");
            return ql;
        }

        // Note: Data is not sufficient for this
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
            ql.setCondition("`person_works_for_institution`.`institutionKey` IN " +
                    iv.visitInstitution(ctx.institution()));
            return ql;
        }

        if (ctx.ABOUT() != null) {
            if (ctx.KEYWORD() != null) {
                ql.setJoins(new Join[]{
                        new Join("`publication_has_keyword`",
                                "`dblpKey`",
                                "`publication`.`dblpKey`"
                        )
                });

                KeywordVisitor kv = new KeywordVisitor();
                ql.setCondition("`publication_has_keyword`.`keyword` IN (" + kv.visitKeyword(ctx.keyword()) + ")");
            } else {
                ql.setCondition("MATCH (`publication`.`abstract`) " +
                        "AGAINST(\"" + ctx.STRING().getText() +
                        "\" IN NATURAL LANGUAGE MODE)");
            }
            return ql;
        }

        // TODO: Full-Text-Search for abstracts with ON keyword (needs to be added to the grammar too)
        if (ctx.BEFORE() != null) {
            ql.setCondition("`publication`.`year` < " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.AFTER() != null) {
            ql.setCondition("`publication`.`year` > " + ctx.YEAR().getText());
            return ql;
        }

        if (ctx.IN_YEAR() != null) {
            ql.setCondition("`publication`.`year` = " + ctx.YEAR().getText());
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
                ql.setCondition("`journal_name`.`journal_dblpKey` IN (" + jv.visitJournal(ctx.journal()) + ")");
            } else if (ctx.conference() != null) {
                ConferenceVisitor jv = new ConferenceVisitor();
                ql.setCondition("`publication`.`conference_dblpKey` IN (" + jv.visitConference(ctx.conference()) + ")");
            } else if (ctx.DBLP_KEY() != null) {
                ql.setCondition("`publication`.`conference_dblpKey` = " + ctx.DBLP_KEY().getText() +
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

                ql.setCondition("`journal`.`acronym` = " + ctx.STRING().getText() +
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
            ql.setCondition("`publication_references`.`pub_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.REFERENCES() != null) {
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
            ql.setCondition("`publication_references`.`pub2_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.TITLE() != null) {
            if (ctx.TILDE() != null) {
                ql.setCondition("MATCH (`publication`.`title`) AGAINST('" + ctx.STRING().getText() + "')");
            } else {
                ql.setCondition("`publication`.`title` = '" + ctx.STRING().getText() + "'");
            }
            return ql;
        }
        return null;
    }
}