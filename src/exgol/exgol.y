%{
  import java.io.*;
  import java.awt.*;
  import javax.swing.*;
%}

/* YACC Declarations */

%token GRIDSIZE
%token CLASS
%token STATE
%token GRIDTYPE
%token ALIAS
%token BOUND
%token WRAP

%token TRANS
%token TRANSRULE
%token TYPE
%token CONDITION
%token PROB
%token RESOLVE
%token PEER
%token ENEMY
%token NEIGHBOR



%token ASSIGN
%token LBRACE
%token RBRACE
%token LBRACK
%token RBRACK
%token LPARAN
%token RPARAN

%token ID
%token NUM

%token SEP
%token PND
%token COL
%token COM
%token DOT
%token NULL
%token DASH
%token EQ
%token NOT
%token GT
%token LT

%token NL
%token WS
%token EOF
//%token COLOR


/* Grammar follows */
%%

//exgol
exgol		: init_section SEP NL trans_section SEP{System.out.println("Exgol Parsed");};;

//init
init_section	: init_statements {System.out.println("Init Section Parsed");};

init_statements	: 
		grid_def 	init_statements|
		gridtype_def 	init_statements|
		class_def 	init_statements|
		state_def 	init_statements|
		alias_dec 	init_statements|
		NL {System.out.println("Init Empty Line");};

grid_def	: GRIDSIZE ASSIGN LBRACE numeric_list RBRACE NL{System.out.println("Create Grid :" + $4.sval);};
class_def	: CLASS	ASSIGN LBRACE identifier_list RBRACE NL{System.out.println("List of Classes " + $4.sval);};
state_def	: STATE	ASSIGN LBRACE identifier_list RBRACE NL{System.out.println("List of States " + $4.sval);};
gridtype_def	: GRIDTYPE ASSIGN BOUND NL {System.out.println("Grid Type is " + $3.sval);};
alias_dec	: ALIAS ID ASSIGN LBRACE identifier_list RBRACE NL {System.out.println("Alias " + $2.sval + " for " + $5.sval);};

//trans
trans_section	: trans_statements {System.out.println("Trans Section Parsed");};

trans_statements:
		trans_def trans_statements|
		transrule_def trans_statements|
		NL {System.out.println("Trans Empty Line");}

trans_def	: TRANS ID ASSIGN ID DASH GT ID NL{System.out.println("Transition " + $2.sval);};
transrule_def	: TRANSRULE ID LBRACE rule_expressions RBRACE {System.out.println("Transrule " + $2.sval);};
rule_expressions: 
		type_def optional_expressions;
type_def	:
		TYPE ASSIGN ID NL {System.out.println("type:" + $3.sval);} ;
optional_expressions:
		resolve		optional_expressions|
		prob		optional_expressions|
		NL;

resolve		: RESOLVE ASSIGN LBRACE identifier_list RBRACE {System.out.println("resolve:" + $4.sval);};
prob		: PROB ASSIGN NUM| PROB ASSIGN NUM DOT NUM;

numeric_list: 	NUM {$$.sval = Integer.toString($1.ival);}|
		NUM COM numeric_list {$$.sval = Integer.toString($1.ival) + "," + $3.sval;};

identifier_list: ID {$$.sval = $1.sval;} |
		ID COM identifier_list 	{$$.sval = $1.sval + "," + $3.sval;}

%%

/* a reference to the lexer object */
private Yylex lexer;


/* interface to the lexer */
private int yylex () {
  int yyl_return = 0;
  try {
    yyl_return = lexer.yylex();
  }
  catch (IOException e) {
    System.err.println("IO error :"+e);
  }
  return yyl_return;
}

/* error reporting */
public void yyerror (String error) {
  System.err.println ("Error: " + error);
}

/* lexer is created in the constructor */
public Parser(Reader r) {
  lexer = new Yylex(r, this);
}


/* that's how you use the parser */
public static void main(String args[]) throws IOException {
  Parser yyparser = new Parser(new FileReader(args[0]));
  yyparser.yyparse();

}
