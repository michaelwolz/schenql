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
CITES:                  'CITES';
CITY:                   'CITY';
COUNTRY:                'COUNTRY';
EDITED:                 'EDITED';
EDITED_BY:              'EDITED BY';
IN_YEAR:                'IN YEAR';
NAMED:                  'NAMED';
PUBLISHED_BY:           'PUBLISHED BY';
PUBLISHED_IN:           'PUBLISHED IN';
PUBLISHED_WITH:         'PUBLISHED WITH';
TITLE:                  'TITLE';
WORKS_FOR:              'WORKS FOR';
WRITTEN_BY:             'WRITTEN BY';


// Helper

STRING :                DOUBLE_QUOTE_SYMB ~('\r' | '\n' | '"')* DOUBLE_QUOTE_SYMB
                        | SINGLE_QUOTE_SYMB ~('\r' | '\n' | '\'')* SINGLE_QUOTE_SYMB;
SPACE:                  (' ' | '\t')+;
YEAR:                   ('18' | '19' | '20' | '21') ('0'..'9')('0'..'9');

// Constructors symbols - from MySQLLexer.g4 (https://github.com/antlr/grammars-v4/blob/master/mysql/MySqlLexer.g4)
DOT:                    '.';
LR_BRACKET:             '(';
RR_BRACKET:             ')';
COMMA:                  ',';
SEMI:                   ';';
AT_SIGN:                '@';
ZERO_DECIMAL:           '0';
ONE_DECIMAL:            '1';
TWO_DECIMAL:            '2';
SINGLE_QUOTE_SYMB:      '\'';
DOUBLE_QUOTE_SYMB:      '"';
REVERSE_QUOTE_SYMB:     '`';
COLON_SYMB:             ':';

SL_BRACKET:             '[';
SR_BRACKET:             ']';