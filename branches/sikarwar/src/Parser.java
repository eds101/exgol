//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "exgol.y"
	import java.io.*;
	import java.awt.*;
	import javax.swing.*;
	import columbia.exgol.intermediate.*;
	import columbia.exgol.simulation.GUI;
	import java.awt.Color;
	import java.util.Hashtable;
	import java.util.Vector;
	import java.util.Enumeration;
//#line 27 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character
public int count;

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt;
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short GRIDSIZE=257;
public final static short CLASS=258;
public final static short STATE=259;
public final static short GRIDTYPE=260;
public final static short ALIAS=261;
public final static short BOUND=262;
public final static short WRAP=263;
public final static short TRANS=264;
public final static short TRANSRULE=265;
public final static short TYPE=266;
public final static short CONDITION=267;
public final static short PROB=268;
public final static short RESOLVE=269;
public final static short PEER=270;
public final static short ENEMY=271;
public final static short NEIGHBOR=272;
public final static short SIM=273;
public final static short START=274;
public final static short POPULATE=275;
public final static short DOTFUNC=276;
public final static short RECTFUNC=277;
public final static short ASSIGN=278;
public final static short LBRACE=279;
public final static short RBRACE=280;
public final static short LBRACK=281;
public final static short RBRACK=282;
public final static short LPARAN=283;
public final static short RPARAN=284;
public final static short ID=285;
public final static short NUM=286;
public final static short SEP=287;
public final static short COMMENT=288;
public final static short COL=289;
public final static short COM=290;
public final static short DOT=291;
public final static short NULL=292;
public final static short DASH=293;
public final static short EQ=294;
public final static short NOT=295;
public final static short GT=296;
public final static short LT=297;
public final static short NL=298;
public final static short WS=299;
public final static short EOF=300;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    4,    1,    5,    5,    5,    5,    5,    5,    5,
    5,    6,    8,    9,    7,    7,   10,    2,   13,   13,
   13,   13,   13,   14,   15,   16,   17,   18,   18,   18,
   18,   18,   20,   19,   21,   21,   22,   23,   25,   26,
   26,   26,   26,   28,   28,   28,   27,   27,   27,   27,
   24,   24,   24,   24,   24,   24,    3,   29,   29,   29,
   29,   29,   29,   30,   33,   33,   31,   32,   34,   34,
   11,   11,   12,   12,   12,   12,
};
final static short yylen[] = {                            2,
    7,    0,    1,    2,    2,    2,    2,    2,    2,    2,
    1,   12,   12,   12,    8,    8,   14,    1,    2,    2,
    2,    2,    1,   20,   12,    2,    8,    2,    2,    2,
    2,    1,    8,    8,    6,   11,   12,    3,    3,    9,
    9,   13,    3,    1,    1,    1,    1,    3,    3,    5,
    1,    1,    1,    2,    2,    2,    1,    2,    2,    2,
    2,    2,    1,   18,   13,   21,   15,   14,    3,    5,
    3,    5,    3,    6,    7,    9,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    3,    0,    0,    0,    0,
    0,    9,   10,    0,    0,    0,    0,    0,    0,    4,
    5,    6,    7,    8,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   18,
    0,    0,    0,    0,    0,    0,    0,   21,   22,    0,
    0,    0,   19,   20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,   57,    0,    0,    0,    0,
    0,    0,    0,    0,   15,   16,    0,    0,    0,   61,
   62,    0,    0,    0,   58,   59,   60,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   72,   12,    0,    0,   13,   14,    0,
    0,    0,    0,    0,   32,    0,   26,    0,    0,    0,
    0,    0,    0,    0,    0,   74,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   28,   29,   30,   31,    0,
    0,    0,    0,   17,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,    0,    0,    0,    0,   76,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   35,    0,    0,    0,    0,    0,
   27,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   34,   44,   46,   45,    0,    0,    0,   38,
   51,    0,    0,    0,    0,   33,    0,    0,    0,    0,
    0,    0,   43,    0,   56,   54,   55,    0,    0,   68,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   67,
    0,    0,    0,    0,   36,    0,    0,    0,    0,    0,
    0,    0,    0,   24,    0,    0,    0,    0,   39,   37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   64,   48,    0,    0,    0,    0,    0,    0,    0,   40,
   41,    0,    0,    0,   50,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,    0,    0,    0,    0,   65,
    0,    0,    0,    0,    0,    0,    0,    0,   66,
};
final static short yydgoto[] = {                          4,
    5,   39,   75,    0,    6,    7,    8,    9,   10,   11,
   81,   83,   40,   41,   42,  129,  130,  147,  148,  149,
  150,  151,  214,  245,  269,  225,  286,  239,   76,   77,
   78,   79,  262,    0,
};
final static short yysindex[] = {                      -274,
 -274, -274, -198,    0, -284,    0, -274, -274, -274, -274,
 -274,    0,    0, -291, -282, -248, -225, -221, -283,    0,
    0,    0,    0,    0, -196, -195, -194, -193, -204, -272,
 -212, -211, -210, -209, -208, -272, -272, -255, -201,    0,
 -272, -272, -187, -186, -185, -190, -183,    0,    0, -203,
 -202, -275,    0,    0, -200, -199, -192, -191, -189, -188,
 -184, -182, -269, -181, -180, -180, -177, -176, -175, -174,
 -173, -269, -269, -205,    0,    0, -269, -269, -269, -172,
 -171, -179, -169, -168,    0,    0, -167, -166, -170,    0,
    0, -165, -164, -163,    0,    0,    0, -162, -178, -161,
 -157, -156, -180, -160, -159, -158, -150, -142, -148, -155,
 -213, -154, -153, -152, -136, -151, -149, -147, -146, -181,
 -144, -143, -141, -139, -138, -131, -137, -115, -135, -264,
 -123, -129, -124,    0,    0, -122, -180,    0,    0, -134,
 -180, -133, -113, -130,    0, -256,    0, -264, -264, -264,
 -264, -128, -127, -126, -125,    0, -121, -120, -110, -119,
 -108, -118, -117, -116, -114,    0,    0,    0,    0, -104,
 -112, -106, -103,    0,  -94, -111, -109, -107, -102,  -88,
  -87,  -85, -105, -101, -100, -180,  -99,  -90,    0,  -89,
  -98,  -97,  -96,  -95, -180,  -80,  -79,    0,  -86,  -91,
  -83,  -76,  -82,  -75,  -73,  -81,  -78,  -77,  -74,  -72,
  -71,  -70,  -69,  -68,    0,  -67,  -65,  -61,  -63,  -62,
    0,  -64,  -60, -265,  -59, -254,  -57,  -56,  -55,  -54,
  -53,  -58,    0,    0,    0,    0,  -52,  -51,  -50,    0,
    0,  -44,  -43,  -42,  -46,    0,  -41,  -40,  -45,  -49,
  -39,  -48,    0, -259,    0,    0,    0,  -38,  -37,    0,
 -197,  -36,  -35,  -33,  -32,  -31,  -30,  -69,  -29,    0,
  -28,  -27,  -47,  -25,    0,  -20,  -20,  -26,  -24,  -22,
   -7,   -4,  -21,    0, -262,  -19,  -18,  -17,    0,    0,
  -16,  -15,  -13,  -20,    1,   -3,    4,    7,    3,    5,
    0,    0,  -34,   -9,   -6,   -5,   -2,   -1,  -20,    0,
    0,  -20,    2,    6,    0,    8,    9,   10,   13,   14,
   15,   11,   12,   17,    0,   20,   22,   18,   19,    0,
   27,   21,   24,   23,   29,   25,   39,   26,    0,
};
final static short yyrindex[] = {                         0,
    0,   32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   36,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   98,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   28,    0,
   30,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   31,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   33,   34,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -208,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,   37,    0,    0,    0,    0,    0,
   76,  -66,   16,    0,    0,    0,    0,  -84,    0,    0,
    0,    0,    0,    0,    0,   38, -276,    0,  -23,    0,
    0,    0,    0,    0,
};
final static int YYTABLESIZE=334;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         84,
  287,  162,   19,  144,  234,  235,  236,   25,   50,   51,
  163,  164,  165,    1,   30,   36,   26,  302,   72,  237,
  238,  266,   63,    2,    3,   37,   38,  294,   73,   74,
  295,  267,  315,  145,  146,  316,  114,   12,   13,  241,
  242,  243,  244,   20,   21,   22,   23,   24,   90,   91,
   27,   48,   49,   95,   96,   97,   53,   54,   14,   15,
   16,   17,   18,  166,  167,  168,  169,   92,   93,   94,
  156,   58,   59,   28,  158,  122,  123,   29,  271,  272,
   35,   31,   32,   33,   34,   52,   43,   44,   45,   46,
   47,   55,   56,   57,   60,   61,   62,   63,   64,   65,
   70,  110,   71,   87,    0,  100,   66,   67,  105,   68,
   69,  104,    0,   98,    0,    0,    0,   80,   82,  198,
   85,   86,  112,  113,   88,   89,  117,   99,  206,  101,
  102,  103,  118,  106,  107,  108,  109,  111,  115,  116,
  119,  120,  127,  121,  124,  125,  126,  128,  140,  131,
  142,  132,  133,  135,  152,  136,  153,  137,  138,  139,
  154,  141,  155,  143,  157,  159,  160,  176,  161,  178,
  170,  171,  172,  173,  183,  191,  174,  184,  175,  177,
  179,  180,  181,  185,  182,  187,  186,  188,  189,  192,
  193,  190,  194,  195,  200,  134,  201,  196,  197,  199,
  202,  203,  204,  205,  207,  208,  209,  210,  212,    0,
  215,  216,    0,    0,  228,  211,  213,  217,    0,  222,
  218,  219,  229,    0,  220,  221,  230,  251,  223,  224,
  226,  227,  265,  231,  232,  263,  283,  233,    0,  240,
  246,    0,  247,  248,  249,  250,  252,  253,  254,  255,
  256,  257,  258,  261,    0,  309,  259,  260,  288,  264,
  268,  270,  273,  274,  275,  285,  276,  277,  278,  280,
  281,  282,  284,  291,  289,  290,  292,  293,  304,  296,
  297,  298,  299,  300,  301,  305,  303,  306,  307,  310,
  308,  317,  311,  312,  322,  318,  313,  314,    0,  323,
  324,  328,    0,    0,    0,  279,  319,  320,  321,  325,
  326,  329,  332,  334,  336,  327,  330,  331,   11,  333,
  338,  335,   23,  337,  339,    0,   71,    0,   73,   75,
    0,   53,   52,   49,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         66,
  277,  258,  287,  268,  270,  271,  272,  299,  264,  265,
  267,  268,  269,  288,  298,  288,  299,  294,  288,  285,
  286,  281,  298,  298,  299,  298,  299,  290,  298,  299,
  293,  291,  309,  298,  299,  312,  103,    1,    2,  294,
  295,  296,  297,    7,    8,    9,   10,   11,   72,   73,
  299,   36,   37,   77,   78,   79,   41,   42,  257,  258,
  259,  260,  261,  148,  149,  150,  151,  273,  274,  275,
  137,  262,  263,  299,  141,  289,  290,  299,  276,  277,
  285,  278,  278,  278,  278,  287,  299,  299,  299,  299,
  299,  279,  279,  279,  278,  299,  299,    0,  299,  299,
  285,  280,  285,  279,   -1,  285,  299,  299,  279,  299,
  299,  278,   -1,  286,   -1,   -1,   -1,  299,  299,  186,
  298,  298,  280,  280,  299,  299,  285,  299,  195,  299,
  299,  299,  283,  299,  299,  299,  299,  299,  299,  299,
  283,  290,  279,  299,  299,  299,  299,  299,  280,  299,
  266,  299,  299,  298,  278,  299,  286,  299,  298,  298,
  285,  299,  285,  299,  299,  299,  280,  278,  299,  278,
  299,  299,  299,  299,  279,  278,  298,  290,  299,  299,
  299,  299,  299,  290,  299,  280,  290,  299,  298,  278,
  278,  299,  278,  299,  285,  120,  286,  299,  299,  299,
  299,  299,  299,  299,  285,  285,  293,  299,  285,   -1,
  286,  285,   -1,   -1,  280,  299,  299,  299,   -1,  291,
  299,  299,  284,   -1,  299,  298,  290,  286,  299,  299,
  299,  299,  281,  296,  299,  285,  284,  298,   -1,  299,
  298,   -1,  299,  299,  299,  299,  299,  299,  299,  294,
  294,  294,  299,  299,   -1,  290,  298,  298,  285,  299,
  299,  299,  299,  299,  298,  286,  299,  299,  299,  299,
  299,  299,  298,  281,  299,  298,  281,  299,  282,  299,
  299,  299,  299,  299,  298,  282,  286,  281,  286,  299,
  286,  290,  299,  299,  282,  290,  299,  299,   -1,  286,
  286,  282,   -1,   -1,   -1,  268,  299,  299,  299,  299,
  299,  290,  286,  290,  286,  299,  299,  299,  287,  299,
  282,  299,  287,  299,  299,   -1,  299,   -1,  299,  299,
   -1,  299,  299,  299,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=300;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"GRIDSIZE","CLASS","STATE","GRIDTYPE","ALIAS","BOUND","WRAP",
