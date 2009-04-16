package columbia.exgol;

import java.util.Vector;
import java.util.Hashtable;
import java.awt.Color;

public class Simulation {
	// Init section
	public Hashtable<String, Color> classes;
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

}

