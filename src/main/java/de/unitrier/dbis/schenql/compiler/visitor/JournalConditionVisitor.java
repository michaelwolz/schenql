package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class JournalConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitJournalCondition(SchenqlParser.JournalConditionContext ctx, Query sqlQuery) {
        if (ctx.NAMED() != null) {
            sqlQuery.addJoin(
                    "journal_name",
                    "journalKey",
                    "journal",
                    "dblpKey"
            );
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "journal",
                            "name",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }

        if (ctx.ACRONYM() != null) {
            sqlQuery.addCondition(
                    new BooleanCondition(
                            "journal",
                            "acronym",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }

        if (ctx.ABOUT() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );
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
        }

        if (ctx.AFTER() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.GT,
                            Integer.parseInt(ctx.YEAR().getText())
                    )
            );
        }

        if (ctx.BEFORE() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.LT,
                            Integer.parseInt(ctx.YEAR().getText())
                    )
            );
        }

        if (ctx.IN_YEAR() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "year",
                            BooleanOperator.EQUALS,
                            Integer.parseInt(ctx.YEAR().getText())
                    )
            );
        }

        if (ctx.VOLUME() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            sqlQuery.addCondition(
                    new BooleanCondition(
                            "publication",
                            "volume",
                            BooleanOperator.EQUALS,
                            ctx.STRING().getText()
                    )
            );
        }

        if (ctx.OF() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            Query subQuery = new Query();
            subQuery.distinct();
            subQuery.addSelect("publication", "journal_dblpKey");
            PublicationVisitor pv = new PublicationVisitor();
            pv.visitPublication(ctx.publication(), subQuery);

            sqlQuery.addCondition(
                    new SubQueryCondition(
                            "journal",
                            "dblpKey",
                            subQuery
                    )
            );
        }
    }
}
