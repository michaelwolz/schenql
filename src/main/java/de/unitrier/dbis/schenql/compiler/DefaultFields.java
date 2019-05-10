package de.unitrier.dbis.schenql.compiler;

public class DefaultFields {
    public final static String[] publication = new String[]{
            "`publication`.`title`",
            "`publication`.`year`",
    };

    public final static String[] person = new String[]{
            "`person`.`orcid`",
            "`person`.`h-index`",
            "`person`.`primary_name`"
    };

    public final static String[] institution = new String[]{
            ""
    };

    public final static String[] conference = new String[]{
            ""
    };

    public final static String[] journal = new String[]{
            "`journal`.`acronym`"
    };
}
