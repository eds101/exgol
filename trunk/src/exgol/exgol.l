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
GRIDSIZE 	= "gridsize"
CLASS 		= "class"
STATE 		= "state"
ASSIGN 		= ":="
LBRACE 		= "{"
RBRACE 		= "}"
COM 		= ","
COL 		= ":"
NL  		= ("\n" | "\r" | "\r\n")
NUM		= [0-9]+
ID		= [a-zA-Z]([a-zA-Z0-9])*
WS		= ""|"\s"|"\t"


%%
{GRIDSIZE}		{return Parser.GRIDSIZE;}
{LBRACE}	    	{return Parser.LBRACE;}
{RBRACE}    		{return Parser.RBRACE;}
{ASSIGN}		{return Parser.ASSIGN;}
{NL}			{return Parser.NL;}
{COM}			{return Parser.COM;}
{CLASS}			{return Parser.CLASS;}
{STATE}			{return Parser.STATE;}
{COL}			{return Parser.COL;}
{WS}			{}

{NUM} 	{ yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));
        return Parser.NUM; }
{ID}	{ yyparser.yylval = new ParserVal(yytext()); 
	return Parser.ID;}
