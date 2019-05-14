package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.ArrayList;

public class PersonLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitPersonLimitation(SchenqlParser.PersonLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        if (ctx.NAMED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_names`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    )
            });
            ql.setLimitation("`person_names`.`name` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.AUTHORED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_authored_publication`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    )
            });
            PublicationVisitor pv = new PublicationVisitor();
            ql.setLimitation("`person_authored_publication`.`publicationKey` IN (" +
                    pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.EDITED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_edited_publication`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    )
            });
            PublicationVisitor pv = new PublicationVisitor();
            ql.setLimitation("`person_edited_publication`.`publicationKey` IN (" +
                    pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.WORKS_FOR() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_works_for_institution`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    ),
                    new Join(
                            "`institution`",
                            "`key`",
                            "`person_works_for_institution`.`institutionKey`"
                    ),
                    new Join(
                            "`institution_name`",
                            "`institutionKey`",
                            "`institution`.`key`"
                    )
            });

            ql.setLimitation("`institution_name`.`name`" + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.PUBLISHED_WITH() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_works_for_institution`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    ),
                    new Join(
                            "`institution`",
                            "`key`",
                            "`person_works_for_institution`.`institutionKey`"
                    )
            });

            InstitutionVisitor iv = new InstitutionVisitor();
            ql.setLimitation("`institution`.`key` IN " + iv.visitInstitution(ctx.institution()));

            return ql;
        }

        if (ctx.PUBLISHED_IN() != null) {
            if (ctx.conference() != null) {
                ql.setJoins(new Join[]{
                        new Join(
                                "`person_authored_publication`",
                                "`personKey`",
                                "`person`.`dblpKey`"
                        ),
                        new Join(
                                "`publication`",
                                "`dblpKey`",
                                "`person_authored_publication`.`publicationKey`"
                        )
                });
                ConferenceVisitor cv = new ConferenceVisitor();
                ql.setLimitation("`publication`.`conference_dblpKey` IN (" + cv.visitConference(ctx.conference()) + ")");
            } else if (ctx.journal() != null) {
                ql.setJoins(new Join[]{
                        new Join(
                                "`person_authored_publication`",
                                "`personKey`",
                                "`person`.`dblpKey`"
                        ),
                        new Join(
                                "`publication`",
                                "`dblpKey`",
                                "`person_authored_publication`.`publicationKey`"
                        )
                });
                JournalVisitor jv = new JournalVisitor();
                ql.setLimitation("`publication`.`journal_dblpKey` IN (" + jv.visitJournal(ctx.journal()) + ")");
            } else if (ctx.DBLP_KEY() != null) {
                ql.setJoins(new Join[]{
                        new Join(
                                "`person_authored_publication`",
                                "`personKey`",
                                "`person`.`dblpKey`"
                        ),
                        new Join(
                                "`publication`",
                                "`dblpKey`",
                                "`person_authored_publication`.`publicationKey`"
                        )
                });
                ql.setLimitation("`publication`.`journal_dblpKey` = " + ctx.DBLP_KEY().getText() +
                        "OR `publication`.`conference_dblpKey` = " + ctx.DBLP_KEY().getText());
            } else {
                ql.setJoins(new Join[]{
                        new Join(
                                "`person_authored_publication`",
                                "`personKey`",
                                "`person`.`dblpKey`"
                        ),
                        new Join(
                                "`publication`",
                                "`dblpKey`",
                                "`person_authored_publication`.`publicationKey`"
                        )
                });
                ql.setLimitation("`publication`.`journal_dblpKey` IN " +
                        "(" + JournalVisitor.defaultQuery(ctx.STRING().getText()) + ") " +
                        "OR `publication`.`conference_dblpKey` IN " +
                        "(" + ConferenceVisitor.defaultQuery(ctx.STRING().getText()) + ")"
                );
            }
            return ql;
        }

        if (ctx.CITED_BY() != null) {
            PublicationVisitor pv = new PublicationVisitor();
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_authored_publication`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    ),
                    new Join(
                            "`publication_references`",
                            "`pub_id`",
                            "`person_authored_publication`.`publicationKey`"
                    )
            });
            ql.setGroupBy(new ArrayList<>() {
                {
                    add("`person`.`dblpKey`");
                }
            });
            ql.setLimitation("`publication_references`.`pub2_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.CITES() != null) {
            PublicationVisitor pv = new PublicationVisitor();
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_authored_publication`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    ),
                    new Join(
                            "`publication_references`",
                            "`pub2_id`",
                            "`person_authored_publication`.`publicationKey`"
                    )
            });
            ql.setGroupBy(new ArrayList<>() {
                {
                    add("`person`.`dblpKey`");
                }
            });
            ql.setLimitation("`publication_references`.`pub_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.ORCID() != null) {
            ql.setLimitation("`person`.`orcid` = \"" + ctx.ORCID_VALUE().getText() + "\"");
        }

        return null;
    }
}
