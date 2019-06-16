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
    : (publicationQuery | personQuery | institutionQuery | conferenceQuery | journalQuery | keywordQuery)
    (LIMIT NUMBER)?
    ;

// Publications
publicationQuery
    : publicationAggregateFunction | PUBLICATION publicationLimitation*
    ;

publicationLimitation
    : WRITTEN_BY person | EDITED_BY person | PUBLISHED_BY institution | ABOUT keyword
    | BEFORE YEAR | AFTER YEAR | IN_YEAR YEAR | APPEARED_IN (STRING | DBLP_KEY | journal | conference)
    | CITED_BY publication | REFERENCES publication | TITLE STRING
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
    | REFERENCES publication | ORCID ORCID_VALUE | WBC
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
    : NAMED STRING | ACRONYM STRING | ABOUT keyword | AFTER YEAR
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
    : NAMED STRING | ACRONYM STRING | ABOUT keyword | AFTER YEAR
    | BEFORE YEAR | IN_YEAR YEAR | VOLUME STRING
    ;

journal
    : LR_BRACKET journalQuery RR_BRACKET | STRING | DBLP_KEY
    ;

// Keyword
keywordQuery
    : KEYWORD
    keywordLimitation*
    ;

keywordLimitation
    : OF (publication | person | journal | conference)
    ;

keyword
    :  LR_BRACKET keywordQuery RR_BRACKET | SL_BRACKET (STRING COMMA)* STRING SR_BRACKET | STRING
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