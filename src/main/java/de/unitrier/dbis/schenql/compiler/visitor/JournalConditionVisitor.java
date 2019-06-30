package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.sqlquerybuilder.Query;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanCondition;
import de.unitrier.dbis.sqlquerybuilder.condition.BooleanOperator;
import de.unitrier.dbis.sqlquerybuilder.condition.Condition;
import de.unitrier.dbis.sqlquerybuilder.condition.SubQueryCondition;

class JournalConditionVisitor extends SchenqlParserBaseVisitor<Void> {
    void visitJournalCondition(SchenqlParser.JournalConditionContext ctx, Query sqlQuery) {
        Condition condition = null;

        if (ctx.NAMED() != null) {
            sqlQuery.addJoin(
                    "journal_name",
                    "journalKey",
                    "journal",
                    "dblpKey"
            );
            condition = new BooleanCondition(
                    "journal",
                    "name",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()
            );
        } else if (ctx.ACRONYM() != null) {
            condition = new BooleanCondition(
                    "journal",
                    "acronym",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()
            );
        } else if (ctx.ABOUT() != null) {
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

            condition = new SubQueryCondition(
                    "publication_has_keyword",
                    "keyword",
                    subQuery
            );
        } else if (ctx.AFTER() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.GT,
                    Integer.parseInt(ctx.YEAR().getText())
            );
        } else if (ctx.BEFORE() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.LT,
                    Integer.parseInt(ctx.YEAR().getText())
            );
        } else if (ctx.IN_YEAR() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            condition = new BooleanCondition(
                    "publication",
                    "year",
                    BooleanOperator.EQUALS,
                    Integer.parseInt(ctx.YEAR().getText())
            );
        } else if (ctx.VOLUME() != null) {
            sqlQuery.addJoin(
                    "publication",
                    "journal_dblpKey",
                    "journal",
                    "dblpKey"
            );

            condition = new BooleanCondition(
                    "publication",
                    "volume",
                    BooleanOperator.EQUALS,
                    ctx.STRING().getText()
            );
        } else if (ctx.OF() != null) {
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

            condition = new SubQueryCondition(
                    "journal",
                    "dblpKey",
                    subQuery
            );
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
