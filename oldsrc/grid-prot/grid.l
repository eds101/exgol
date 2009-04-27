%%

%byaccj

%{
  /* store a reference to the parser object */
  private Parser yyparser;

  /* constructor taking an additional parser object */
  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

GRID = grid:=
NUM = [0-9]+
NL  = \n | \r | \r\n

%%

/* operators */ 
"[" | 
"]"    {return (int) yycharat(0); }

{GRID}  { return Parser.GRID; }
/* newline */
{NL}   { return Parser.NL; }

/* float */
{NUM}  { yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
         return Parser.NUM; }
