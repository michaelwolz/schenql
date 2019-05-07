lexer grammar SchenQLLexer;

@header {
package de.unitrier.dbis.schenql
}

// First-Class-Citizen
CONFERENCE:             'CONFERENCE' 'S'?;
INSTITUTION:            'INSTITUTION' 'S'?;
JOURNAL:                'JOURNAL' 'S'?;
PERSON:                 'PERSON' 'S'?;
PUBLICATION:            'PUBLICATION' 'S'?;

// Literals
DBLP_KEY:               ('homepages/' STRING | 'conf/' STRING | 'journal/' STRING);

// Limitations
ABOUT:                  'ABOUT';
ACRONYM:                'ACRONYM';
AFTER:                  'AFTER';
APPEARED_IN:            'APPEARED IN';
AUTHORED:               'AUTHORED';
BEFORE:                 'BEFORE';
CITED_BY:               'CITED BY';
CITES:                  'CITES' | 'CITING' | 'REFERENCES';
CITY:                   'CITY';
COUNTRY:                'COUNTRY';
EDITED:                 'EDITED';
EDITED_BY:              'EDITED BY';
IN_YEAR:                'IN YEAR';
LAT:                    'LAT';
LON:                    'LON';
MEMBERS:                'MEMBERS';
NAMED:                  'NAMED';
PUBLISHED_BY:           'PUBLISHED BY';
PUBLISHED_IN:           'PUBLISHED IN';
PUBLISHED_WITH:         'PUBLISHED WITH';
TITLE:                  'TITLE';
VOLUME:                 'VOLUME';
WORKS_FOR:              'WORKS FOR';
WRITTEN_BY:             'WRITTEN BY';

// Functions
COUNT:                  'COUNT';
LIMIT:                  'LIMIT';
MOST_CITED:             'MOST CITED';


// Data types
STRING:                 DOUBLE_QUOTE_SYMB ~('\r' | '\n' | '"')* DOUBLE_QUOTE_SYMB
                        | SINGLE_QUOTE_SYMB ~('\r' | '\n' | '\'')* SINGLE_QUOTE_SYMB;
YEAR:                   ('18' | '19' | '20' | '21') ('0'..'9')('0'..'9');
NUMBER:                 YEAR | [1-9][0-9]*; // Quick Fix

// Constructors symbols
LR_BRACKET:             '(';
RR_BRACKET:             ')';
SL_BRACKET:             '[';
SR_BRACKET:             ']';
COMMA:                  ',';
SEMI:                   ';';
SINGLE_QUOTE_SYMB:      '\'';
DOUBLE_QUOTE_SYMB:      '"';

// Ignore Spaces
SPACE:                  [ \t\r\n]+ -> skip;