"TRANS","TRANSRULE","TYPE","CONDITION","PROB","RESOLVE","PEER","ENEMY",
"NEIGHBOR","SIM","START","POPULATE","DOTFUNC","RECTFUNC","ASSIGN","LBRACE",
"RBRACE","LBRACK","RBRACK","LPARAN","RPARAN","ID","NUM","SEP","COMMENT","COL",
"COM","DOT","NULL","DASH","EQ","NOT","GT","LT","NL","WS","EOF",
};
final static String yyrule[] = {
"$accept : exgol",
"exgol : init_section SEP NL trans_section SEP NL simulation_section",
"comment :",
"init_section : init_statements",
"init_statements : grid_def init_statements",
"init_statements : gridtype_def init_statements",
"init_statements : class_def init_statements",
"init_statements : state_def init_statements",
"init_statements : alias_dec init_statements",
"init_statements : COMMENT init_statements",
"init_statements : NL init_statements",
"init_statements : NL",
"grid_def : WS GRIDSIZE WS ASSIGN WS LBRACE WS dim_list WS RBRACE WS NL",
"class_def : WS CLASS WS ASSIGN WS LBRACE WS identifier_list WS RBRACE WS NL",
"state_def : WS STATE WS ASSIGN WS LBRACE WS identifier_list WS RBRACE WS NL",
"gridtype_def : WS GRIDTYPE WS ASSIGN WS BOUND WS NL",
"gridtype_def : WS GRIDTYPE WS ASSIGN WS WRAP WS NL",
"alias_dec : WS ALIAS WS ID WS ASSIGN WS LBRACE WS identifier_list WS RBRACE WS NL",
"trans_section : trans_statements",
"trans_statements : trans_def trans_statements",
"trans_statements : transrule_def trans_statements",
"trans_statements : COMMENT trans_statements",
"trans_statements : NL trans_statements",
"trans_statements : NL",
"trans_def : WS TRANS WS ID WS ASSIGN WS LBRACE WS identifier_list WS RBRACE WS DASH WS GT WS ID WS NL",
"transrule_def : WS TRANSRULE WS ID WS LBRACE WS rule_expressions WS RBRACE WS NL",
"rule_expressions : type_def optional_expressions",
"type_def : WS TYPE WS ASSIGN WS ID WS NL",
"optional_expressions : rule_class optional_expressions",
"optional_expressions : resolve optional_expressions",
"optional_expressions : prob optional_expressions",
"optional_expressions : condition optional_expressions",
"optional_expressions : NL",
"resolve : WS RESOLVE WS ASSIGN WS ID WS NL",
"rule_class : WS CLASS WS ASSIGN WS ID WS NL",
"prob : WS PROB WS ASSIGN WS NUM",
"prob : PROB WS ASSIGN WS NUM WS DOT WS NUM WS NL",
"condition : WS CONDITION WS ASSIGN WS lhs WS compare WS rhs WS NL",
"lhs : WS condition_stmt WS",
"rhs : WS condition_stmt WS",
"condition_stmt : WS ID WS LBRACK WS range_stmt WS RBRACK WS",
"condition_stmt : WS builtin_alias WS LBRACK WS range_stmt WS RBRACK WS",
"condition_stmt : WS builtin_alias WS DOT WS ID WS LBRACK WS range_stmt WS RBRACK WS",
"condition_stmt : WS NUM WS",
"builtin_alias : PEER",
"builtin_alias : NEIGHBOR",
"builtin_alias : ENEMY",
"range_stmt : NUM",
"range_stmt : NUM COM range_stmt",
"range_stmt : NUM DASH NUM",
"range_stmt : NUM DASH NUM COM range_stmt",
"compare : EQ",
"compare : LT",
"compare : GT",
"compare : GT EQ",
"compare : LT EQ",
"compare : NOT EQ",
"simulation_section : simulation_stmts",
"simulation_stmts : populate_stmt simulation_stmts",
"simulation_stmts : sim_stmt simulation_stmts",
"simulation_stmts : start_stmt simulation_stmts",
"simulation_stmts : COMMENT simulation_stmts",
"simulation_stmts : NL simulation_stmts",
"simulation_stmts : NL",
"populate_stmt : WS POPULATE WS LPARAN WS ID WS COM WS ID WS COM WS fill_func WS RPARAN WS NL",
"fill_func : WS DOTFUNC WS LBRACK WS NUM WS COM WS NUM WS RBRACK WS",
"fill_func : WS RECTFUNC WS LBRACK WS NUM WS COM WS NUM WS COM WS NUM WS COM WS NUM WS RBRACK WS",
"sim_stmt : WS SIM WS ID WS ASSIGN WS LBRACE WS identifier_list WS RBRACE WS NL WS",
"start_stmt : WS START WS LPARAN WS NUM WS COM WS ID WS RPARAN WS NL",
"numeric_list : WS NUM WS",
"numeric_list : WS NUM WS COM numeric_list",
"dim_list : WS NUM WS",
"dim_list : WS NUM WS COM dim_list",
"identifier_list : WS ID WS",
"identifier_list : WS ID WS COM WS identifier_list",
"identifier_list : WS ID WS COL WS ID WS",
"identifier_list : WS ID WS COL WS ID WS COM identifier_list",
};

