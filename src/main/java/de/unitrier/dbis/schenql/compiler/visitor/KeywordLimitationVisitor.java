package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.ArrayList;

public class KeywordLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitKeywordLimitation(SchenqlParser.KeywordLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        if (ctx.OF() != null) {
            ArrayList<Join> joins = new ArrayList<>();
            joins.add(
                    new Join("`publication_has_keyword`",
                            "`keyword`",
                            "`keyword`.`keyword`"
                    )
            );
            joins.add(
                    new Join("`publication`",
                            "`dblpKey`",
                            "`publication_has_keyword`.`dblpKey`"
                    )
            );

            if (ctx.conference() != null) {
                ConferenceQueryVisitor cqv = new ConferenceQueryVisitor();
                ql.setLimitation(
                        "`publication`.`conference_dblpKey` IN (" +
                                cqv.visitConferenceQuery(
                                        ctx.conference().conferenceQuery(),
                                        new String[]{"`conference`.`dblpKey`"}
                                ) + ")"
                );
            } else if (ctx.journal() != null) {
                JournalQueryVisitor cqv = new JournalQueryVisitor();
                ql.setLimitation(
                        "`publication`.`journal_dblpKey` IN (" +
                                cqv.visitJournalQuery(
                                        ctx.journal().journalQuery(),
                                        new String[]{"`journal`.`dblpKey`"}
                                ) + ")"
                );
            } else if (ctx.person() != null) {
                joins.add(
                        new Join("`person_authored_publication`",
                                "`publicationKey`",
                                "`publication`.`dblpKey`"
                        )
                );
                joins.add(
                        new Join("`person`",
                                "`dblpKey`",
                                "`person_authored_publication`.`personKey`"
                        )
                );
                PersonQueryVisitor pqv = new PersonQueryVisitor();
                ql.setLimitation(
                        "`person`.`dblpKey` IN (" +
                                pqv.visitPersonQuery(
                                        ctx.person().personQuery(),
                                        new String[]{"`person`.`dblpKey`"}
                                ) + ")"
                );
            } else if (ctx.publication() != null) {
                PublicationQueryVisitor pqv = new PublicationQueryVisitor();
                ql.setLimitation(
                        "`publication`.`dblpKey` IN (" +
                                pqv.visitPublicationQuery(
                                        ctx.publication().publicationQuery(),
                                        new String[]{"`publication`.`dblpKey`"}
                                ) + ")"
                );
            }
            ql.setJoins(joins.toArray(new Join[0]));
            return ql;
        }

        return null;
    }
}
