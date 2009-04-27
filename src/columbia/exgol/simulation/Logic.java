/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package columbia.exgol.simulation;

import columbia.exgol.intermediate.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Hashtable;

/**
 *
 * @author sikarwar
 */
public class Logic {

	Simulation s;
	int iteration;
	Cell oddCells[][];
	Cell evenCells[][];

	Cell[][] getNextGen() {
		Cell[][] oldCells, newCells;

		iteration++;
		if (iteration % 2 == 0) {
			oldCells = evenCells;
			newCells = oddCells;
		}
		else {
			oldCells = oddCells;
			newCells = evenCells;
		}

		// for each cell evaluate all conditions
		for (int x = 0; x < s.gridsize.get(0); x++) {
			for (int y = 0; y < s.gridsize.get(1); y++) {
				newCells[x][y].className = oldCells[x][y].className;
				newCells[x][y].state = oldCells[x][y].state;
				for (int c = 0; c < s.transrule.size(); c++) {
					//TODO: should we be looping over sim rules instead?
					TransRule tr = s.transrule.get(c);
					if (!tr.appliesToClass(oldCells[x][y].className)) {
						continue;
					}
					if (!tr.appliesToState(oldCells[x][y].state)) {
						continue;
					}
					if (tr.checkCondition(oldCells, x, y, s.gridtype)) {
						newCells[x][y].state = tr.type.to;
						if (tr.type.to.equals("EMPTY")) {
							newCells[x][y].className = "EMPTY";
						}
						else {
							//TODO: figure out the correct way
							if (tr.classes.size() == 0) {
								newCells[x][y].className = s.classes.get(0);
							}
							else {
								newCells[x][y].className = tr.classes.get(0);
							}
						}
						break; //rule applied - go to next cell
					}
				}
			}
		}
		return newCells;
	}

	void fillRect(int index) {
		String cell = s.populate.get(index).className;
		String state = s.populate.get(index).stateName;
		int x1, x2, y1, y2;
		x1 = s.populate.get(index).populateArgs.get(0).intValue();
		y1 = s.populate.get(index).populateArgs.get(1).intValue();
		x2 = s.populate.get(index).populateArgs.get(2).intValue();
		y2 = s.populate.get(index).populateArgs.get(3).intValue();
		int x, y;

		for (x = x1; x <= x2; x++) {
			for (y = y1; y <= y2; y++) {
				evenCells[x][y].className = cell;
				evenCells[x][y].state = state;
			}
		}
	}

	void fillDot(int index) {
		int x, y;
		x = s.populate.get(index).populateArgs.get(0).intValue();
		y = s.populate.get(index).populateArgs.get(1).intValue();

		evenCells[x][y].className = s.populate.get(index).className;
		evenCells[x][y].state = s.populate.get(index).stateName;
	}

	Hashtable<String, Color> initColorMaps() {
		s.classColors.put("EMPTY", Color.BLACK);
		return s.classColors;
	}

	Hashtable<String, AlphaComposite> initCompositeMap() {
		Hashtable<String, AlphaComposite> stateComposites = new Hashtable<String, AlphaComposite>();
		float trans;

		stateComposites.put("EMPTY", AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
		for (int i = 0; i < s.states.size(); i++) {
			trans = (float) (s.states.size() - i) / s.states.size();
			stateComposites.put(s.states.get(i), AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
		}
		return stateComposites;
	}

	public Cell[][] populate() {
		int x, y;
		switch (s.gridsize.size()) {
			case 1: //TODO
				break;

			case 2: //2-D
				evenCells = new Cell[s.gridsize.get(0)][s.gridsize.get(1)];
				oddCells = new Cell[s.gridsize.get(0)][s.gridsize.get(1)];
				for (x = 0; x < s.gridsize.get(0); x++) {
					for (y = 0; y < s.gridsize.get(1); y++) {
						evenCells[x][y] = new Cell();
						oddCells[x][y] = new Cell();
					}
				}
				break;

			case 3: //TODO
				break;
		}

		for (int i = 0; i < s.populate.size(); i++) {
			switch (s.populate.get(i).populateType) {
				//TODO: implement all populate statements
				case RECTANGLE:
					fillRect(i);
					break;
				case DOT:
					fillDot(i);
					break;
			}
		}
		return evenCells;
	}

	public Logic() {
		s = Simulation.getSimulation();
		iteration = -1;
	}

	public Dimension getDimension() {
		return new Dimension(s.gridsize.get(0) * GUI.SCALE, s.gridsize.get(1) * GUI.SCALE);
	}
}
