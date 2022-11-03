
grammar Basic;

basic: NAME (SPACE INT_LITERAL)+;





INT_LITERAL: [1-9][0-9]* | '0';
NAME: [a-zA-Z_$][a-zA-Z0-9_$]*;
SPACE: ' ';