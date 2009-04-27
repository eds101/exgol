package columbia.exgol.intermediate;

import columbia.exgol.simulation.Cell;
import java.util.Vector;

/**
 *
 * @author sikarwar
 */
public class TransRule {

	public String name;
	public Trans type;
	public Vector<String> classes;
	public columbia.exgol.intermediate.Condition cond;
	public Vector<String> resolve;
	public float prob;

	public TransRule(String name) {
		this.name = name;
		prob = (float) 1.0;
		//cond = new Condition();
		//cond.alwaysTrue = true;
		resolve = new Vector<String>();
		classes = new Vector<String>();
	}

	public boolean appliesToClass(String className) {
		if (classes.size() == 0 || classes.contains(className)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean appliesToState(String state) {
		if (type.from.contains(state)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkCondition(Cell[][] oldCells, int x, int y, GridType gt) {
		//TODO: handle WRAPPED grids
		int lhsCount, rhsCount;

		if (cond.alwaysTrue) {
			return true;
		}

		lhsCount = cond.LHS.evaluate(oldCells, x, y, gt);
		rhsCount = cond.RHS.evaluate(oldCells, x, y, gt);

		switch (cond.op) {
			case GT:
				return lhsCount > rhsCount;
			case GET:
				return lhsCount >= rhsCount;
			case LT:
				return lhsCount < rhsCount;
			case LET:
				return lhsCount <= rhsCount;
			case EQ:
				return lhsCount == rhsCount;
			case NEQ:
				return lhsCount != rhsCount;
		}
		return false;
	}
}
