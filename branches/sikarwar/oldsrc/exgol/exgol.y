%{
  import java.io.*;
  import java.awt.*;
  import javax.swing.*;
%}

/* YACC Declarations */

%token CLASS
%token STATE
%token GRIDSIZE
%token GRIDTYPE
%token ALIAS
%token BOUND
%token WRAP
%token ASSIGN
%token LBRACE
%token RBRACE
%token LBRACK
%token RBRACK
%token COM
%token ID
%token NUM
%token NL
%token SEP
%token PND
%token COL
%token NULL

/* Grammar follows */
%%

exgol	:
	init_sec NL SEP;

init_sec:
	init_statements;

init_statements: 
	class_def		init_statements|
	state_def		init_statements|
	grid_def		init_statements|
	gridtype_def 		init_statements|
	alias_dec		init_statements|
	NULL;

identifier_list:
 	 ID COM identifier_list|
 	 ID;

numeric_list:
 	 NUM COM numeric_list|
 	 NUM;

grid_def: 
	GRIDSIZE ASSIGN LBRACE numeric_list RBRACE;

class_def:
	CLASS ASSIGN LBRACE identifier_list RBRACE;

state_def:
	STATE ASSIGN LBRACE identifier_list RBRACE;

gridtype_def:
	GRIDTYPE ASSIGN gridtype_values;

gridtype_values: 
	BOUND | 
	WRAP;

alias_dec:
	ALIAS ID ASSIGN LBRACE identifier_list RBRACE;


%%

/* a reference to the lexer object */
private Yylex lexer;
static int gridx, gridy;

private void set_grid(int x, int y){
  gridx = x;
  gridy = y;
}

/* interface to the lexer */
private int yylex () {
  int yyl_return = -1;
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

private static void createAndShowGUI() {
  //Create and set up the window.
  JFrame frame = new JFrame("EXGOL");
  GridPanel pan = new GridPanel();
  pan.setPreferredSize(new Dimension(10*gridx+1, 10*gridy+1));
  frame.getContentPane().add(pan);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.pack();
  frame.setVisible(true);
}

/* that's how you use the parser */
public static void main(String args[]) throws IOException {
  Parser yyparser = new Parser(new FileReader(args[0]));
  yyparser.yyparse();
  
  // create grid window
  createAndShowGUI();
}
