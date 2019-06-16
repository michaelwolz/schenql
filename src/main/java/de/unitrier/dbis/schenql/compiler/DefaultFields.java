package de.unitrier.dbis.schenql.compiler;

public class DefaultFields {
    public final static String[] conference = new String[]{
            "`conference`.`acronym`",
//            "`conference`.`corerank`"
    };

    public final static String[] institution = new String[]{
            "`institution`.`primaryName`",
            "`institution`.`location`"
    };

    public final static String[] journal = new String[]{
            "`journal`.`acronym`"
    };

    public final static String[] keyword = new String[]{
            "`keyword`.`keyword`"
    };

    public final static String[] person = new String[]{
            "`person`.`primaryName`",
            "`person`.`orcid`",
//            "`person`.`h-index`"
    };

    public final static String[] publication = new String[]{
            "`publication`.`title`",
            "`publication`.`year`",
    };
}
