/*
 * This file contains the grammar for the SchenQL query language
 */

parser grammar SchenQLParser;

options { tokenVocab=SchenQLLexer; }

@header {
package de.unitrier.dbis.schenql
}

query
    : (publicationQuery | personQuery | institutionQuery | conferenceQuery | journalQuery)
      SPACE?
      SEMI
      ;

// Publications
publicationQuery
    : PUBLICATION
    SPACE
    publicationLimitation*
    ;

publicationLimitation
    : (WRITTEN_BY SPACE person | EDITED_BY SPACE person | PUBLISHED_BY SPACE institution | ABOUT SPACE keywords+
    | BEFORE SPACE YEAR | AFTER SPACE YEAR | IN_YEAR SPACE YEAR | APPEARED_IN SPACE journal
    | CITED_BY SPACE publication | CITES SPACE publication | TITLE SPACE STRING)
    SPACE?
    ;

publication
    : LR_BRACKET publicationQuery RR_BRACKET | STRING
    ;

// Persons
personQuery
    : PERSON
    SPACE
    personLimitation
    ;

personLimitation
    : (NAMED SPACE STRING | AUTHORED SPACE publication | EDITED SPACE publication | WORKS_FOR SPACE institution
    | PUBLISHED_WITH SPACE institution | PUBLISHED_IN SPACE (conference | journal) | CITED_BY SPACE publication
    | CITES SPACE publication)
    SPACE?
    ;

person
    : LR_BRACKET personQuery RR_BRACKET | STRING ; // Orcid

// Institutions

institutionQuery
    : INSTITUTION
    ;

institution
    : LR_BRACKET institutionQuery RR_BRACKET | STRING
    ;

// Conferences
conferenceQuery
    : CONFERENCE
    SPACE
    conferenceLimitation
    ;

conferenceLimitation
    : (NAMED SPACE STRING | ACRONYM SPACE STRING | ABOUT SPACE STRING | AFTER SPACE YEAR
    | BEFORE SPACE YEAR | IN_YEAR SPACE YEAR | CITY SPACE STRING | COUNTRY SPACE STRING)
    SPACE?
    ;

conference
    : LR_BRACKET conferenceQuery RR_BRACKET | STRING
    ;

// Journals

journalQuery
    : JOURNAL
    ;

journal
    : LR_BRACKET journalQuery RR_BRACKET | STRING
    ;

// Other
keywords
    : SL_BRACKET (STRING SPACE? COMMA SPACE?)* STRING SR_BRACKET | STRING
    ;