/*
 * This file contains the grammar for the SchenQL query language
 */

parser grammar SchenqlParser;

options { tokenVocab=SchenqlLexer; }

@header {
package de.unitrier.dbis.schenql;
}

root
    : (functionCall | query | attributeOf)
    ;

query
    : (publicationQuery | personQuery | institutionQuery | conferenceQuery | journalQuery | keywordQuery)
    (LIMIT NUMBER)?
    SEMI?
    ;

// Logical Operators
logicalOperator
    : (or | and) not?
    | not
    ;

or
    : OR
    ;

and
    : AND
    ;

not
    : NOT
    ;

// Publications
publicationQuery
    : publicationFunction |
    PUBLICATION
    publicationCondition*
    ;

publicationCondition
    : logicalOperator?
    ( WRITTEN_BY person | EDITED_BY person | PUBLISHED_BY institution | ABOUT KEYWORD keyword | ABOUT STRING
    | BEFORE YEAR | AFTER YEAR | IN_YEAR YEAR | APPEARED_IN (STRING | DBLP_KEY | journal | conference)
    | CITED_BY publication | REFERENCES publication | TITLE TILDE? STRING )
    ;

publication
    : LR_BRACKET publicationQuery RR_BRACKET | DBLP_KEY | TILDE? STRING
    ;

publicationFunction
    : MOST_CITED LR_BRACKET publicationQuery RR_BRACKET
    ;

// Persons
personQuery
    : personFunction |
    PERSON
    personCondition*
    ;

personCondition
    : logicalOperator?
    ( NAMED TILDE? STRING | AUTHORED publication | EDITED publication | WORKS_FOR institution
    | PUBLISHED_WITH institution | PUBLISHED_IN (STRING | DBLP_KEY | conference | journal) | CITED_BY publication
    | REFERENCES publication | ORCID ORCID_VALUE | WBC )
    ;

person
    : LR_BRACKET personQuery RR_BRACKET | DBLP_KEY | ORCID_VALUE | TILDE? STRING;

personFunction
    : COAUTHORS OF person
    ;

// Institutions

institutionQuery
    : INSTITUTION
    institutionCondition*
    ;

institutionCondition
    : logicalOperator?
    ( NAMED TILDE? STRING | CITY STRING | COUNTRY STRING | MEMBERS person )
    ;

institution
    : LR_BRACKET institutionQuery RR_BRACKET | TILDE? STRING
    ;

// Conferences
conferenceQuery
    : CONFERENCE
    (conferenceCondition logicalOperator conferenceCondition | conferenceCondition)*
    ;

conferenceCondition
    : logicalOperator?
    ( NAMED STRING | ACRONYM STRING | ABOUT KEYWORD? keyword | AFTER YEAR | OF publication
    | BEFORE YEAR | IN_YEAR YEAR | CITY STRING | COUNTRY STRING )
    ;

conference
    : LR_BRACKET conferenceQuery RR_BRACKET | STRING | DBLP_KEY
    ;

// Journals
journalQuery
    : JOURNAL
    journalCondition*
    ;

journalCondition
    : logicalOperator?
    ( NAMED STRING | ACRONYM STRING | ABOUT KEYWORD? keyword | AFTER YEAR | OF publication
    | BEFORE YEAR | IN_YEAR YEAR | VOLUME STRING )
    ;

journal
    : LR_BRACKET journalQuery RR_BRACKET | STRING | DBLP_KEY
    ;

// Keyword
keywordQuery
    : KEYWORD
    keywordCondition*
    ;

keywordCondition
    : logicalOperator?
    ( OF (publication | person | journal | conference) )
    ;

keyword
    :  LR_BRACKET keywordQuery RR_BRACKET | SL_BRACKET (STRING COMMA)* STRING SR_BRACKET | STRING
    ;

// Function
functionCall
    : COUNT LR_BRACKET query RR_BRACKET
    ;

// Attributes
attributeOf
    : (STRING COMMA)* STRING OF query
    ;