lexer grammar SchenqlLexer;

@header {
package de.unitrier.dbis.schenql;
}

// First-Class-Citizen
CONFERENCE:             C O N F E R E N C E S?;
INSTITUTION:            I N S T I T U T I O N S?;
JOURNAL:                J O U R N A L S?;
KEYWORD:                K E Y W O R D S?;
PERSON:                 P E R S O N S?
                        | A U T H O R S?
                        | E D I T O R S?;
PUBLICATION:            P U B L I C A T I O N S?
                        | B O O K S?
                        | A R T I C L E S?
                        | M A S T E R T H E S I S (E S)?
                        | P H D T H E S I S (E S)?
                        | I N P R O C E E D I N G S?;

// Literals
DBLP_KEY:               (DOUBLE_QUOTE_SYMB | SINGLE_QUOTE_SYMB)
                        ( 'homepages/'
                        | 'conf/'
                        | 'journals/'
                        | 'books/'
                        )
                        ~('\r' | '\n' | '"')*
                        (DOUBLE_QUOTE_SYMB | SINGLE_QUOTE_SYMB)
                        {setText(getText().substring(1, getText().length()-1));}
                        ;

// Conditions
fragment DIGIT:         '0'..'9';

ABOUT:                  A B O U T;
ACRONYM:                A C R O N Y M;
AFTER:                  A F T E R;
APPEARED_IN:            A P P E A R E D ' ' I N;
AUTHORED:               A U T H O R E D;
BEFORE:                 B E F O R E;
CITED_BY:               C I T E D ' ' B Y;
REFERENCES:             C I T E S | C I T I N G | R E F E R E N C E S | R E F E R E N C I N G;
CITY:                   C I T Y;
COAUTHORS:              C O A U T H O R S?;
COUNTRY:                C O U N T R Y;
EDITED:                 E D I T E D;
EDITED_BY:              E D I T E D ' ' B Y;
IN_YEAR:                I N ' ' Y E A R;
LAT:                    L A T;
LON:                    L O N;
MEMBERS:                M E M B E R S;
NAMED:                  N A M E D;
ORCID:                  O R C I D;
ORCID_VALUE:            DIGIT DIGIT DIGIT DIGIT '-' DIGIT DIGIT DIGIT DIGIT '-'
                        DIGIT DIGIT DIGIT DIGIT '-' DIGIT DIGIT DIGIT DIGIT
                        ;
WBC:                    W H O ' ' B E C A M E ' ' C R A Z Y;
PUBLISHED_BY:           P U B L I S H E D ' ' B Y;
PUBLISHED_IN:           P U B L I S H E D ' ' I N;
PUBLISHED_WITH:         P U B L I S H E D ' ' W I T H;
TITLE:                  T I T L E | T I T L E D;
VOLUME:                 V O L U M E;
WORKS_FOR:              (W O R K S | W O R K I N G) ' ' F O R;
WRITTEN_BY:             W R I T T E N ' ' B Y;

// Functions
COUNT:                  C O U N T;
LIMIT:                  L I M I T;
MOST_CITED:             M O S T ' ' C I T E D;

// Logical Operators
OR:                     O R | '||';
AND:                    A N D | '&&';
NOT:                    N O T | '!';


// Data types
STRING:                 (DOUBLE_QUOTE_SYMB ~('\r' | '\n' | '"')* DOUBLE_QUOTE_SYMB
                        | SINGLE_QUOTE_SYMB ~('\r' | '\n' | '\'')* SINGLE_QUOTE_SYMB)
                        {setText(getText().substring(1, getText().length()-1));}
                        ;
YEAR:                   ('18' | '19' | '20' | '21') ('0'..'9')('0'..'9');
NUMBER:                 [1-9][0-9]* | YEAR; //Quick-Fix


// Additional
OF:                     ' ' O F ' ';

// Constructors symbols
LR_BRACKET:             '(';
RR_BRACKET:             ')';
SL_BRACKET:             '[';
SR_BRACKET:             ']';
COMMA:                  ',';
SEMI:                   ';';
SINGLE_QUOTE_SYMB:      '\'';
DOUBLE_QUOTE_SYMB:      '"';
TILDE:                  '~';

// Ignore Spaces
SPACE:                  [ \t\r\n]+ -> skip;

// Fragments for every letter to achieve case insensitivity
fragment A:             ('a'|'A');
fragment B:             ('b'|'B');
fragment C:             ('c'|'C');
fragment D:             ('d'|'D');
fragment E:             ('e'|'E');
fragment F:             ('f'|'F');
fragment G:             ('g'|'G');
fragment H:             ('h'|'H');
fragment I:             ('i'|'I');
fragment J:             ('j'|'J');
fragment K:             ('k'|'K');
fragment L:             ('l'|'L');
fragment M:             ('m'|'M');
fragment N:             ('n'|'N');
fragment O:             ('o'|'O');
fragment P:             ('p'|'P');
fragment Q:             ('q'|'Q');
fragment R:             ('r'|'R');
fragment S:             ('s'|'S');
fragment T:             ('t'|'T');
fragment U:             ('u'|'U');
fragment V:             ('v'|'V');
fragment W:             ('w'|'W');
fragment X:             ('x'|'X');
fragment Y:             ('y'|'Y');
fragment Z:             ('z'|'Z');