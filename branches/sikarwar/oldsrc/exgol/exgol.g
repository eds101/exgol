grammar exgol;

options{backtrack=true; memoize=true;}

CLASS 		: 'class';
STATE 		: 'state';
GRIDSIZE 	: 'gridsize';
GRIDTYPE	: 'gridtype';
ALIAS		: 'alias';
BOUND		: 'bounded';
WRAP		: 'wrapped';

ASSIGN 	: ':=';
LBRACE 	: '{';
RBRACE 	: '}';
LBRACK 	: '[';
RBRACK 	: ']';
ID	: ('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9')*;
NUM 	: ('0'..'9')+;
NL  	: '\n' | '\r' | '\r\n';
SEP	: '%%';
PND	: '#';
COL	: ':';
COM	: ',';
NULL	: '';

exgol	:
	init_sec SEP;
	
test	:
	NUM COM;
	
init_sec:
	init_statements;

init_statements: 
	grid_def init_statements|
	class_def init_statements|
	state_def init_statements|
	alias_dec init_statements|
	gridtype_def init_statements|
	grid_def |
	class_def |
	state_def |
	alias_dec |
	gridtype_def;
	
	
	

identifier_list:
 	 ID COM identifier_list|
 	 ID;

numeric_list:
 	 NUM (COM NUM)*;

grid_def: 
	GRIDSIZE ASSIGN LBRACE numeric_list RBRACE NL;

class_def:
	CLASS ASSIGN LBRACE identifier_list RBRACE NL;

state_def:
	STATE ASSIGN LBRACE identifier_list RBRACE NL;

gridtype_def:
	GRIDTYPE ASSIGN gridtype_values NL;

gridtype_values: 
	BOUND | 
	WRAP;

alias_dec:
	ALIAS ID ASSIGN LBRACE identifier_list RBRACE NL;
