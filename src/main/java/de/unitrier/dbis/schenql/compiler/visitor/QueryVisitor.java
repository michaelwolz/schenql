package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.Select;

class QueryVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitQuery(SchenqlParser.QueryContext ctx, Query sqlQuery) {
        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor cqv = new ConferenceQueryVisitor();
            cqv.visitConferenceQuery(ctx.conferenceQuery(), sqlQuery);
        }

        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor iqv = new InstitutionQueryVisitor();
            if (selectFields != null) {
                iqv.visitInstitutionQuery(ctx.institutionQuery(), sqlQuery, selectFields);
            } else {
                iqv.visitInstitutionQuery(ctx.institutionQuery(), sqlQuery, null);
            }
        }

        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            if (selectFields != null) {
                jqv.visitJournalQuery(ctx.journalQuery(), sqlQuery, selectFields);
            } else {
                jqv.visitJournalQuery(ctx.journalQuery(), sqlQuery, null);
            }
        }

        if (ctx.keywordQuery() != null) {
            KeywordQueryVisitor kqv = new KeywordQueryVisitor();
            if (selectFields != null) {
                kqv.visitKeywordQuery(ctx.keywordQuery(), sqlQuery, selectFields);
            } else {
                kqv.visitKeywordQuery(ctx.keywordQuery(), sqlQuery, null);
            }
        }

        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            if (selectFields != null) {
                pqv.visitPersonQuery(ctx.personQuery(), sqlQuery, selectFields);
            } else {
                pqv.visitPersonQuery(ctx.personQuery(), sqlQuery, null);
            }
        }

        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            if (selectFields != null) {
                pqv.visitPublicationQuery(ctx.publicationQuery(), sqlQuery, selectFields);
            } else {
                pqv.visitPublicationQuery(ctx.publicationQuery(), sqlQuery, null);
            }
        }
    }
}
