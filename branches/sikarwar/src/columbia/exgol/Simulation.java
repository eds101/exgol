package columbia.exgol;

import java.util.Vector;
import java.util.Hashtable;
import java.awt.Color;

public class Simulation {
	public Hashtable<String, Color> classes;
	public Vector<String> states;
	public Vector<Integer> gridsize;
	public GridType gridtype;
	public Vector<Trans> trans;
	public Vector<TransRule> transrule;
}
