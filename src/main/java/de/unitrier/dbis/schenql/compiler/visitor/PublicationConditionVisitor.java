package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.SubQueryJoin;
import de.unitrier.dbis.sqlquerybuilder.condition.*;

class PublicationConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPublicationCondition(SchenqlParser.PublicationConditionContext ctx, Query sqlQuery) {
        if (ctx.WRITTEN_BY() != null) {
            sqlQuery.addJoin(
                    "person_authored_publication",
                    "publicationKey",
                    "publication",
                    "dblpKey"
            );
            sqlQuery.addJoin(
                    "person",
                    "dblpKey",
                    "person_authored_publication",
                    "personKey"
            );
            Query subQuery = new Query();
            PersonVisitor pv = new PersonVisitor();
            pv.visitPerson(ctx.person(), subQuery);
            sqlQuery.addJoin(
                    new SubQueryJoin(
                            subQuery,
                            "person_subquery",
                            "dblpKey",
                            "person",
                            "personKey"
                    )
            );
        }

        // TODO: What if WRITTEN BY and EDITED BY is used in the same query?
        if (ctx.EDITED_BY() != null) {
            sqlQuery.addJoin(
                    "person_edited_publication",
                    "publicationKey",
                    "publication",
                    "dblpKey"
            );
            sqlQuery.addJoin(
                    "person",
                    "dblpKey",
                    "person_edited_publication",
                    "personKey"
            );
            Query subQuery = new Query();
            PersonVisitor pv = new PersonVisitor();
            pv.visitPerson(ctx.person(), subQuery);
            sqlQuery.addJoin(
                    new SubQueryJoin(
                            subQuery,
                            "person_subquery",
                            "dblpKey",
                            "person",
                            "personKey"
                    )
            );
        }

        // Note: Data is not sufficient for this
        if (ctx.PUBLISHED_BY() != null) {
            sqlQuery.addJoin(
                    "person_authored_publication",
                    "publicationKey",
                    "publication",
                    "dblpKey"
            );
            sqlQuery.addJoin(
                    "person",
                    "dblpKey",
                    "person_authored_publication",
                    "personKey"
            );
            sqlQuery.addJoin(
                    "person_works_for_institution",
                    "personKey",
                    "person",
                    "dblpKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("institution", "key");
            InstitutionVisitor iv = new InstitutionVisitor();
            iv.visitInstitution(ctx.institution(), subQuery);

            sqlQuery.addCondition(
                    new SubQueryCondition(
                            "person_works_for_institution",
                            "institutionKey",
                            subQuery
                    )
            );
        }

        if (ctx.ABOUT() != null) {
            if (ctx.KEYWORD() != null) {
                sqlQuery.addJoin(
                        "publication_has_keyword",
                        "dblpKey",
                        "publication",
                        "dblpKey"
                );
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("keyword", "keyword");
                KeywordVisitor kv = new KeywordVisitor();
                kv.visitKeyword(ctx.keyword(), subQuery);

                sqlQuery.addCondition(
                        new SubQueryCondition(
                                "publication_has_keyword",
                                "keyword",
                                subQuery
                        )
                );
            } else {
                sqlQuery.addCondition(
                        new FulltextCondition(
                                "publication",
                                "abstract",
                                ctx.STRING().getText()
                        )
                );
            }
        }

        if (ctx.BEFORE() != null) {
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.LT,
                            Integer.parseInt(ctx.YEAR().getText())
                    )
            );
        }

        if (ctx.AFTER() != null) {
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.GT,
                            Integer.parseInt(ctx.YEAR().getText())
                    )
            );
        }

        if (ctx.IN_YEAR() != null) {
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.EQUALS,
                            Integer.parseInt(ctx.YEAR().getText())
                    )
            );
        }

        if (ctx.APPEARED_IN() != null) {
            if (ctx.journal() != null) {
                sqlQuery.addJoin(
                        "journal_name",
                        "journal_key",
                        "publication",
                        "dblpKey"
                );
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("journal", "dblpKey");
                JournalVisitor jv = new JournalVisitor();
                jv.visitJournal(ctx.journal(), subQuery);

                sqlQuery.addCondition(
                        new SubQueryCondition(
                                "journal_name",
                                "journal_dblpKey",
                                subQuery
                        )
                );
            } else if (ctx.conference() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("journal", "dblpKey");
                ConferenceVisitor cv = new ConferenceVisitor();
                cv.visitConference(ctx.conference(), subQuery);

                sqlQuery.addCondition(
                        new SubQueryCondition(
                                "publication",
                                "conference_dblpKey",
                                subQuery
                        )
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
                sqlQuery.addCondition(condGroup);
            } else if (ctx.STRING() != null) {
                ConditionGroup condGroup = new ConditionGroup();

                Query journalSubQuery = new Query();
                sqlQuery.addFrom("journal");
                sqlQuery.addCondition(
                        new BooleanCondition(
                                "journal",
                                "acronym",
                                BooleanOperator.EQUALS,
                                ctx.STRING().getText()
                        )
                );
                Query conferenceSubQuery = new Query();
                sqlQuery.addFrom("conference");
                sqlQuery.addCondition(
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
        }

        if (ctx.CITED_BY() != null) {
            sqlQuery.addJoin(
                    "publication_references",
                    "pub2_id",
                    "publication",
                    "dblpKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            sqlQuery.addGroupBy("publication", "dblpkey");

            sqlQuery.addCondition(
                    new SubQueryCondition(
                            "publication_references",
                            "pub_id",
                            subQuery
                    )
            );
        }

        if (ctx.REFERENCES() != null) {
            sqlQuery.addJoin(
                    "publication_references",
                    "pub_id",
                    "publication",
                    "dblpKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            sqlQuery.addGroupBy("publication", "dblpkey");

            sqlQuery.addCondition(
                    new SubQueryCondition(
                            "publication_references",
                            "pub2_id",
                            subQuery
                    )
            );
        }

        if (ctx.TITLE() != null) {
            if (ctx.TILDE() != null) {
                sqlQuery.addCondition(
                        new FulltextCondition(
                                "publication",
                                "title",
                                ctx.STRING().getText()
                        )
                );
            } else {
                sqlQuery.addCondition(
                        new BooleanCondition(
                                "publication",
                                "title",
                                BooleanOperator.EQUALS,
                                ctx.STRING().getText()
                        )
                );
            }
        }
    }
}