//#line 224 "exgol.y"

/* a reference to the lexer object */
private Yylex lexer;

static Simulation s;


Vector<Integer> prox = new Vector<Integer>(); //proximity
Vector<String> idList = new Vector<String>();
static Vector<Float> popArgs = new Vector<Float>();

TransRule trRule = new TransRule("Deafult");
CondExpr cond;
CondExpr LHS, RHS;

PopulateType ptype;



/* interface to the lexer */
private int yylex () {
  count++;
  System.out.println(count + " ");
  int yyl_return = 0;
  try {
    yyl_return = lexer.yylex();
  }
  catch (IOException e) {
    System.err.println("IO error :"+e);
  }
  if (yyl_return == EOF) { return 0;}
  return yyl_return;
}

/* error reporting */
public void yyerror (String error) {
  System.err.println ("Error: " + error);
}

/* lexer is created in the constructor */
public Parser(Reader r) {
	count=0;
  lexer = new Yylex(r, this);
}


private void addColor(String cls, String clr){
  s.classColors.put(cls, Color.BLACK);
}

private void addDim(int x){
	s.gridsize.add(x);
}

private void addID(String i){
	//System.out.println(i);
	idList.add(i);
}
private void setGridType(int m){
	switch(m){
		case 1:  s.gridtype = GridType.BOUNDED;break;
		case 2:  s.gridtype = GridType.WRAPPED;break;
		default: s.gridtype = GridType.BOUNDED;break;
	}
}

