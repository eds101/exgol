%{
  import java.io.*;
  import java.awt.*;
  import javax.swing.*;
%}

/* YACC Declarations */

%token GRIDSIZE
%token CLASS
%token STATE
%token LBRACE
%token RBRACE
%token ASSIGN
%token NUM
%token COM
%token COL
%token COLOR
%token NL
%token ID
/* Grammar follows */
%%
init_statements: 
	grid_def init_statements|
	class_def init_statements|
	state_def init_statements|
	NL {System.out.println("init NL");};

grid_def	: GRIDSIZE ASSIGN LBRACE numeric_list RBRACE NL{System.out.println("Create Grid :" + $4.sval);};
class_def	: CLASS	ASSIGN LBRACE identifier_list RBRACE NL{System.out.println("List of Classes" + $4.sval);};
state_def	: STATE	ASSIGN LBRACE identifier_list RBRACE NL{System.out.println("List of States" + $4.sval);};

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
