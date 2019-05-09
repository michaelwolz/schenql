package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;

public class QueryVisitor extends SchenqlParserBaseVisitor<String> {
    @Override
    public String visitQuery(SchenqlParser.QueryContext ctx) {
        String query = "";

        if (ctx.conferenceQuery() != null) {
            ConferenceQueryVisitor cqv = new ConferenceQueryVisitor();
            query += cqv.visitConferenceQuery(ctx.conferenceQuery());
        }

        if (ctx.institutionQuery() != null) {
            InstitutionQueryVisitor iqv = new InstitutionQueryVisitor();
            query += iqv.visitInstitutionQuery(ctx.institutionQuery());
        }

        if (ctx.journalQuery() != null) {
            JournalQueryVisitor jqv = new JournalQueryVisitor();
            query += jqv.visitJournalQuery(ctx.journalQuery());
        }

        if (ctx.personQuery() != null) {
            PersonQueryVisitor pqv = new PersonQueryVisitor();
            query += pqv.visitPersonQuery(ctx.personQuery());
        }

        if (ctx.publicationQuery() != null) {
            PublicationQueryVisitor pqv = new PublicationQueryVisitor();
            query += pqv.visitPublicationQuery(ctx.publicationQuery());
        }

        return query;
    }
}
