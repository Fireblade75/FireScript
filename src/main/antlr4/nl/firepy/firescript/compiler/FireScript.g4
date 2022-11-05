grammar FireScript;

program: rootStatement* EOF;

rootStatement: statement 
    // | declareFunction 
    // | declareClass
    ;

// declareClass: CLASS NAME classList? block;
// classList: ':' NAME (',' NAME)*;

block: '{' blockStatement* '}';
blockStatement: statement | returnStatement;
statement: declareStatement
    | assignStatement
    | declareFunction
    // | ifStatement
    // | chainExp
    ;

exp: boolLiteral                           #BoolExpression
    | numberLiteral                         #NumberExpression
    | stringLiteral                         #StringExpression
    // | NULL                                  #NullExpression
    // | '(' exp ')'                           #BracketExpression
    | functionStatement                     #FunctionExpression
    // | chainExp                              #ChainExpression
    // | left=exp math_operator right=exp      #MathExpression
    // | left=exp eq_operator right=exp        #EqualityExpression
    // | left=exp comp_operator right=exp      #CompareExpression
    // | OP_NOT exp                            #NotExpression
    // | left=exp logic_operator right=exp     #LogicExpression
    ;

// chainExp: chainPart* chainTail;
// chainPart: (variable | functionCall) '.';
// chainTail: (variable | functionCall);

// ifStatement:
//     IF_MARK exp block
//     elseifStatement*
//     elseStatement?
//     ;
// elseifStatement: ( ':?' exp block );
// elseStatement: ( ':' block);

// forStatement: FOR NAME IN exp block;

declareFunction: FN NAME paramList (':' type)? block;
functionStatement: paramList (':' type)? FN_ARROW block;
// functionCall: NAME '(' (exp (',' exp)*)? ')';
paramList: '(' (paramItem (',' paramItem)*)? ')';
paramItem: label=NAME ':' (type | NAME);


declareStatement: (declareInferStatement | declareOnlyStatement) LINE_END;
declareInferStatement: varKey=(CONST | LET) NAME ('=' exp);
// declareOnlyStatement: varKey=(CONST | LET) NAME (':' type);
declareOnlyStatement: LET NAME ':' type;

assignStatement: variable '=' exp LINE_END;
returnStatement: RETURN exp LINE_END;

stringLiteral: STRING_STRING;
numberLiteral: intLiteral | floatLiteral ;
type: NAME | INT | FLOAT | BOOL | STRING;

math_operator: PLUS | MINUS | MULTIPLY | DEVIDE;
eq_operator: OP_EQ | OP_NOT_EQ;
comp_operator: OP_LESS | OP_LESSEQ | OP_MORE | OP_MOREEQ;
logic_operator: OP_AND | OP_OR;

intLiteral: INT_LITERAL;
floatLiteral: FLOAT_LITERAL;
boolLiteral: BOOL_LITERAL;
variable: NAME ;

WS: [\r\n\t ]+ -> skip;

COMMENT: ( '~' ~[\r\n]* '\r'? '\n'
         | '<~' .*? '~>'
         ) -> channel(HIDDEN);

INT_LITERAL: [1-9][0-9]* | '0';
FLOAT_LITERAL: [0-9]+('.'[0-9]+);
BOOL_LITERAL: 'true' | 'false';

INT: 'int';
FLOAT: 'float';
BOOL: 'bool';
STRING: 'string';

CONST: 'const';
LET: 'let';
RETURN: 'return';
NULL: 'null';

PLUS: '+';
MINUS: '-';
MULTIPLY: '*';
DEVIDE: '/';

OP_LESS: '<';
OP_MORE: '>';
OP_EQ: '==';
OP_NOT_EQ: '!=';
OP_LESSEQ: '<=';
OP_MOREEQ: '>=';

OP_AND: 'and';
OP_OR: 'or';
OP_NOT: 'not';

LINE_END: ';';

STRUCT: 'struct';
CLASS: 'class';
FN: 'fn';
FN_ARROW: '->';

IF_MARK: '?';
FOR: 'for';
IN: 'in';

NAME: [a-zA-Z_$][a-zA-Z0-9_$]*;

STRING_STRING: '"' (STRING_CHAR*) '"';
STRING_CHAR: ~('\\'|'"') | '\\\\' | '\\"';

// IMPORT_LITERAL: '\'' [a-zA-Z0-9._]+ '\'';

