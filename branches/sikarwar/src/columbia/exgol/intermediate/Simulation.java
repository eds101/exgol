package columbia.exgol.intermediate;

import java.util.Vector;
import java.util.Hashtable;
import java.awt.Color;

public class Simulation {
	// Init section
	public Vector<String> classes;
	public Hashtable<String, Color> classColors;
	public Vector<String> states;
	public Vector<Integer> gridsize;
	public GridType gridtype;

	// Transition section
	public Vector<Trans> trans;
	public Vector<TransRule> transrule;
	
	// Simulation section
	public Vector<TransRule> simrules;
	public int generations;
	public Vector<Populate> populate;

	//singleton
	private Simulation() {}
	private static Simulation s = null;
	public static Simulation createSimulation() {
		if (s == null)
			s = new Simulation();
		return s;
	}
	public static Simulation getSimulation() {
		return s;
	}
}

