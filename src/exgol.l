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

//keywords
GRIDSIZE 	= "gridsize"
CLASS 		= "class"
STATE 		= "state"
GRIDTYPE	= "gridtype"
ALIAS 		= "alias"
BOUND 		= "bounded"
WRAP 		= "wrapped"

TRANS		= "trans"
TRANSRULE	= "transrule"
TYPE		= "type"
CONDITION	= "condition"
PROB		= "prob"
RESOLVE		= "resolve"
PEER		= "peer"
ENEMY		= "enemy"
NEIGHBOR	= "neighbor"

//COLOR		= "black"|"red"

SIM		= "sim"
START		= "start"
POPULATE	= "populate"

DOTFUNC		= "dot"
RECTFUNC	= "rectangle"

//assignment operators
ASSIGN 		= ":="
LBRACE 		= "{"
RBRACE 		= "}"
LBRACK		= "["
RBRACK		= "]"
LPARAN		= "("
RPARAN		= ")"
COM 		= ","
COL 		= ":"


//identifiers
NUM		= [0-9]+
ID		= [a-zA-Z]([a-zA-Z0-9])*

//space handling
NL  		= ("\n" | "\r" | "\r\n")
WS		= " "|"\s"|"\t"
SEP		= "%%"

COMMENT		= "#".*$
DOT		= "."
DASH		= "-"
EQ		= "="
NOT		= "!"
GT		= ">"
LT		= "<"


%%
{GRIDSIZE}		{return Parser.GRIDSIZE;}
{CLASS}			{return Parser.CLASS;}
{STATE}			{return Parser.STATE;}
{GRIDTYPE}		{return Parser.GRIDTYPE;}
{ALIAS}			{return Parser.ALIAS;}
{BOUND}			{yyparser.yylval = new ParserVal(yytext()); return Parser.BOUND;}
{WRAP}			{yyparser.yylval = new ParserVal(yytext()); return Parser.WRAP;}

{TRANS}			{return Parser.TRANS;}
{TRANSRULE}		{return Parser.TRANSRULE;}
{TYPE}			{return Parser.TYPE;}
{CONDITION}		{return Parser.CONDITION;}
{PROB}			{return Parser.PROB;}
{RESOLVE}		{return Parser.RESOLVE;}
{PEER}			{yyparser.yylval = new ParserVal(yytext()); return Parser.PEER;}
{ENEMY}			{yyparser.yylval = new ParserVal(yytext()); return Parser.ENEMY;}
{NEIGHBOR}		{yyparser.yylval = new ParserVal(yytext()); return Parser.NEIGHBOR;}
{SIM}			{return Parser.SIM;}
{START}			{return Parser.START;}
{POPULATE}		{return Parser.POPULATE;}

{DOTFUNC}		{return Parser.DOTFUNC;}
{RECTFUNC}		{return Parser.RECTFUNC;}

//{COLOR}			{ yyparser.yylval = new ParserVal(yytext());return Parser.COLOR;}

{ASSIGN}		{return Parser.ASSIGN;}
{LBRACE}	    	{return Parser.LBRACE;}
{RBRACE}    		{return Parser.RBRACE;}
{COM}			{return Parser.COM;}
{COL}			{return Parser.COL;}

{LBRACK}		{return Parser.LBRACK;}
{RBRACK}		{return Parser.RBRACK;}
{LPARAN}		{return Parser.LPARAN;}
{RPARAN}		{return Parser.RPARAN;}
//{PND}			{return Parser.PND;}
{DOT}			{return Parser.DOT;}
{DASH}			{return Parser.DASH;}
{EQ}			{return Parser.EQ;}
{NOT}			{return Parser.NOT;}
{GT}			{return Parser.GT;}
{LT}			{return Parser.LT;}

{NUM}			{ yyparser.yylval = new ParserVal(Integer.parseInt(yytext()));return Parser.NUM;}
{ID}			{ yyparser.yylval = new ParserVal(yytext());return Parser.ID;}
{NL}			{return Parser.NL;}
{WS}			{}
{SEP}			{return Parser.SEP;}
<<EOF>>			{return Parser.EOF;}
