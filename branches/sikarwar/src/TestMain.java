/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import columbia.exgol.intermediate.*;
import columbia.exgol.simulation.GUI;
import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author sikarwar
 */
public class TestMain {

	public static void main(String args[]) {
		Simulation s = Simulation.createSimulation();
		s.generations = 0;

		s.gridsize = new Vector<Integer>();
		s.gridsize.add(100);
		s.gridsize.add(50);

		s.gridtype = GridType.BOUNDED;

		s.classes = new Vector<String>();
		s.classes.add("CELL");

		s.classColors = new Hashtable<String, Color>();
		s.classColors.put("CELL", Color.BLACK);

		s.states = new Vector<String>();
		s.states.add("ALIVE");
		s.states.add("INJURED");

		s.trans = new Vector<Trans>();

		Trans die = new Trans();
		die.from = new Vector<String>();
		die.from.add("ALIVE");
		die.to = "EMPTY";
		die.name = "DIE";

		Trans breed = new Trans();
		breed.from = new Vector<String>();
		breed.from.add("EMPTY");
		breed.to = "ALIVE";
		breed.name = "BIRTH";

		s.trans.add(die);
		s.trans.add(breed);

		Vector<Integer> prox = new Vector<Integer>(); //proximity
		prox.add(1);
		CondExpr LHS, RHS;

		TransRule birth = new TransRule("BIRTH");
		birth.type = breed;
		LHS = new CondExpr(CondExpr.PEER, "EMPTY", prox);
		RHS = new CondExpr(3);
		birth.cond = new Condition(LHS, RelopType.EQ, RHS);

		TransRule death = new TransRule("DEATH");
		death.type = die;
		LHS = new CondExpr(CondExpr.PEER, "ALIVE", prox);
		RHS = new CondExpr(2);
		death.cond = new Condition(LHS, RelopType.LT, RHS);

		TransRule crowded = new TransRule("OVERPOPULATION");
		crowded.type = die;
		LHS = new CondExpr(CondExpr.PEER, "ALIVE", prox);
		RHS = new CondExpr(3);
		crowded.cond = new Condition(LHS, RelopType.GT, RHS);


		s.transrule = new Vector<TransRule>();
		s.transrule.add(death);
		s.transrule.add(crowded);
		s.transrule.add(birth);

		s.simrules = new Vector<TransRule>();
		s.simrules.add(birth);
		s.simrules.add(death);
		s.simrules.add(crowded);

		Vector<Float> popArgs;
		Vector<Float> popDot1;
		Vector<Float> popDot2;
		s.populate = new Vector<Populate>();

		//GLIDER 1
		popArgs = new Vector<Float>();
		popArgs.add(new Float(17));
		popArgs.add(new Float(17));
		popArgs.add(new Float(19));
		popArgs.add(new Float(17));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));

		popDot1 = new Vector<Float>();
		popDot1.add(new Float(17));
		popDot1.add(new Float(18));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));

		popDot2 = new Vector<Float>();
		popDot2.add(new Float(18));
		popDot2.add(new Float(19));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot2));

		//BLINKER
		popArgs = new Vector<Float>();
		popArgs.add(new Float(15));
		popArgs.add(new Float(5));
		popArgs.add(new Float(17));
		popArgs.add(new Float(5));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));

		//TOAD
		popArgs = new Vector<Float>();
		popArgs.add(new Float(5));
		popArgs.add(new Float(15));
		popArgs.add(new Float(7));
		popArgs.add(new Float(15));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(4));
		popArgs.add(new Float(16));
		popArgs.add(new Float(6));
		popArgs.add(new Float(16));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));

		//LIGHT WEIGHT SPACESHIP
		popArgs = new Vector<Float>();
		popArgs.add(new Float(25));
		popArgs.add(new Float(25));
		popArgs.add(new Float(25));
		popArgs.add(new Float(27));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(26));
		popArgs.add(new Float(27));
		popArgs.add(new Float(28));
		popArgs.add(new Float(27));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(26));
		popDot1.add(new Float(24));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(29));
		popDot1.add(new Float(24));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(29));
		popDot1.add(new Float(26));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));

		//Breeder
		popArgs = new Vector<Float>();
		popArgs.add(new Float(30));
		popArgs.add(new Float(20));
		popArgs.add(new Float(31));
		popArgs.add(new Float(21));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(41));
		popDot1.add(new Float(19));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(40));
		popArgs.add(new Float(20));
		popArgs.add(new Float(40));
		popArgs.add(new Float(22));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(41));
		popDot1.add(new Float(23));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(42));
		popArgs.add(new Float(18));
		popArgs.add(new Float(43));
		popArgs.add(new Float(18));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(42));
		popArgs.add(new Float(24));
		popArgs.add(new Float(43));
		popArgs.add(new Float(24));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(44));
		popDot1.add(new Float(21));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(45));
		popDot1.add(new Float(19));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(45));
		popDot1.add(new Float(23));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(46));
		popArgs.add(new Float(20));
		popArgs.add(new Float(46));
		popArgs.add(new Float(22));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(47));
		popDot1.add(new Float(21));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(50));
		popArgs.add(new Float(18));
		popArgs.add(new Float(51));
		popArgs.add(new Float(20));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(52));
		popDot1.add(new Float(17));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(52));
		popDot1.add(new Float(21));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(54));
		popArgs.add(new Float(16));
		popArgs.add(new Float(54));
		popArgs.add(new Float(17));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(54));
		popArgs.add(new Float(21));
		popArgs.add(new Float(54));
		popArgs.add(new Float(22));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(64));
		popArgs.add(new Float(18));
		popArgs.add(new Float(65));
		popArgs.add(new Float(19));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));

		GUI gui = new GUI();
		gui.run();
	}
}
