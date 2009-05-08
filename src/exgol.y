%{
	import java.io.*;
	import java.awt.*;
	import javax.swing.*;
	import columbia.exgol.intermediate.*;
	import columbia.exgol.simulation.GUI;
	import java.awt.Color;
	import java.util.Hashtable;
	import java.util.Vector;
	import java.util.Enumeration;
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
%token SIM
%token START
%token POPULATE

%token DOTFUNC
%token RECTFUNC
%token BLINKER
%token GLIDER

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
%token COMMENT
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
exgol		: init_section SEP NL trans_section SEP NL simulation_section{System.out.println("Exgol Parsed");};

//init
init_section	: init_statements {System.out.println("Init Section Parsed");};

init_statements	: 
		grid_def NL init_statements|
		gridtype_def NL init_statements|
		class_def NL init_statements|
		state_def NL init_statements|
		alias_dec NL init_statements|
		NL init_statements|
		grid_def NL  |
		gridtype_def NL  |
		class_def NL  |
		state_def NL  |
		alias_dec NL  |
		NL {System.out.println("Init Empty Line");};

grid_def	: GRIDSIZE ASSIGN LBRACE dim_list RBRACE	{//System.out.println("Grid Generated");
								};

class_def	: CLASS	ASSIGN LBRACE identifier_list RBRACE	{//System.out.println("List of Classes ");
								addClasses();//printIDList();
								};

state_def	: STATE	ASSIGN LBRACE identifier_list RBRACE	{//System.out.println("List of States ");
								addStates();//printIDList();
								};
gridtype_def	: GRIDTYPE ASSIGN BOUND 			{setGridType(1);}|
		  GRIDTYPE ASSIGN WRAP 			{setGridType(2);};
alias_dec	: ALIAS ID ASSIGN LBRACE identifier_list RBRACE {System.out.println("Alias " + $2.sval + " for " + $5.sval);};

//trans
trans_section	: trans_statements {System.out.println("Trans Section Parsed");};

trans_statements:
		trans_def NL trans_statements |
		transrule_def NL trans_statements |
		transrule_def NL |
		trans_def NL |
		NL trans_statements |
		NL;

trans_def	: TRANS ID ASSIGN LBRACE identifier_list RBRACE DASH GT ID	{addTrans($9.sval,$2.sval);};
transrule_def	: TRANSRULE ID LBRACE rule_expressions RBRACE	{setRuleName($2.sval);addRule();};

rule_expressions: 
		type_def NL optional_expressions|
		NL rule_expressions |
		type_def nl_string|
		type_def;
		
nl_string	: NL nl_string |
		  NL;
type_def	:
		TYPE ASSIGN ID {setRuleType($3.sval);};

optional_expressions:
		rule_class NL optional_expressions|
		resolve	 NL	optional_expressions|
		prob NL	optional_expressions|
		condition NL	optional_expressions|
		NL		optional_expressions|
		condition |
		resolve |
		prob |
		rule_class |
		NL;

resolve		: RESOLVE ASSIGN ID {System.out.println("resolve:" + $3.sval);};
rule_class	: CLASS ASSIGN ID {System.out.println("class:" + $3.sval);};
prob		: PROB ASSIGN NUM| PROB ASSIGN NUM DOT NUM {setProb($3.ival*10 +$5.ival*0.1);}
condition	: CONDITION ASSIGN  lhs compare rhs {setCondition($4.sval);};
lhs		: condition_stmt {setLHS();};
rhs		: condition_stmt {setRHS();};

condition_stmt	: ID LBRACK range_stmt RBRACK {setConditionExpr($1.sval,"*");}|
		  builtin_alias LBRACK range_stmt RBRACK {setConditionExpr($1.sval,"*");}|
		  builtin_alias DOT ID LBRACK range_stmt RBRACK {setConditionExpr($1.sval, $3.sval);}|
		  NUM {setConditionExpr($1.ival);};

builtin_alias	: PEER {$$.sval = $1.sval;}|
		  NEIGHBOR {$$.sval = $1.sval;}|
		  ENEMY {$$.sval = $1.sval;};

range_stmt	: NUM {setProx($1.ival);}|
		  NUM COM range_stmt|
		  NUM DASH NUM|
		  NUM DASH NUM COM range_stmt;

compare		: EQ {$$.sval = "EQ";}|
		  LT {$$.sval = "LT";}|
		  GT {$$.sval = "GT";}|
		  GT EQ {$$.sval = "GET";}|
		  LT EQ {$$.sval = "LET";}|
		  NOT EQ {$$.sval = "NEQ";};
		;

//sim

simulation_section:
		simulation_stmts;

simulation_stmts:
		populate_stmt NL simulation_stmts|
		sim_stmt NL simulation_stmts|
		start_stmt NL simulation_stmts|
		NL simulation_stmts|
		start_stmt|
		sim_stmt|
		populate_stmt|
		NL;


populate_stmt:
	POPULATE LPARAN ID COM ID COM fill_func RPARAN {
							popSim($3.sval, $5.sval);
							//System.out.println("Populate " + $3.sval + "," + $5.sval + "," + $7.sval);
							};


fill_func:
	//DOTFUNC LBRACK NUM COM NUM RBRACK {System.out.println("Dot Function");}|
	//RECTFUNC LBRACK NUM COM NUM COM NUM COM NUM RBRACK {System.out.println("Rectangle Function");};
	DOTFUNC LBRACK NUM COM NUM RBRACK {	
						setPopType("dot");
						popParams( new float[] {$3.ival, $5.ival});
						//System.out.println("Dot Function");
						}|
	RECTFUNC LBRACK NUM COM NUM COM NUM COM NUM RBRACK {
						setPopType("rectangle");
						popParams( new float[] {$3.ival, $5.ival, $7.ival, $9.ival});
						//System.out.println("Rectangle Function");
						}|
	BLINKER LBRACK NUM COM NUM COM ID RBRACK {
						setPopType("rectangle");
						if($7.sval.equalsIgnoreCase("h"))
							popParams( new float[] {$3.ival, $5.ival, $3.ival+2, $5.ival});
						else if($7.sval.equalsIgnoreCase("v"))
							popParams( new float[] {$3.ival, $5.ival, $3.ival, $5.ival+2});
						else
							yyerror("Incorrect blinker type");
						}|
	GLIDER LBRACK NUM COM NUM COM ID RBRACK {
					if($7.sval.equalsIgnoreCase("NW")){
						setPopType("rectangle");
						popParams( new float[] {$3.ival, $5.ival, $3.ival+2, $5.ival});
						setPopType("rectangle");
						popParams( new float[] {$3.ival, $5.ival+1, $3.ival, $5.ival+1});
						setPopType("rectangle");
						popParams( new float[] {$3.ival+1, $5.ival+2, $3.ival+1, $5.ival+2});
					}
					else if($7.sval.equalsIgnoreCase("NE"))
						popParams( new float[] {$3.ival, $5.ival, $3.ival, $5.ival+2});
					
					else if($7.sval.equalsIgnoreCase("SE"))
							;
					else if($7.sval.equalsIgnoreCase("SW"))
					;
					else
						yyerror("Incorrect glider type");
					};


sim_stmt:
	SIM ID ASSIGN LBRACE identifier_list RBRACE {System.out.println("Sim Function");};

start_stmt:
	START LPARAN NUM COM ID RPARAN {System.out.println("Start Function");};


//generic

numeric_list	: NUM {$$.sval = Integer.toString($1.ival);}|
		  NUM COM numeric_list {$$.sval = Integer.toString($1.ival) + "," + $3.sval;};

dim_list	: NUM {addDim($1.ival);}|
		  NUM COM dim_list {addDim($1.ival);};


identifier_list : ID 				{addID($1.sval); } |
		  ID COM identifier_list 	{addID($1.sval); } |
		  ID COL ID 			{addID($1.sval); addColor($1.sval, $3.sval);} |
		  ID COL ID COM identifier_list {addID($1.sval); addColor($1.sval, $3.sval);};

%%

/* a reference to the lexer object */
private Yylex lexer;

static Simulation s;

				
Vector<Integer> prox = new Vector<Integer>(); //proximity
Vector<String> idList = new Vector<String>();
static Vector<Float> popArgs = new Vector<Float>();

TransRule trRule = new TransRule("Default");
CondExpr cond;
CondExpr LHS, RHS;

PopulateType ptype;



/* interface to the lexer */
private int yylex () {
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
  System.exit(1);
}

/* lexer is created in the constructor */
public Parser(Reader r) {
  lexer = new Yylex(r, this);
}


private void addColor(String cls, String clr){
  	if(clr.equalsIgnoreCase("red"))
		s.classColors.put(cls, Color.RED);
	if(clr.equalsIgnoreCase("black"))
	        s.classColors.put(cls, Color.BLACK);
	if(clr.equalsIgnoreCase("blue"))
		s.classColors.put(cls, Color.BLUE);
	if(clr.equalsIgnoreCase("green"))
	        s.classColors.put(cls, Color.GREEN);
	if(clr.equalsIgnoreCase("orange"))
	        s.classColors.put(cls, Color.ORANGE);
        if(clr.equalsIgnoreCase("yellow"))
		s.classColors.put(cls, Color.YELLOW);
	if(clr.equalsIgnoreCase("white"))
	        s.classColors.put(cls, Color.WHITE);
}

private void addDim(int x){
	s.gridsize.add(x);
}

private void addID(String i){
	System.out.println("adding " + i);
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
	for(int i=idList.size()-1;i>=0;i--)
	{
		String ID = (String) idList.get(i);
		System.out.println("adding state: " + ID);
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

private void setProb(double prob){
	trRule.prob = (float)prob;
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
	trRule = new TransRule("Default");
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
	try {
		UIManager.setLookAndFeel(
			UIManager.getSystemLookAndFeelClassName());
	} catch (Exception e) {}
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
	for (Enumeration e = s.transrule.elements(); e.hasMoreElements();)
	{		
		TransRule tr = (TransRule) e.nextElement();
		s.simrules.add(tr);
	}

	for (Enumeration e = s.populate.elements(); e.hasMoreElements();)
	{		
		Populate p = (Populate) e.nextElement();
		for (Enumeration e2 = p.populateArgs.elements(); e2.hasMoreElements();)
		{		
			Float r = (Float) e2.nextElement();
			System.out.println(r);
		}
	}
	GUI gui = new GUI();
	gui.run();
}
