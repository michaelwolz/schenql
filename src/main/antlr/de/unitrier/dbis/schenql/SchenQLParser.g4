/*
 * This file contains the grammar for the SchenQL query language
 */

parser grammar SchenQLParser;

options { tokenVocab=SchenQLLexer; }


query
    : (
      | publicationQuery | personQuery | institutionQuery | conferenceQuery | journalQuery
      )
      WHITESPACE
      limitation?
      WHITESPACE?
      SEMI
      ;

publicationQuery
    : PUBLICATION
    ;

personQuery
    : PERSON ;

institutionQuery
    : INSTITUTION ;

conferenceQuery
    : CONFERENCE ;

journalQuery
    : JOURNAL ;

limitation
    : writtenby WHITESPACE person
    ;

publication
    : ( publicationQuery | STRING )
    ;

person
    : ( personQuery | STRING) ; // Orcid

institution
    : ( institutionQuery | STRING)
    ;

conference
    : ( conferenceQuery | STRING)
    ;

journal
    : ( journalQuery | STRING)
    ;

writtenby
    : WRITTEN_BY;

dblpKey
    : DBLP_KEY
    ;