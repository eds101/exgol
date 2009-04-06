package columbia.exgol;

import java.util.Vector;

public class Condition {
	CondExpr LHS;
	String op; //maybe an enum?
	CondExpr RHS;
}

class CondExpr {
	String cellClass; //i would name it class; its a keyword
	String state;
	Vector<Integer> proximity;
}
