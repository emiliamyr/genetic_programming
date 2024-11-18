grammar grammarGP;

program     : statement* EOF;

block       : LBRACE statement* RBRACE;

statement   : assignmentStatement SEMI
            | loopStatement
            | ifStatement
            | read
            | write
            | block
            ;

assignmentStatement
            : identifier ASSIGN expression;

ifStatement : IF LPAREN expression RPAREN statement (ELSE statement)?;

loopStatement
            : WHILE LPAREN expression RPAREN statement;

read        : READ LPAREN identifier RPAREN SEMI;

write       : WRITE LPAREN expression RPAREN SEMI;



primaryExpression
            : literal
            | identifier
            | castExpression
            | LPAREN expression RPAREN
            ;

unaryExpression
            : unaryOperator primaryExpression
            | primaryExpression;

multiplicativeExpression
            : unaryExpression ((TIMES | DIV) unaryExpression)*
            ;

additiveExpression
            : multiplicativeExpression ((PLUS | MINUS) multiplicativeExpression)*
            ;

relationalExpression
            : additiveExpression (relation additiveExpression)?
            ;

equalityExpression
            : relationalExpression (equalityRelation relationalExpression)?
            ;

logicalAndExpression
            : equalityExpression (AND equalityExpression)?
            ;

logicalOrExpression
            : logicalAndExpression (OR logicalAndExpression)?
            ;

expression  : logicalOrExpression;

castExpression
            : typeSpecifier LPAREN expression RPAREN;


relation    : LT | LE | GT | GE;

equalityRelation
            : EQ | NE;

unaryOperator
            : PLUS | MINUS | NOT;

literal     : INTEGER_LITERAL
            | FLOAT_LITERAL
            ;

identifier  : IDENTIFIER;

typeSpecifier
            : INT
            | FLOAT
            ;

COMMA: ',';
DOT: '.';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';

PLUS: '+';
MINUS: '-';
TIMES: '*';
DIV: '/';

ASSIGN: '=';
SEMI: ';';

INTEGER_LITERAL : DEC_DIGIT+;
FLOAT_LITERAL   : INTEGER_LITERAL DOT INTEGER_LITERAL;

IF: 'if';
ELSE: 'else';
WHILE: 'while';
READ: 'read';
WRITE: 'write';

AND: '&&';
OR: '||';

INT: 'int';
FLOAT: 'float';

EQ: '==';
NE: '!=';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';
NOT: '!';

IDENTIFIER
    : LETTER (LETTER | DIGIT)*
    ;
WS          : [ \t\r\n]+ -> skip;
DIGIT       : [0-9]+;
LETTER      : [a-zA-Z_]+;

fragment DEC_DIGIT: [0-9];