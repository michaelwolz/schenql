package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.SubQueryJoin;
import de.unitrier.dbis.sqlquerybuilder.condition.*;

class PersonConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPersonCondition(SchenqlParser.PersonConditionContext ctx, Query sqlQuery) {
        Condition condition = null;

        if (ctx.NAMED() != null) {
            sqlQuery.addJoin(
                    "person_names",
                    "personKey",
                    "person",
                    "dblpKey"
            );
            BooleanCondition cond = new BooleanCondition("person_names", "name");
            cond.setConditionValue(ctx.STRING().getText());
            if (ctx.TILDE() != null) {
                cond.setOperator(BooleanOperator.SOUNDSLIKE);
            } else {
                cond.setOperator(BooleanOperator.EQUALS);
            }
            condition = cond;
        } else if (ctx.AUTHORED() != null) {
            sqlQuery.addJoin(
                    "person_authored_publication",
                    "personKey",
                    "person",
                    "dblpKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            if (ctx.publication().publicationQuery() != null &&
                    ctx.publication().publicationQuery().publicationFunction() != null) {
                sqlQuery.addJoin(
                        new SubQueryJoin(
                                subQuery,
                                "publication_authored_sub",
                                "dblpKey",
                                "person_authored_publication",
                                "publicationKey"
                        )
                );
            } else {
                condition = new SubQueryCondition(
                        "person_authored_publication",
                        "publicationKey",
                        subQuery
                );
            }
        } else if (ctx.EDITED() != null) {
            sqlQuery.addJoin(
                    "person_edited_publication",
                    "personKey",
                    "person",
                    "dblpKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            if (ctx.publication().publicationQuery() != null &&
                    ctx.publication().publicationQuery().publicationFunction() != null) {
                sqlQuery.addJoin(
                        new SubQueryJoin(
                                subQuery,
                                "publication_edited_sub",
                                "dblpKey",
                                "person_edited_publication",
                                "publicationKey"
                        )
                );
            } else {
                condition = new SubQueryCondition(
                        "person_edited_publication",
                        "publicationKey",
                        subQuery
                );
            }
        } else if (ctx.WORKS_FOR() != null) {
            sqlQuery.addJoin(
                    "person_works_for_institution",
                    "personKey",
                    "person",
                    "dblpKey"
            );

            sqlQuery.addJoin(
                    "institution",
                    "key",
                    "person_works_for_institution",
                    "institutionKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("institution", "key");
            InstitutionVisitor iv = new InstitutionVisitor();
            iv.visitInstitution(ctx.institution(), subQuery);
            condition = new SubQueryCondition(
                    "institution",
                    "key",
                    subQuery

            );
        }

        // Not possible with current dataset
        if (ctx.PUBLISHED_WITH() != null) {
            // Currently same as WORKS FOR
            sqlQuery.addJoin(
                    "person_works_for_institution",
                    "personKey",
                    "person",
                    "dblpKey"
            );

            sqlQuery.addJoin(
                    "institution",
                    "key",
                    "person_works_for_institution",
                    "institutionKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("institution", "key");
            InstitutionVisitor iv = new InstitutionVisitor();
            iv.visitInstitution(ctx.institution(), subQuery);
            condition = new SubQueryCondition(
                    "institution",
                    "key",
                    subQuery

            );
        } else if (ctx.PUBLISHED_IN() != null) {
            sqlQuery.addJoin(
                    "person_authored_publication",
                    "personKey",
                    "person",
                    "dblpKey"
            );
            sqlQuery.addJoin(
                    "publication",
                    "dblpKey",
                    "person_authored_publication",
                    "publicationKey"
            );

            if (ctx.conference() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("publication", "conference_dblpKey");
                ConferenceVisitor cv = new ConferenceVisitor();
                cv.visitConference(ctx.conference(), subQuery);
                condition = new SubQueryCondition(
                        "publication",
                        "conference_dblpKey",
                        subQuery
                );
            } else if (ctx.journal() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("publication", "journal_dblpKey");
                JournalVisitor jv = new JournalVisitor();
                jv.visitJournal(ctx.journal(), subQuery);
                condition = new SubQueryCondition(
                        "publication",
                        "journal_dblpKey",
                        subQuery
                );
            } else if (ctx.DBLP_KEY() != null) {
                ConditionGroup condGroup = new ConditionGroup();
                BooleanCondition cond1 = new BooleanCondition(
                        "publication",
                        "journal_dblpKey",
                        BooleanOperator.EQUALS,
                        ctx.DBLP_KEY().getText()
                );
                BooleanCondition cond2 = new BooleanCondition(
                        "publication",
                        "conference_dblpKey",
                        BooleanOperator.EQUALS,
                        ctx.DBLP_KEY().getText()
                );
                cond2.or();
                condGroup.addCondition(cond1);
                condGroup.addCondition(cond2);
                condition = condGroup;
            } else {
                ConditionGroup condGroup = new ConditionGroup();

                Query journalSubQuery = new Query();
                journalSubQuery.addFrom("journal");
                journalSubQuery.addCondition(
                        new BooleanCondition(
                                "journal",
                                "acronym",
                                BooleanOperator.EQUALS,
                                ctx.STRING().getText()
                        )
                );
                Query conferenceSubQuery = new Query();
                conferenceSubQuery.addFrom("conference");
                conferenceSubQuery.addCondition(
                        new BooleanCondition(
                                "conference",
                                "acronym",
                                BooleanOperator.EQUALS,
                                ctx.STRING().getText()
                        )
                );

                SubQueryCondition cond1 = new SubQueryCondition(
                        "publication",
                        "journal_dblpKey",
                        journalSubQuery
                );
                SubQueryCondition cond2 = new SubQueryCondition(
                        "publication",
                        "conference_dblpKey",
                        conferenceSubQuery
                );
                cond2.or();

                condGroup.addCondition(cond1);
                condGroup.addCondition(cond2);

                sqlQuery.addCondition(condGroup);
            }
        } else if (ctx.CITED_BY() != null) {
            sqlQuery.addJoin(
                    "person_authored_publication",
                    "personKey",
                    "person",
                    "dblpKey"
            );
            sqlQuery.addJoin(
                    "publication_references",
                    "pub_id",
                    "person_authored_publication",
                    "publicationKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            condition = new SubQueryCondition(
                    "publication_references",
                    "pub2_id",
                    subQuery
            );
        } else if (ctx.REFERENCES() != null) {
            sqlQuery.addJoin(
                    "person_authored_publication",
                    "personKey",
                    "person",
                    "dblpKey"
            );
            sqlQuery.addJoin(
                    "publication_references",
                    "pub_id",
                    "person_authored_publication",
                    "publicationKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            condition = new SubQueryCondition(
                    "publication_references",
                    "pub_id",
                    subQuery
            );
        } else if (ctx.ORCID() != null) {
            condition = new BooleanCondition(
                    "person",
                    "orcid",
                    BooleanOperator.EQUALS,
                    ctx.ORCID_VALUE().getText()
            );
        } else if (ctx.WBC() != null) {
            System.out.println("primaryName: Christin Katharina Kreutz, orcid: 0000-0002-5075-7699");
            System.out.println("primaryName: Michael Wolz, orcid: 0000-0002-9313-7131");
            System.exit(1);
        }

        if (condition != null) {
            if (ctx.logicalOperator() != null) {
                if (ctx.logicalOperator().or() != null) {
                    condition.or();
                }
                if (ctx.logicalOperator().not() != null) {
                    condition.negate();
                }
            }
            sqlQuery.addCondition(condition);
        }
    }
}
