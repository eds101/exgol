/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import columbia.exgol.*;
import columbia.exgol.CondExpr;
import columbia.exgol.simulation.GUI;
import java.awt.Color;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author sikarwar
 */
public class TestMain {

		public void test1(Simulation s) {

		}

		public static void main(String args[]) {
			Simulation s = new Simulation();
			s.generations = 0;

			s.gridsize = new Vector<Integer>();
			s.gridsize.add(20);
			s.gridsize.add(20);

			s.gridtype = GridType.BOUNDED;

			s.classes = new Hashtable<String, Color>();
			s.classes.put("CELL", Color.BLACK);

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

			TransRule birth  = new TransRule("BIRTH");
			birth.type = breed;
			Vector<Integer> prox = new Vector<Integer>();
			prox.add(1);
			birth.cond.LHS = new CondExpr("peer", "EMPTY", prox);
			birth.cond.op = "=";
			birth.cond.RHS = new CondExpr(3);

			TransRule death  = new TransRule("DEATH");
			death.type = die;
			death.cond.LHS = new CondExpr("peer", "ALIVE", prox);
			death.cond.op = "<";
			death.cond.RHS = new CondExpr(2);

			TransRule crowded  = new TransRule("OVERPOPULATION");
			crowded.type = die;
			crowded.cond.LHS = new CondExpr("peer", "ALIVE", prox);
			crowded.cond.op = ">";
			crowded.cond.RHS = new CondExpr(3);

			s.transrule = new Vector<TransRule>();
			s.transrule.add(birth);
			s.transrule.add(death);
			s.transrule.add(crowded);

			s.simrules = new Vector<TransRule>();
			s.simrules.add(birth);
			s.simrules.add(death);
			s.simrules.add(crowded);
			
			Vector<Float> popArgs = new Vector<Float>();
			popArgs.add(new Float(5));
			popArgs.add(new Float(5));
			popArgs.add(new Float(7));
			popArgs.add(new Float(5));

			/*Vector<Float> popArgs2 = new Vector<Float>();
			popArgs2.add(new Float(0));
			popArgs2.add(new Float(0));
			popArgs2.add(new Float(5));
			popArgs2.add(new Float(5));
			Populate rect2 = new Populate("CELL", "INJURED", PopulateType.RECTANGLE, popArgs2);
			s.populate.add(rect2);*/

			Vector<Float> popDot1 = new Vector<Float>();
			popDot1.add(new Float(5));
			popDot1.add(new Float(6));

			Vector<Float> popDot2 = new Vector<Float>();
			popDot2.add(new Float(6));
			popDot2.add(new Float(7));


			s.populate = new Vector<Populate>();
			s.populate.add(new Populate("CELL", "ALIVE", PopulateType.RECTANGLE, popArgs));
			s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot1));
			s.populate.add(new Populate("CELL", "ALIVE", PopulateType.DOT, popDot2));
			GUI gui = new GUI(s);
			gui.run();
		}
}
