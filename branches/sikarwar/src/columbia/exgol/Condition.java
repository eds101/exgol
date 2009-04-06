package columbia.exgol;

import java.util.Vector;

public class Condition {
	CondExpr LHS;
	String op; //maybe an enum?
	CondExpr RHS;
}

class CondExpr {
	private boolean isNum;
	private int value;
	private String cellClass; //i would name it class; but it's a keyword
	private String state;
	private Vector<Integer> proximity; //for multi dimension

	public CondExpr(int value) {
		isNum = true;
	}

	public CondExpr(String cellClass, String state, Vector<Integer> prox) {
		isNum = false;
		this.cellClass = cellClass;
		this.state = state;
		this.proximity = prox;
	}

	public int evaluate() {
		if (isNum)
			return value;
		/* else evaluate condition here */
		return 0;
	}
}
