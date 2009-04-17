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

CLASS 		= "class"
STATE 		= "state"
GRIDSIZE 	= "gridsize"
GRIDTYPE	= "gridtype"
ALIAS		= "alias"
BOUND		= "bounded"
WRAP		= "wrapped"

ASSIGN 	= ":="
LBRACE 	= "{"
RBRACE 	= "}"
LBRACK 	= "["
RBRACK 	= "]"
COM	= ","
ID	= [a-zA-Z][a-zA-Z0-9]*
NUM 	= [0-9]+
NL  	= \n | \r | \r\n
SEP	= "%%"
PND	= "#"
COL	= ":"
NULL	= ""

%%

/* operators */ 
LBRACK | RBRACK    	{return (int) yycharat(0); }
LBRACE		    	{return Parser.LBRACE; }
RBRACE    		{return Parser.RBRACE; }
GRIDSIZE ASSIGN		{return Parser.GRIDSIZE; }

/* newline */
{NL}   			{return Parser.NL; }
{COM}   		{return Parser.COM; }

/* float */
{NUM}  { yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
         return Parser.NUM; }
