%{
  import java.io.*;
  import java.awt.*;
  import javax.swing.*;
%}

/* YACC Declarations */
%token NL
%token GRID
%token <ival> NUM
%token '[' ']'

/* Grammar follows */
%%
input: /* empty string */
 | input line
 ;

line: NL
 | grid NL
 ;

grid: GRID '[' NUM ']' '[' NUM ']' { set_grid($3,$6); }
 ;
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
