package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class QueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitQuery(SchenqlParser.QueryContext ctx) {
        return visitQuery(ctx, null);
    }

    String visitQuery(SchenqlParser.QueryContext ctx, String[] selectFields) {
        String query = "";

        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor cqv = new ConferenceQueryVisitor();
            if (selectFields != null) {
                query += cqv.visitConferenceQuery(ctx.conferenceQuery(), selectFields);
            } else {
                query += cqv.visitConferenceQuery(ctx.conferenceQuery());
            }
        }

        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor iqv = new InstitutionQueryVisitor();
            if (selectFields != null) {
                query += iqv.visitInstitutionQuery(ctx.institutionQuery(), selectFields);
            } else {
                query += iqv.visitInstitutionQuery(ctx.institutionQuery());
            }
        }

        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            if (selectFields != null) {
                query += jqv.visitJournalQuery(ctx.journalQuery(), selectFields);
            } else {
                query += jqv.visitJournalQuery(ctx.journalQuery());
            }
        }

        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            if (selectFields != null) {
                query += pqv.visitPersonQuery(ctx.personQuery(), selectFields);
            } else {
                query += pqv.visitPersonQuery(ctx.personQuery());
            }
        }

        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            if (selectFields != null) {
                query += pqv.visitPublicationQuery(ctx.publicationQuery(), selectFields);
            } else {
                query += pqv.visitPublicationQuery(ctx.publicationQuery());
            }
        }

        return query;
    }
}