private void printIDList(){
	//System.out.println("printing");
	for (Enumeration e = idList.elements(); e.hasMoreElements();)
		{
			String ID = (String) e.nextElement();
			System.out.println(ID);
		}

}

private void addClasses(){
	for (Enumeration e = idList.elements(); e.hasMoreElements();)
		{
			String ID = (String) e.nextElement();
			s.classes.add(ID);
		}
	idList.clear();
}

private void addStates(){
	for (Enumeration e = idList.elements(); e.hasMoreElements();)
		{
			String ID = (String) e.nextElement();
			s.states.add(ID);
		}
	idList.clear();
}

private void addTrans(String transTo, String transName){
	Trans trans = new Trans();
	trans.from = new Vector<String>();
	for (Enumeration e = idList.elements(); e.hasMoreElements();)
		{
			String ID = (String) e.nextElement();
			trans.from.add(ID);
		}
	idList.clear();

	trans.to = transTo;
	trans.name = transName;
	s.trans.add(trans);
}

private void setConditionExpr(int n){
//	System.out.println("Cond Num - " + n);
	cond = new CondExpr(n);
}

private void setConditionExpr(String condClass, String condState){
	System.out.println(condClass + " - " + condState);
	cond = new CondExpr(condClass, condState, prox);
}

private void setLHS(){
//	System.out.println("LHS ");
	LHS = cond;
	cond = new CondExpr(1);
}
private void setRHS(){
//	System.out.println("RHS ");
	RHS = cond;
	cond = new CondExpr(1);
}

