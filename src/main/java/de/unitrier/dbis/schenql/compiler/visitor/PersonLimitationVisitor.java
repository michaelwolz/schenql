package de.unitrier.dbis.schenql.compiler.visitor;

import de.unitrier.dbis.schenql.SchenqlParser;
import de.unitrier.dbis.schenql.SchenqlParserBaseVisitor;
import de.unitrier.dbis.schenql.compiler.Helper;
import de.unitrier.dbis.schenql.compiler.Join;
import de.unitrier.dbis.schenql.compiler.QueryLimitation;

import java.util.ArrayList;

public class PersonLimitationVisitor extends SchenqlParserBaseVisitor<QueryLimitation> {
    @Override
    public QueryLimitation visitPersonLimitation(SchenqlParser.PersonLimitationContext ctx) {
        QueryLimitation ql = new QueryLimitation();

        if (ctx.NAMED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_names`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    )
            });
            ql.setLimitation("`person_names`.`name` " + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.AUTHORED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_authored_publication`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    )
            });
            PublicationVisitor pv = new PublicationVisitor();
            ql.setLimitation("`person_authored_publication`.`publicationKey` IN (" +
                    pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.EDITED() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_edited_publication`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    )
            });
            PublicationVisitor pv = new PublicationVisitor();
            ql.setLimitation("`person_edited_publication`.`publicationKey` IN (" +
                    pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.WORKS_FOR() != null) {
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_works_for_institution`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    ),
                    new Join(
                            "`institution`",
                            "`key`",
                            "`person_works_for_institution`.`institutionKey`"
                    ),
                    new Join(
                            "`institution_name`",
                            "`institutionKey`",
                            "`institution`.`key`"
                    )
            });

            ql.setLimitation("`institution_name`.`name`" + Helper.sqlStringComparison(ctx.STRING().getText()));
            return ql;
        }

        if (ctx.PUBLISHED_WITH() != null) {
            return ql;
        }

        if (ctx.PUBLISHED_IN() != null) {
            return ql;
        }

        if (ctx.CITED_BY() != null) {
            PublicationVisitor pv = new PublicationVisitor();
            ql.setJoins(new Join[]{
                    new Join(
                            "`person_authored_publication`",
                            "`personKey`",
                            "`person`.`dblpKey`"
                    ),
                    new Join(
                            "`publication_references`",
                            "`pub_id`",
                            "`person_authored_publication`.`publicationKey`"
                    )
            });
            ql.setGroupBy(new ArrayList<>() {
                {
                    add("`person`.`dblpKey`");
                }
            });
            ql.setLimitation("`publication_references`.`pub2_id` IN (" + pv.visitPublication(ctx.publication()) + ")");
            return ql;
        }

        if (ctx.CITES() != null) {

        }

        return null;
    }
}
