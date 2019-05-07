/*
 * This file contains the grammar for the SchenQL query language
 */

parser grammar SchenQLParser;

options { tokenVocab=SchenQLLexer; }

@header {
package de.unitrier.dbis.schenql
}

query
    : (aggregateFunction
    | publicationQuery | personQuery | institutionQuery | conferenceQuery | journalQuery)
    (LIMIT NUMBER)?
    SEMI
    ;

// Publications
publicationQuery
    : publicationAggregateFunction | PUBLICATION publicationLimitation*
    ;

publicationLimitation
    : WRITTEN_BY person | EDITED_BY person | PUBLISHED_BY institution | ABOUT keywords+
    | BEFORE YEAR | AFTER YEAR | IN_YEAR YEAR | APPEARED_IN journal
    | CITED_BY publication | CITES publication | TITLE STRING
    ;

publication
    : LR_BRACKET publicationQuery RR_BRACKET | STRING
    ;

// Persons
personQuery
    : PERSON
    personLimitation*
    ;

personLimitation
    : NAMED STRING | AUTHORED publication | EDITED publication | WORKS_FOR institution
    | PUBLISHED_WITH institution | PUBLISHED_IN (conference | journal) | CITED_BY publication
    | CITES publication
    ;

person
    : LR_BRACKET personQuery RR_BRACKET | STRING | aggregateFunction; // Orcid

// Institutions

institutionQuery
    : INSTITUTION
    institutionLimitation*
    ;

institutionLimitation
    : NAMED STRING | IN_YEAR YEAR | CITY STRING | COUNTRY STRING | MEMBERS person
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
    : NAMED STRING | ACRONYM STRING | ABOUT STRING | AFTER YEAR
    | BEFORE YEAR | IN_YEAR YEAR | CITY STRING | COUNTRY STRING
    ;

conference
    : LR_BRACKET conferenceQuery RR_BRACKET | STRING
    ;

// Journals
journalQuery
    : JOURNAL
    journalLimitation*
    ;

journalLimitation
    : NAMED STRING | ACRONYM STRING | ABOUT STRING | AFTER YEAR
    | BEFORE YEAR | IN_YEAR YEAR | VOLUME STRING
    ;

journal
    : LR_BRACKET journalQuery RR_BRACKET | STRING
    ;

// Aggregate Function
aggregateFunction
    : COUNT LR_BRACKET query RR_BRACKET
    ;

publicationAggregateFunction
    : MOST_CITED LR_BRACKET publicationQuery RR_BRACKET
    ;



// Other
keywords
    : SL_BRACKET (STRING COMMA)* STRING SR_BRACKET | STRING
    ;