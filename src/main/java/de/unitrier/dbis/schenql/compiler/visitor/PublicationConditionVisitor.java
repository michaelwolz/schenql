package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.*;

class PublicationConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitPublicationCondition(SchenqlParser.PublicationConditionContext ctx, Query sqlQuery) {
        Condition condition = null;

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
            subQuery.distinct();
            subQuery.addSelect("person", "dblpKey");
            PersonVisitor pv = new PersonVisitor();
            pv.visitPerson(ctx.person(), subQuery);
            condition = new SubQueryCondition(
                    "person",
                    "dblpKey",
                    subQuery
            );
        } else if (ctx.EDITED_BY() != null) {
            // TODO: What if WRITTEN BY and EDITED BY is used in the same query?
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
            subQuery.distinct();
            subQuery.addSelect("person", "dblpKey");
            PersonVisitor pv = new PersonVisitor();
            pv.visitPerson(ctx.person(), subQuery);
            condition = new SubQueryCondition(
                    "person",
                    "dblpKey",
                    subQuery
            );
        } else if (ctx.PUBLISHED_BY() != null) {
            // Note: Data is not sufficient for this
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

            condition = new SubQueryCondition(
                    "person_works_for_institution",
                    "institutionKey",
                    subQuery
            );
        } else if (ctx.ABOUT() != null) {
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

                condition = new SubQueryCondition(
                        "publication_has_keyword",
                        "keyword",
                        subQuery
                );
            } else {
                condition = new FulltextCondition(
                        "publication",
                        "abstract",
                        ctx.STRING().getText()
                );
            }
        } else if (ctx.BEFORE() != null) {
            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.LT,
                    Integer.parseInt(ctx.YEAR().getText())
            );
        } else if (ctx.AFTER() != null) {
            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.GT,
                    Integer.parseInt(ctx.YEAR().getText())
            );
        } else if (ctx.IN_YEAR() != null) {
            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.EQUALS,
                    Integer.parseInt(ctx.YEAR().getText())
            );
        } else if (ctx.APPEARED_IN() != null) {
            if (ctx.journal() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("journal", "dblpKey");
                JournalVisitor jv = new JournalVisitor();
                jv.visitJournal(ctx.journal(), subQuery);

                condition = new SubQueryCondition(
                        "publication",
                        "journal_dblpKey",
                        subQuery
                );
            } else if (ctx.conference() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("conference", "dblpKey");
                ConferenceVisitor cv = new ConferenceVisitor();
                cv.visitConference(ctx.conference(), subQuery);

                condition = new SubQueryCondition(
                        "publication",
                        "conference_dblpKey",
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
            } else if (ctx.STRING() != null) {
                ConditionGroup condGroup = new ConditionGroup();

                Query journalSubQuery = new Query();
                journalSubQuery.addSelect("journal", "dblpKey");
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
                conferenceSubQuery.addSelect("conference", "dblpKey");
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
                condition = condGroup;
            }
        } else if (ctx.CITED_BY() != null) {
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

            condition = new SubQueryCondition(
                    "publication_references",
                    "pub_id",
                    subQuery
            );
        } else if (ctx.REFERENCES() != null) {
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

            condition = new SubQueryCondition(
                    "publication_references",
                    "pub2_id",
                    subQuery
            );
        } else if (ctx.TITLE() != null) {
            if (ctx.TILDE() != null) {
                condition = new FulltextCondition(
                        "publication",
                        "title",
                        ctx.STRING().getText()
                );
            } else {
                condition = new BooleanCondition(
                        "publication",
                        "title",
                        BooleanOperator.EQUALS,
                        ctx.STRING().getText()
                );
            }
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
