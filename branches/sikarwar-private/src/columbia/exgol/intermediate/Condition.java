package columbia.exgol.intermediate;

    public class Condition {

	public CondExpr LHS;
	public RelopType op;
	public CondExpr RHS;
	public boolean alwaysTrue;

	public Condition() {
		alwaysTrue = true;
	}

	public Condition(CondExpr LHS, RelopType op, CondExpr RHS) {
		alwaysTrue = false;
		this.LHS = LHS;
		this.op = op;
		this.RHS = RHS;
	}
}