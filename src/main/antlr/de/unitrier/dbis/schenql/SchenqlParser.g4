/*
 * This file contains the grammar for the SchenQL query language
 */

parser grammar SchenqlParser;

options { tokenVocab=SchenqlLexer; }

@header {
package de.unitrier.dbis.schenql;
}

root
    : (aggregateFunction | query | attributeOf)
    SEMI
    ;

query
    : (publicationQuery | personQuery | institutionQuery | conferenceQuery | journalQuery)
    (LIMIT NUMBER)?
    ;

// Publications
publicationQuery
    : publicationAggregateFunction | PUBLICATION publicationLimitation*
    ;

publicationLimitation
    : WRITTEN_BY person | EDITED_BY person | PUBLISHED_BY institution | ABOUT keywords
    | BEFORE YEAR | AFTER YEAR | IN_YEAR YEAR | APPEARED_IN journal
    | CITED_BY publication | CITES publication | TITLE STRING
    ;

publication
    : LR_BRACKET publicationQuery RR_BRACKET | DBLP_KEY | STRING
    ;

// Persons
personQuery
    : PERSON
    personLimitation*
    ;

personLimitation
    : NAMED STRING | AUTHORED publication | EDITED publication | WORKS_FOR institution
    | PUBLISHED_WITH institution | PUBLISHED_IN (STRING | DBLP_KEY | conference | journal) | CITED_BY publication
    | CITES publication | ORCID ORCID_VALUE
    ;

person
    : LR_BRACKET personQuery RR_BRACKET | DBLP_KEY | ORCID_VALUE | STRING; // Orcid

// Institutions

institutionQuery
    : INSTITUTION
    institutionLimitation*
    ;

institutionLimitation
    : NAMED STRING | CITY STRING | COUNTRY STRING | MEMBERS person
    ;

institution
    : LR_BRACKET institutionQuery RR_BRACKET | STRING
    ;

// Conferences
conferenceQuery
    : CONFERENCE
    conferenceLimitation*
    ;

conferenceLimitation
    : NAMED STRING | ACRONYM STRING | ABOUT keywords | AFTER YEAR
    | BEFORE YEAR | IN_YEAR YEAR | CITY STRING | COUNTRY STRING
    ;

conference
    : LR_BRACKET conferenceQuery RR_BRACKET | STRING | DBLP_KEY
    ;

// Journals
journalQuery
    : JOURNAL
    journalLimitation*
    ;

journalLimitation
    : NAMED STRING | ACRONYM STRING | ABOUT keywords | AFTER YEAR
    | BEFORE YEAR | IN_YEAR YEAR | VOLUME STRING
    ;

journal
    : LR_BRACKET journalQuery RR_BRACKET | STRING | DBLP_KEY
    ;

// Aggregate Function
aggregateFunction
    : COUNT LR_BRACKET query RR_BRACKET
    ;

publicationAggregateFunction
    : MOST_CITED LR_BRACKET publicationQuery RR_BRACKET
    ;

// Attributes
attributeOf
    : STRING OF query
    ;


// Other
keywords
    : SL_BRACKET (STRING COMMA)* STRING SR_BRACKET
    | STRING
    ;