private void setProx(int n){
	System.out.println("Prox:" + n);
	prox = new Vector<Integer>();
	prox.add(n);
}

private void setRuleType(String ruleName){
	//System.out.println("Rule Type " + ruleName);
	for (Enumeration e = s.trans.elements(); e.hasMoreElements();)
	{

		Trans tr = (Trans) e.nextElement();
		//System.out.println("Compare To " + tr.getName());
		if (ruleName.equals(tr.getName())){
		trRule.type = tr;
		//System.out.println("Rule Type " + ruleName);
		};

	}

}

private void setRuleName(String n){
//	System.out.println("Rule name: " + n);
	trRule.name = n;
}

private void setCondition(String relop){
	RelopType r = RelopType.EQ;
//	System.out.println("Relop " + relop);
	if(relop.equals("GT")){r = RelopType.GT;}
	if(relop.equals("GET")){r = RelopType.GET;}
	if(relop.equals("LT")){r = RelopType.LT;}
	if(relop.equals("LET")){r = RelopType.LET;}
	if(relop.equals("NEQ")){r = RelopType.NEQ;}
	trRule.cond = new Condition(LHS, r, RHS);
/*
	System.out.println("Name " + trRule.name);
	System.out.println("Type " + trRule.type.name);
	System.out.println("LHSClass " + LHS.condClass);
	System.out.println("LHS " + LHS.condState);
	System.out.println("RHSClass " + LHS.condClass);
	System.out.println("LHS " + LHS.condState);
*/
}

