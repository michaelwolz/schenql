package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;

class QueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitQuery(SchenqlParser.QueryContext ctx, Query sqlQuery) {
        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor cqv = new ConferenceQueryVisitor();
            cqv.visitConferenceQuery(ctx.conferenceQuery(), sqlQuery);
        }

        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor iqv = new InstitutionQueryVisitor();
            iqv.visitInstitutionQuery(ctx.institutionQuery(), sqlQuery);
        }

        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            jqv.visitJournalQuery(ctx.journalQuery(), sqlQuery);
        }

        if (ctx.keywordQuery() != null) {
            KeywordQueryVisitor kqv = new KeywordQueryVisitor();
            kqv.visitKeywordQuery(ctx.keywordQuery(), sqlQuery);
        }

        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            pqv.visitPersonQuery(ctx.personQuery(), sqlQuery);
        }

        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            pqv.visitPublicationQuery(ctx.publicationQuery(), sqlQuery);
        }
    }
}
