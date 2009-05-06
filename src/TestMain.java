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
		s.gridsize.add(100);

		s.gridtype = GridType.BOUNDED;

		s.classes = new Vector<String>();
		s.classes.add("CELL");
        s.classes.add("KNIGHT");

		s.classColors = new Hashtable<String, Color>();
		s.classColors.put("CELL", Color.BLACK);
        s.classColors.put("KNIGHT", Color.RED);

		s.states = new Vector<String>();
		s.states.add("ALIVE");
		s.states.add("INJURED");

		s.trans = new Vector<Trans>();

		Trans die = new Trans();
		die.from = new Vector<String>();
		die.from.add("ALIVE");
        die.from.add("INJURED");
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

        s.transrule = new Vector<TransRule>();
		TransRule birth = new TransRule("BIRTH");
		birth.type = breed;
        birth.resolve.add("KNIGHT");
		LHS = new CondExpr(CondExpr.PEER, "", prox);
		RHS = new CondExpr(3);
		birth.cond = new Condition(LHS, RelopType.EQ, RHS);
        s.transrule.add(birth);

		TransRule death = new TransRule("DEATH");
		death.type = die;
		LHS = new CondExpr(CondExpr.PEER, "", prox);
		RHS = new CondExpr(2);
		death.cond = new Condition(LHS, RelopType.LT, RHS);
        s.transrule.add(death);

//      TransRule fight = new TransRule("FIGHT");
//		fight.type = die;
//		LHS = new CondExpr(CondExpr.ENEMY, "ALIVE", prox);
//		RHS = new CondExpr(CondExpr.PEER, "ALIVE", prox);
//		fight.cond = new Condition(LHS, RelopType.GET, RHS);
//      s.transrule.add(fight);

		TransRule crowded = new TransRule("OVERPOPULATION");
		crowded.type = die;
		LHS = new CondExpr(CondExpr.PEER, "ALIVE", prox);
		RHS = new CondExpr(3);
		crowded.cond = new Condition(LHS, RelopType.GT, RHS);
		s.transrule.add(crowded);

		s.simrules = new Vector<TransRule>();
		s.simrules.add(birth);
		s.simrules.add(death);
		s.simrules.add(crowded);

		Vector<Float> popArgs;
		Vector<Float> popDot1;
		Vector<Float> popDot2;
		s.populate = new Vector<Populate>();

        int x, y;
        //2 DOTS
        x = 1;
        y = 1;


        //BLINKER below dots
        popArgs = new Vector<Float>();
		popArgs.add(new Float(x));
		popArgs.add(new Float(y));
		popArgs.add(new Float(x+1));
		popArgs.add(new Float(y+1));
		s.populate.add(new Populate("CELL", "INJURED", PopulateType.RECTANGLE, popArgs));

         popArgs = new Vector<Float>();
		popArgs.add(new Float(x+2));
		popArgs.add(new Float(y));
		popArgs.add(new Float(x+3));
		popArgs.add(new Float(y+1));
		s.populate.add(new Populate("KNIGHT", "ALIVE", PopulateType.RECTANGLE, popArgs));

		//GLIDER 1
        glider(s, "CELL", "ALIVE", 17, 17);
        glider(s, "KNIGHT", "INJURED", 40, 70);

        //BLINKER
		popArgs = new Vector<Float>();
		popArgs.add(new Float(15));
		popArgs.add(new Float(5));
		popArgs.add(new Float(17));
		popArgs.add(new Float(5));
		s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));

        //BLINKER
		popArgs = new Vector<Float>();
		popArgs.add(new Float(15));
		popArgs.add(new Float(7));
		popArgs.add(new Float(17));
		popArgs.add(new Float(7));
		s.populate.add(new Populate("KNIGHT", "ALIVE", PopulateType.RECTANGLE, popArgs));

		//TOAD
		popArgs = new Vector<Float>();
		popArgs.add(new Float(5));
		popArgs.add(new Float(15));
		popArgs.add(new Float(7));
		popArgs.add(new Float(15));
		s.populate.add(new Populate("KNIGHT", "ALIVE", PopulateType.RECTANGLE, popArgs));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(4));
		popArgs.add(new Float(16));
		popArgs.add(new Float(6));
		popArgs.add(new Float(16));
		s.populate.add(new Populate("KNIGHT", "ALIVE", PopulateType.RECTANGLE, popArgs));

		//LIGHT WEIGHT SPACESHIP
        
		popArgs = new Vector<Float>();
        x = 20;
        y = 20;
        String spaceship = "CELL";
		popArgs.add(new Float(x));
		popArgs.add(new Float(y));
		popArgs.add(new Float(x));
		popArgs.add(new Float(y+2));
		s.populate.add(new Populate(spaceship, "ALIVE", PopulateType.RECTANGLE, popArgs));
		popArgs = new Vector<Float>();
		popArgs.add(new Float(x+1));
		popArgs.add(new Float(y+2));
		popArgs.add(new Float(x+3));
		popArgs.add(new Float(y+2));
		s.populate.add(new Populate(spaceship, "ALIVE", PopulateType.RECTANGLE, popArgs));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(x+1));
		popDot1.add(new Float(y-1));
		s.populate.add(new Populate(spaceship, "ALIVE", PopulateType.DOT, popDot1));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(x+4));
		popDot1.add(new Float(y-1));
		s.populate.add(new Populate(spaceship, "ALIVE", PopulateType.DOT, popDot1));
		popDot1 = new Vector<Float>();
		popDot1.add(new Float(x+4));
		popDot1.add(new Float(y+1));
		s.populate.add(new Populate(spaceship, "ALIVE", PopulateType.DOT, popDot1));

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

    static void glider(Simulation s, String type, String state, int x, int y) {
        Vector<Float> popArgs;
		Vector<Float> popDot1;
		Vector<Float> popDot2;

        popArgs = new Vector<Float>();
		popArgs.add(new Float(x));
		popArgs.add(new Float(y));
		popArgs.add(new Float(x+2));
		popArgs.add(new Float(y));
		s.populate.add(new Populate(type, state, PopulateType.RECTANGLE, popArgs));

		popDot1 = new Vector<Float>();
		popDot1.add(new Float(x));
		popDot1.add(new Float(y+1));
		s.populate.add(new Populate(type, state, PopulateType.DOT, popDot1));

		popDot2 = new Vector<Float>();
		popDot2.add(new Float(x+1));
		popDot2.add(new Float(y+2));
		s.populate.add(new Populate(type, state, PopulateType.DOT, popDot2));
    }
}
