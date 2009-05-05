package columbia.exgol.intermediate;

import columbia.exgol.simulation.Cell;
import java.util.Hashtable;
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

	public Vector<String> checkCondition(Cell[][] oldCells, int x, int y, GridType gt) {
		//TODO: handle WRAPPED grids
		Integer lhsCount, rhsCount;
        Vector<String> classes = new Vector<String>();
		if (cond.alwaysTrue) {
            classes.add("");
			return classes;
		}

        Hashtable<String, Integer> lhs, rhs;
		lhs = cond.LHS.evaluate(oldCells, x, y, gt);
		rhs = cond.RHS.evaluate(oldCells, x, y, gt);

        classes.addAll(lhs.keySet());
        for(String cl : rhs.keySet()){
            if(!classes.contains(cl))
                classes.add(cl);
        }

        classes.remove("EMPTY");
        
        for(int i = classes.size() - 1; i >= 0; i--){
            String cl = classes.get(i);
            boolean remove = false;
            lhsCount = lhs.get("EMPTY") == null ? lhs.get(cl) : lhs.get("EMPTY");
            rhsCount = rhs.get("EMPTY") == null ? rhs.get(cl) : rhs.get("EMPTY");
            if(lhsCount == null || rhsCount == null)
               remove = true;
            else

        
            switch (cond.op) {
                case GT:
                    if (!(lhsCount > rhsCount))
                        remove = true;
                    break;
                case GET:
                    if (!(lhsCount >= rhsCount))
                        remove = true;
                    break;
                case LT:
                    if (!(lhsCount < rhsCount))
                        remove = true;
                    break;
                case LET:
                    if (!(lhsCount <= rhsCount))
                        remove = true;
                    break;
                case EQ:
                    if (!(lhsCount == rhsCount))
                        remove = true;
                    break;
                case NEQ:
                    if (!(lhsCount != rhsCount))
                        remove = true;
                    break;
            }
            if (remove) {
                classes.remove(cl);
            }
        }
        return classes;
	}
}
