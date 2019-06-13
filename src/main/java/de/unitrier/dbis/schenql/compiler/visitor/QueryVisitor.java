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
            return query;
        }

        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor iqv = new InstitutionQueryVisitor();
            if (selectFields != null) {
                query += iqv.visitInstitutionQuery(ctx.institutionQuery(), selectFields);
            } else {
                query += iqv.visitInstitutionQuery(ctx.institutionQuery());
            }
            return query;
        }

        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            if (selectFields != null) {
                query += jqv.visitJournalQuery(ctx.journalQuery(), selectFields);
            } else {
                query += jqv.visitJournalQuery(ctx.journalQuery());
            }
            return query;
        }

        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            if (selectFields != null) {
                query += pqv.visitPersonQuery(ctx.personQuery(), selectFields);
            } else {
                query += pqv.visitPersonQuery(ctx.personQuery());
            }
            return query;
        }

        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            if (selectFields != null) {
                query += pqv.visitPublicationQuery(ctx.publicationQuery(), selectFields);
            } else {
                query += pqv.visitPublicationQuery(ctx.publicationQuery());
            }
            return query;
        }

        return query;
    }
}
