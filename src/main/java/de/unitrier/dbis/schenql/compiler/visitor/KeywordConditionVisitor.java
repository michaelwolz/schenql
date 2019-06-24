package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class KeywordConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitKeywordCondition(SchenqlParser.KeywordConditionContext ctx, Query sqlQuery) {
        if (ctx.OF() != null) {
            sqlQuery.addJoin(
                    "publication_has_keyword",
                    "keyword",
                    "keyword",
                    "keyword"
            );
            sqlQuery.addJoin(
                    "publication",
                    "dblpKey",
                    "publication_has_keyword",
                    "dblpKey"

            );

            if (ctx.conference() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("conference", "dblpKey");
                ConferenceVisitor cv = new ConferenceVisitor();
                cv.visitConference(ctx.conference(), subQuery);
                sqlQuery.addCondition(
                        new SubQueryCondition("publication", "conference_dblpKey", subQuery)
                );
            } else if (ctx.journal() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("journal", "dblpKey");
                JournalVisitor jv = new JournalVisitor();
                jv.visitJournal(ctx.journal(), subQuery);
                sqlQuery.addCondition(
                        new SubQueryCondition("publication", "journal_dblpKey", subQuery)
                );
            } else if (ctx.person() != null) {
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

                sqlQuery.addCondition(
                        new SubQueryCondition("person", "dblpKey", subQuery)
                );
            } else if (ctx.publication() != null) {
                Query subQuery = new Query();
                subQuery.distinct();
                subQuery.addSelect("publication", "dblpKey");
                PublicationVisitor pv = new PublicationVisitor();
                pv.visitPublication(ctx.publication(), subQuery);
                sqlQuery.addCondition(
                        new SubQueryCondition("publication", "dblpKey", subQuery)
                );
            }
        }
    }
}