private void addRule(){
	//System.out.println("Rule ");
	//System.out.println("Name " + trRule.name);
	//System.out.println("Type " + trRule.type.name);
/*
	for (Enumeration e = trRule.classes.elements(); e.hasMoreElements();)
	{
		String r = (String) e.nextElement();
		//System.out.println("Rule Class " + r);
	}
*/
	s.transrule.add(trRule);
	trRule = new TransRule("Deafult");
}

private void popParams(float[] params){
	//popArgs.clear();
	popArgs = new Vector<Float>();
	for(float i:params){
	System.out.println(i);
	popArgs.add(new Float(i));
	}
}
private void setPopType(String t){
	if (t.equals("dot")) ptype = PopulateType.DOT;
	else if (t.equals("rectangle")) ptype = PopulateType.RECTANGLE;
	else System.out.println("No Pop Type matched for: " + t);
}

private void popSim(String sClass, String sState){
	s.populate.add(new Populate(sClass, sState, ptype, popArgs));
	//System.out.println(sClass + " - " + sState + " - " + ptype);
	//popArgs.clear();
	//popArgs.add(new Float(17));
	//popArgs.add(new Float(18));
	//s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popArgs));
}

public static void main(String args[]) throws IOException {

		s = Simulation.createSimulation();
		s.generations = 0;

		s.gridsize = new Vector<Integer>();
		s.classColors = new Hashtable<String, Color>();
		s.classes = new Vector<String>();
		s.states = new Vector<String>();
		s.trans = new Vector<Trans>();
		s.transrule = new Vector<TransRule>();
		s.simrules = new Vector<TransRule>();
		s.populate = new Vector<Populate>();


	  	Parser yyparser = new Parser(new FileReader(args[0]));
	 	yyparser.yyparse();
/**/
		for (Enumeration e = s.transrule.elements(); e.hasMoreElements();)
		{
		TransRule tr = (TransRule) e.nextElement();
		s.simrules.add(tr);
//		System.out.println(tr.name + ";" + tr.type.name);
		}

		for (Enumeration e = s.populate.elements(); e.hasMoreElements();)
		{
		Populate p = (Populate) e.nextElement();
		System.out.println(p.className + " - " + p.stateName + " - " + p.populateType);


			for (Enumeration e2 = p.populateArgs.elements(); e2.hasMoreElements();)
			{
				Float r = (Float) e2.nextElement();
				System.out.println(r);
			}

		}
		//s.simrules.add(birth);
		//s.simrules.add(death);
		//s.simrules.add(crowded);

		/*
		Vector<Float> popDot1;
		Vector<Float> popDot2;


		//GLIDER 1
		popArgs = new Vector<Float>();
		popArgs.add(new Float(17));
		popArgs.add(new Float(17));
		popArgs.add(new Float(19));
		popArgs.add(new Float(17));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));

		popDot1 = new Vector<Float>();
		popDot1.add(new Float(17));
		popDot1.add(new Float(18));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));

		popDot2 = new Vector<Float>();
		popDot2.add(new Float(18));
		popDot2.add(new Float(19));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot2));
*/

		GUI gui = new GUI();
		gui.run();




}
//#line 752 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop");
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 73 "exgol.y"
{System.out.println("Exgol Parsed");}
break;
case 3:
//#line 78 "exgol.y"
{System.out.println("Init Section Parsed");}
break;
case 11:
//#line 88 "exgol.y"
{System.out.println("Init Empty Line");}
break;
case 12:
//#line 90 "exgol.y"
{/*System.out.println("Grid Generated");*/
								}
