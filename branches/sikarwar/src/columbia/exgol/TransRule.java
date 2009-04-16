package columbia.exgol;

import java.util.Vector;

public class TransRule {
	public String name;
	public Trans type;
	public Vector<String> classes;
	public columbia.exgol.Condition cond;
	public Vector<String> resolve;
	public float prob;

	public TransRule(String name) {
		this.name = name;
		prob = (float) 1.0;
		cond = new columbia.exgol.Condition();
		resolve = new Vector<String>();
		classes = new Vector<String>();
	}
}
