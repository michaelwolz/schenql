package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

public class PublicationLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitPublicationLimitation(SchenqlParser.PublicationLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        // Written By
        if (ctx.WRITTEN_BY() != null) {
            ql.setJoins(new String[]{"`person_authored_publication`", "`person_names`"});
            ql.setLimitation("`person_names`.`name` = " + ctx.person().getText());
            return ql;
        }

        // Edited By
        if (ctx.EDITED_BY() != null) {
            ql.setJoins(new String[]{"`person_edited_publication`", "`person_names`"});
            ql.setLimitation("`person_names`.`name` = " + ctx.person().getText());
            return ql;
        }

        // Published by
        if (ctx.PUBLISHED_BY() != null) {
        }

        // About
        if (ctx.ABOUT() != null) {
            ql.setJoins(new String[]{"`publication_has_keyword`"});

            KeywordVisitor kv = new KeywordVisitor();
            String keywords = kv.visitKeywords(ctx.keywords());

            // Comparison-Type is implemented in KeywordVisitor for this case
            ql.setLimitation("`publication_has_keyword`.`keyword`" + keywords);
            return ql;
        }

        // Before
        if (ctx.BEFORE() != null) {
            ql.setLimitation("`publication`.`year` <= " + ctx.YEAR().getText());
            return ql;
        }

        // After
        if (ctx.AFTER() != null) {
            ql.setLimitation("`publication`.`year` >= " + ctx.YEAR().getText());
            return ql;
        }

        // In Year
        if (ctx.IN_YEAR() != null) {
            ql.setLimitation("`publication`.`year` = " + ctx.YEAR().getText());
            return ql;
        }

        // Appeared In
        if (ctx.APPEARED_IN() != null) {
        }

        // Cited By
        if (ctx.CITED_BY() != null) {
            PublicationVisitor pv = new PublicationVisitor();
            ql.setJoins(new String[]{"publication_references", "publication"});
            ql.setLimitation("`publication_references`.`pub2` = " + pv.visitPublication(ctx.publication()));
            return ql;
        }

        // Cites
        if (ctx.CITES() != null) {
            PublicationVisitor pv = new PublicationVisitor();
            ql.setJoins(new String[]{"publication_references", "publication"});
            ql.setLimitation("`publication_references`.`pub1` = " + pv.visitPublication(ctx.publication()));
            return ql;
        }

        // Title
        if (ctx.TITLE() != null) {
            ql.setLimitation("`publication`.`title` LIKE \"%" + ctx.TITLE().getText() + "%\"");
        }

        return null;
    }
}
