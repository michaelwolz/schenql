package de.unitrier.dbis.schenql.compiler;

public final class ExtendedFields {
    public final static String[] conference = new String[]{
            "dblpKey",
            "acronym"
    };

    public final static String[] institution = new String[]{
            "key",
            "primaryName",
            "location",
            "country",
            "city",
            "lat",
            "lon"
    };

    public final static String[] journal = new String[]{
            "dblpKey",
            "acronym"
    };

    public final static String[] keyword = new String[]{
            "keyword"
    };

    public final static String[] person = new String[]{
            "dblpKey",
            "primaryName",
            "orcid",
            "h-index"
    };

    public final static String[] publication = new String[]{
            "dblpKey",
            "title",
            "abstract",
            "ee",
            "url",
            "year",
            "type",
            "conference_dblpKey",
            "journal_dblpKey"
    };
}