break;
case 13:
//#line 93 "exgol.y"
{/*System.out.println("List of Classes ");*/
								addClasses();/*printIDList();*/
								}
break;
case 14:
//#line 97 "exgol.y"
{/*System.out.println("List of States ");*/
								addStates();/*printIDList();*/
								}
break;
case 15:
//#line 100 "exgol.y"
{setGridType(1);}
break;
case 16:
//#line 101 "exgol.y"
{setGridType(2);}
break;
case 17:
//#line 102 "exgol.y"
{System.out.println("Alias " + val_peek(12).sval + " for " + val_peek(9).sval);}
break;
case 18:
//#line 105 "exgol.y"
{System.out.println("Trans Section Parsed");}
break;
case 19:
//#line 108 "exgol.y"
{
						/*System.out.println("Transformation");*/
						}
break;
case 20:
//#line 111 "exgol.y"
{
						/*System.out.println("Trans Rule");*/
						}
break;
case 23:
//#line 116 "exgol.y"
{
						/*System.out.println("Trans Empty Line");*/
						}
break;
case 24:
//#line 120 "exgol.y"
{addTrans(val_peek(11).sval,val_peek(18).sval);}
break;
case 25:
//#line 121 "exgol.y"
{setRuleName(val_peek(10).sval);addRule();}
break;
case 27:
//#line 126 "exgol.y"
{setRuleType(val_peek(5).sval);}
break;
case 33:
//#line 137 "exgol.y"
{System.out.println("resolve:" + val_peek(4).sval);}
break;
case 34:
//#line 138 "exgol.y"
{System.out.println("class:" + val_peek(4).sval);}
break;
case 36:
//#line 140 "exgol.y"
{System.out.println("prob:" + (val_peek(8).ival*10 +val_peek(6).ival*0.1));}
break;
case 37:
//#line 141 "exgol.y"
{setCondition(val_peek(8).sval);}
break;
case 38:
//#line 142 "exgol.y"
{setLHS();}
break;
case 39:
//#line 143 "exgol.y"
{setRHS();}
break;
case 40:
//#line 145 "exgol.y"
{setConditionExpr(val_peek(8).sval,"EMPTY");}
break;
case 41:
//#line 146 "exgol.y"
{setConditionExpr(val_peek(8).sval,"EMPTY");}
break;
case 42:
//#line 147 "exgol.y"
{setConditionExpr(val_peek(12).sval, val_peek(10).sval);}
break;
case 43:
//#line 148 "exgol.y"
{setConditionExpr(val_peek(2).ival);}
break;
case 44:
//#line 150 "exgol.y"
{yyval.sval = val_peek(0).sval;}
break;
case 45:
//#line 151 "exgol.y"
{yyval.sval = val_peek(0).sval;}
break;
case 46:
//#line 152 "exgol.y"
{yyval.sval = val_peek(0).sval;}
break;
case 47:
//#line 154 "exgol.y"
{setProx(val_peek(0).ival);}
break;
case 51:
//#line 159 "exgol.y"
{yyval.sval = "EQ";}
break;
case 52:
//#line 160 "exgol.y"
{yyval.sval = "LT";}
break;
case 53:
//#line 161 "exgol.y"
{yyval.sval = "GT";}
break;
case 54:
//#line 162 "exgol.y"
{yyval.sval = "GET";}
break;
case 55:
//#line 163 "exgol.y"
{yyval.sval = "LET";}
break;
case 56:
//#line 164 "exgol.y"
{yyval.sval = "NEQ";}
break;
case 64:
//#line 182 "exgol.y"
{
							popSim(val_peek(15).sval, val_peek(13).sval);
							/*System.out.println("Populate " + $3.sval + "," + $5.sval + "," + $7.sval);*/
							}
