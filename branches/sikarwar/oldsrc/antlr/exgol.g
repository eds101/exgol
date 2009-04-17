grammar exgol;

//options{backtrack=true; memoize=true;}

CLASS 		:	'class';
STATE 		:	'state';
GRIDSIZE	:	'gridsize';
GRIDTYPE	:	'gridtype';
ALIAS		:	'alias';
BOUND		:	'bounded';
WRAP		:	'wrapped';
TRANS		:	'trans';
TRANSRULE	:	'transrule';
TYPE		:	'type';
CONDITION	:	'condition';
PROB		:	'prob';
RESOLVE		:	'resolve';
PEER		:	'peer';
ENEMY		:	'enemy';
NEIGHBOR	:	'neighbor';
SIM		:	'sim';
POPULATE	:	'populate';
START		:	'start';
NORMALFUNC	:	'normal';
POISSONFUNC	:	'poisson';
LINEFUNC	:	'line';
DOTFUNC		:	'dot';
CIRCLEFUNC	:	'circle';
RECTFUNC	:	'rectanlgle';

COLOR		:	'blue';
ASSIGN 		:	':=';
LBRACE 		:	'{';
RBRACE 		:	'}';
LBRACK 		:	'[';
RBRACK 		:	']';
LPARAN 		:	'(';
RPARAN 		:	')';
ID		:	('a'..'z'|'A'..'Z')('a'..'z'|'A'..'Z'|'0'..'9')*;
NUM 		:	('0'..'9')+;
FLOAT 		:	('0'..'9')+ '.' ('0'..'9')+;
NL  		:	'\n' | '\r' | '\r\n';
SEP		:	'%%';
PND		:	'#';
COL		:	':';
COM		:	',';
DOT		:	'.';
NULL		:	'';
DASH		:	'-';
EQ		:	'=';
NOT		:	'!';
GT		:	'>';
LT		:	'<';
//WS 		: 	('\t'|'\n'){$setType(Token.SKIP);}; //ignore this token



exgol	:
	 init_section SEP NL trans_section SEP NL simulation_section;
 
	
init_section:
	init_statements;
	
init_statements: 
	grid_def init_statements|
	class_def init_statements|
	state_def init_statements|
	alias_dec init_statements|
	gridtype_def init_statements|
	NL;

identifier_list:
 	 ID COM identifier_list|
 	 ID COL COLOR COM identifier_list|
 	 ID COL COLOR|
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

trans_section:
	trans_statements;

trans_statements: 
	trans_def	NL trans_statements|
	transrule_def	NL trans_statements|
	NL;

trans_def:
	TRANS ID ASSIGN identifier_list DASH GT ID;

transrule_def:
	 TRANSRULE ID LBRACE  rule_expressions RBRACE;

rule_expressions:
	TYPE ASSIGN ID | optional_expressions;

optional_expressions:
	class_def 	NL optional_expressions|
	condition	NL optional_expressions|
	resolve		NL optional_expressions|
	prob		NL optional_expressions|
	NL|'';

condition
	:	
	CONDITION ASSIGN  condition_stmt (EQ|LT|GT|LT EQ|GT EQ|NOT EQ)? condition_stmt;

resolve	:	
	RESOLVE ASSIGN LBRACE identifier_list RBRACE;

prob	:	
	PROB ASSIGN  FLOAT;
	
condition_stmt:
	var_expression LBRACK range_stmt RBRACK
	var_expression DOT var_expression DOT LBRACK range_stmt RBRACK|
	ID;

var_expression:
	ID|
	builtin_alias;
	

class_expression:
	ID|
	builtin_alias;

state_expression:
	ID;

builtin_alias: 
	PEER|ENEMY|NEIGHBOR;

range_stmt:
	NUM|
	NUM COM range_stmt|
	NUM DASH NUM|
	NUM DASH NUM COM range_stmt;
	
	
simulation_section:
	simulation_stmts;

simulation_stmts:
	populate_stmt simulation_stmts|
	sim_stmt simulation_stmts|
	start_stmt simulation_stmts|
	NL;

populate_stmt:
	POPULATE LPARAN ID COM ID COM fill_func RPARAN;


fill_func:
 	NORMALFUNC LPARAN NUM COM NUM COM FLOAT RPARAN|
	POISSONFUNC LPARAN NUM COM FLOAT RPARAN|
	LINEFUNC LPARAN NUM COM NUM COM NUM COM NUM RPARAN|
	DOTFUNC LPARAN NUM COM NUM RPARAN|
	CIRCLEFUNC LPARAN NUM COM NUM COM NUM RPARAN|
	RECTFUNC LPARAN NUM COM NUM COM NUM COM NUM RPARAN;

sim_stmt:
	SIM ID ASSIGN LBRACE identifier_list RBRACE NL;

start_stmt:
	START LPARAN NUM COM ID RPARAN;
