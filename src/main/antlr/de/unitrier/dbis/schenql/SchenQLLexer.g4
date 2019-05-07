lexer grammar SchenQLLexer;


// First-Class-Citizen
PUBLICATION         : P U B L I C A T I O N S? ;

PERSON              : P E R S O N S? ;

INSTITUTION         : I N S T I T U T I O N S? ;

CONFERENCE          : C O N F E R E N C E S? ;

JOURNAL             : J O U R N A L S? ;

// Literals

DBLP_KEY            : ('homepages/' STRING | 'conf/' STRING | 'journal/' STRING);

// Limitations

WRITTEN_BY           : W R I T T E N WHITESPACE B Y ;


// Helper

STRING              : '"' ~('\r' | '\n' | '"')* '"' | '\'' ~('\r' | '\n' | '\'')* '\'' ;

WHITESPACE          : (' ' | '\t')+ ;

// Constructors symbols - from MySQLLexer.g4 (https://github.com/antlr/grammars-v4/blob/master/mysql/MySqlLexer.g4)
DOT:                                 '.';
LR_BRACKET:                          '(';
RR_BRACKET:                          ')';
COMMA:                               ',';
SEMI:                                ';';
AT_SIGN:                             '@';
ZERO_DECIMAL:                        '0';
ONE_DECIMAL:                         '1';
TWO_DECIMAL:                         '2';
SINGLE_QUOTE_SYMB:                   '\'';
DOUBLE_QUOTE_SYMB:                   '"';
REVERSE_QUOTE_SYMB:                  '`';
COLON_SYMB:                          ':';

// Adding fragments for every letter for case insensitivity
fragment A : [aA];
fragment B : [bB];
fragment C : [cC];
fragment D : [dD];
fragment E : [eE];
fragment F : [fF];
fragment G : [gG];
fragment H : [hH];
fragment I : [iI];
fragment J : [jJ];
fragment K : [kK];
fragment L : [lL];
fragment M : [mM];
fragment N : [nN];
fragment O : [oO];
fragment P : [pP];
fragment Q : [qQ];
fragment R : [rR];
fragment S : [sS];
fragment T : [tT];
fragment U : [uU];
fragment V : [vV];
fragment W : [wW];
fragment X : [xX];
fragment Y : [yY];
fragment Z : [zZ];