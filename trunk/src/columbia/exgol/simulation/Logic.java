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
import java.util.Vector;

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
        } else {
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
                    Vector<String> classes = tr.checkCondition(oldCells, x, y, s.gridtype);
                    if (classes.size() > 0) {
                        newCells[x][y].state = tr.type.to;
                        if (tr.type.to.equals("EMPTY")) {
                            newCells[x][y].className = "EMPTY";
                        } else {
                            if (classes.size() == 1) {
                                newCells[x][y].className = classes.get(0);
                            } 
                            else if(tr.resolve.size() < 1){
                                newCells[x][y].className =
                                       classes.get((int)(classes.size()*Math.random()));
                            }
                            else{
                                for (String r : tr.resolve) {
                                    if (classes.contains(r)) {
                                        newCells[x][y].className = r;
                                        break;
                                    }
                                }
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

    void fillCircle(int index) {
        String cell = s.populate.get(index).className;
        String state = s.populate.get(index).stateName;
        int x1, y1, r;
        x1 = s.populate.get(index).populateArgs.get(0).intValue();
        y1 = s.populate.get(index).populateArgs.get(1).intValue();
        r = s.populate.get(index).populateArgs.get(2).intValue();
        int x, y;
        for (x = x1 - r; x <= x1; x++) {
            y = y1 + r - x1 + x;
            evenCells[x][y].className = s.populate.get(index).className;
            evenCells[x][y].state = s.populate.get(index).stateName;
            y = y1 - (r - x1 + x);
            evenCells[x][y].className = s.populate.get(index).className;
            evenCells[x][y].state = s.populate.get(index).stateName;
        }
        for (x = x1 + 1; x <= x1 + r; x++) {
            y = y1 + r - x + x1;
            evenCells[x][y].className = s.populate.get(index).className;
            evenCells[x][y].state = s.populate.get(index).stateName;
            y = y1 - (r - x + x1);
            evenCells[x][y].className = s.populate.get(index).className;
            evenCells[x][y].state = s.populate.get(index).stateName;
        }
    }

    void fillBlinker(int index) {
        int x = s.populate.get(index).populateArgs.get(0).intValue();
        int y = s.populate.get(index).populateArgs.get(1).intValue();
        int align = s.populate.get(index).populateArgs.get(2).intValue();

        evenCells[x][y].className = s.populate.get(index).className;
        evenCells[x][y].state = s.populate.get(index).stateName;
        if (align == Blinker.HORIZONTAL) {
            System.out.println("horiz");
            evenCells[x + 1][y].className = s.populate.get(index).className;
            evenCells[x + 1][y].state = s.populate.get(index).stateName;
            evenCells[x + 2][y].className = s.populate.get(index).className;
            evenCells[x + 2][y].state = s.populate.get(index).stateName;
        }
        else {
            System.out.println("vert");
            evenCells[x][y + 1].className = s.populate.get(index).className;
            evenCells[x][y + 1].state = s.populate.get(index).stateName;
            evenCells[x][y + 2].className = s.populate.get(index).className;
            evenCells[x][y + 2].state = s.populate.get(index).stateName;
        }
    }

    void fillGlider(int index) {
        System.out.println("fill glider");

        int x = s.populate.get(index).populateArgs.get(0).intValue();
        int y = s.populate.get(index).populateArgs.get(1).intValue();
        int dir = s.populate.get(index).populateArgs.get(2).intValue();

        if (dir == Glider.NW) {
            evenCells[x][y].className = s.populate.get(index).className;
            evenCells[x][y].state = s.populate.get(index).stateName;

            evenCells[x+1][y].className = s.populate.get(index).className;
            evenCells[x+1][y].state = s.populate.get(index).stateName;

            evenCells[x+2][y].className = s.populate.get(index).className;
            evenCells[x+2][y].state = s.populate.get(index).stateName;

            evenCells[x][y+1].className = s.populate.get(index).className;
            evenCells[x][y+1].state = s.populate.get(index).stateName;
            
            evenCells[x + 1][y+2].className = s.populate.get(index).className;
            evenCells[x + 1][y+2].state = s.populate.get(index).stateName;
        }
        else if (dir == Glider.SW) {
            evenCells[x + 1][y].className = s.populate.get(index).className;
            evenCells[x + 1][y].state = s.populate.get(index).stateName;

            evenCells[x][y+1].className = s.populate.get(index).className;
            evenCells[x][y+1].state = s.populate.get(index).stateName;

            evenCells[x][y+2].className = s.populate.get(index).className;
            evenCells[x][y+2].state = s.populate.get(index).stateName;

            evenCells[x + 1][y+2].className = s.populate.get(index).className;
            evenCells[x + 1][y+2].state = s.populate.get(index).stateName;

            evenCells[x + 2][y+2].className = s.populate.get(index).className;
            evenCells[x + 2][y+2].state = s.populate.get(index).stateName;
        }
        else if (dir == Glider.NE) {
            evenCells[x][y].className = s.populate.get(index).className;
            evenCells[x][y].state = s.populate.get(index).stateName;

            evenCells[x+1][y].className = s.populate.get(index).className;
            evenCells[x+1][y].state = s.populate.get(index).stateName;

            evenCells[x+2][y].className = s.populate.get(index).className;
            evenCells[x+2][y].state = s.populate.get(index).stateName;

            evenCells[x+2][y+1].className = s.populate.get(index).className;
            evenCells[x+2][y+1].state = s.populate.get(index).stateName;

            evenCells[x + 1][y+2].className = s.populate.get(index).className;
            evenCells[x + 1][y+2].state = s.populate.get(index).stateName;
        }
        else if (dir == Glider.SE) {
            evenCells[x][y].className = s.populate.get(index).className;
            evenCells[x][y].state = s.populate.get(index).stateName;
            evenCells[x][y+1].className = s.populate.get(index).className;
            evenCells[x][y+1].state = s.populate.get(index).stateName;
            evenCells[x][y+2].className = s.populate.get(index).className;
            evenCells[x][y+2].state = s.populate.get(index).stateName;
            evenCells[x-1][y+2].className = s.populate.get(index).className;
            evenCells[x-1][y+2].state = s.populate.get(index).stateName;
            evenCells[x-2][y+1].className = s.populate.get(index).className;
            evenCells[x-2][y+1].state = s.populate.get(index).stateName;
        }
    }

    //TODO: stumped at first try
	/*
    void fillLine(int index) {
    int x1,y1,x2,y2;
    x1 = s.populate.get(index).populateArgs.get(0).intValue();
    y1 = s.populate.get(index).populateArgs.get(1).intValue();
    x2 = s.populate.get(index).populateArgs.get(2).intValue();
    y2 = s.populate.get(index).populateArgs.get(3).intValue();

    for(int i =0;i<

    evenCells[x][y].className = s.populate.get(index).className;
    evenCells[x][y].state = s.populate.get(index).stateName;
    }*/
    /*void fillUniform(int index) {
    int x,y,d;
    x = s.populate.get(index).populateArgs.get(0).intValue();
    y = s.populate.get(index).populateArgs.get(1).intValue();
    //P SHOULD BE BETWEEN ZERO AND ONE
    p = s.populate.get(index).populateArgs.get(1).doubleValue();

    for(int i =0;i<

    evenCells[x][y].className = s.populate.get(index).className;
    evenCells[x][y].state = s.populate.get(index).stateName;
    }*/
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
                case GLIDER:
                    fillGlider(i);
                    break;
                case BLINKER:
                    fillBlinker(i);
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