break;
case 65:
//#line 191 "exgol.y"
{
						setPopType("dot");
						popParams( new float[] {val_peek(10).ival, val_peek(8).ival});
						/*System.out.println("Dot Function");*/
						}
break;
case 66:
//#line 196 "exgol.y"
{
						setPopType("rectangle");
						popParams( new float[] {val_peek(18).ival, val_peek(16).ival, val_peek(14).ival, val_peek(12).ival});
						/*System.out.println("Rectangle Function");*/
						}
break;
case 67:
//#line 203 "exgol.y"
{System.out.println("Sim Function");}
break;
case 68:
//#line 206 "exgol.y"
{System.out.println("Start Function");}
break;
case 69:
//#line 211 "exgol.y"
{yyval.sval = Integer.toString(val_peek(2).ival);}
break;
case 70:
//#line 212 "exgol.y"
{yyval.sval = Integer.toString(val_peek(4).ival) + "," + val_peek(2).sval;}
break;
case 71:
//#line 214 "exgol.y"
{addDim(val_peek(2).ival);}
break;
case 72:
//#line 215 "exgol.y"
{addDim(val_peek(4).ival);}
break;
case 73:
//#line 218 "exgol.y"
{addID(val_peek(2).sval); }
break;
case 74:
//#line 219 "exgol.y"
{addID(val_peek(5).sval); }
break;
case 75:
//#line 220 "exgol.y"
{addID(val_peek(6).sval); addColor(val_peek(6).sval, val_peek(4).sval);}
break;
case 76:
//#line 221 "exgol.y"
{addID(val_peek(8).sval); addColor(val_peek(8).sval, val_peek(6).sval);}
break;
//#line 1119 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
