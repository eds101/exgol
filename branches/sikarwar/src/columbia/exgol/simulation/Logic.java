/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package columbia.exgol.simulation;

import columbia.exgol.*;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Hashtable;
import java.util.Map;

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
		computeNextGen();
		if (iteration % 2 == 0)
			return evenCells;
		else
			return oddCells;
	}

	void computeNextGen() {
		Cell[][] oldCells, newCells;

        iteration++;
		if (iteration == 0) return;
        if (iteration % 2 == 0){
            oldCells = oddCells;
            newCells = oddCells;
        }
        else{
            oldCells = evenCells;
            newCells = evenCells;
        }
        // for each cell evaluate all conditions
		for (int y = 0; y < newCells[0].length; y++) {
			for (int x = 0; x < newCells.length; x++) {
                for(int c = 0;c < s.transrule.size();c++){
                    TransRule tr = s.transrule.get(c);
                    // Check that the rule applies
                    if(!tr.appliesToClass(oldCells[x][y].className)) continue;
                    if(!tr.appliesToState(oldCells[x][y].state)) continue;
                    if(tr.checkCondition(oldCells, x, y, s.gridtype)){
                        newCells[x][y].state = tr.type.to;
                        if(newCells[x][y].state.equals("EMPTY"))
                            newCells[x][y].className = "EMPTY";
                    }
                }
			}
		}

        if (iteration % 2 == 0)
            evenCells = newCells;
        else
            oddCells = newCells;
	}

	void fillRect(int index) {
		String cell  = s.populate.get(index).className;
		String state = s.populate.get(index).stateName;
		int x1, x2, y1, y2;
		x1 = s.populate.get(index).populateArgs.get(0).intValue();
		y1 = s.populate.get(index).populateArgs.get(1).intValue();
		x2 = s.populate.get(index).populateArgs.get(2).intValue();
		y2 = s.populate.get(index).populateArgs.get(3).intValue();
		int x, y;

		for (x = x1; x <= x2; x++)
			for (y = y1; y <= y2; y++) {
				evenCells[x][y].className = cell;
				evenCells[x][y].state = state;
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
		s.classes.put("EMPTY", Color.BLACK);
		return s.classes;
	}

	Hashtable<String, AlphaComposite> initCompositeMap() {
		Hashtable<String, AlphaComposite> stateComposites = new Hashtable<String, AlphaComposite>();
		float trans;

		stateComposites.put("EMPTY", AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0));
		for(int i=0;i<s.states.size();i++){
			trans = (float)(s.states.size()-i) / s.states.size();
			stateComposites.put(s.states.get(i), AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
		}
		return stateComposites;
	}

	void populate() {
		int x, y, z;
		switch(s.gridsize.size()) {
				case 1: //TODO
					;
				case 2:
					evenCells = new Cell[s.gridsize.get(0)][s.gridsize.get(1)];
					oddCells = new Cell[s.gridsize.get(0)][s.gridsize.get(1)];
					for (y = 0; y < evenCells[0].length; y++) {
						for (x = 0; x < evenCells.length; x++) {
							evenCells[x][y] = new Cell();
							oddCells[x][y] = new Cell();
						}
					}
			case 3: //TODO
				;
		}

		for (int i = 0; i < s.populate.size(); i++){
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
	}

	public Logic(Simulation s) {
		this.s = s;
		iteration = -1;
		populate();
	}

	public Dimension getDimension() {
		return new Dimension(s.gridsize.get(0) * GUI.SCALE, s.gridsize.get(1) * GUI.SCALE);
	}
}
