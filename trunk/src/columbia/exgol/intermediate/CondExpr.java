/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package columbia.exgol.intermediate;

import columbia.exgol.simulation.Cell;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author sikarwar
 */
public class CondExpr {

    public static String PEER = "peer";
    public static String ENEMY = "enemy";
    public static String NEIGHBOR = "neighbor";
    private boolean isNum;
    private int value;
    private String condClass; //i would name it class; but it's a keyword
    private String condState;
    private Vector<Integer> proximity; //for multi dimension

    public CondExpr(int value) {
        isNum = true;
        this.value = value;
    }

    public CondExpr(String condClass, String condState, Vector<Integer> prox) {
        isNum = false;
        this.condClass = condClass;
        this.condState = condState;
        this.proximity = prox;
    }

    void countCell(Cell c, Hashtable<String, Integer> neighbors){
        if (!c.className.equals("EMPTY")) {
            Integer cellCount = neighbors.get(c.className);
            if (cellCount == null) {
                cellCount = 0;
            }
            cellCount++;
            neighbors.put(c.className, cellCount);
        }
    }
    
    Hashtable<String, Integer> evaluate(Cell[][] cells, int x, int y, GridType gt) {
        Hashtable<String, Integer> neighbors = new Hashtable<String, Integer>();
        Hashtable<String, Integer> retVal = new Hashtable<String, Integer>();
        if (isNum) {
            retVal.put("EMPTY", value);
            return retVal;
        }

        int x1, y1, x2, y2;
        Simulation s = Simulation.getSimulation();
        int maxX = s.gridsize.get(0);
        int maxY = s.gridsize.get(1);

        Cell kernel = cells[x][y];
        if (!kernel.className.equals("EMPTY")) {
            neighbors.put(kernel.className, 0);
        }

        for (int p = 0; p < proximity.size(); p++) {
            int i = proximity.get(p);
            Cell c;

            x1 = x - i;
            y1 = y - i;		//top left
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY)
                this.countCell(cells[x1][y1],neighbors);

            x1 = x + i;
            y1 = y - i;		//top right
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY)
                this.countCell(cells[x1][y1],neighbors);

            x1 = x - i;
            y1 = y + i;		//bottom left
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY)
                this.countCell(cells[x1][y1],neighbors);

            x1 = x + i;
            y1 = y + i;		//bottom right
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY)
                this.countCell(cells[x1][y1],neighbors);

            y1 = y - i;
            y2 = y + i;
            for (x1 = x - i + 1; x1 < x + i; x1++)
            {
                try {
                    // Top -->
                    c = cells[x1][y1];
                    this.countCell(c,neighbors);
                } catch(ArrayIndexOutOfBoundsException e) {}

                try {
                    // Bottom -->
                    c = cells[x1][y2];
                    this.countCell(c,neighbors);
                } catch(ArrayIndexOutOfBoundsException e) {}

            }

            x1 = x + i;
            x2 = x - i;
            for (y1 = y - i + 1; y1 < y + i; y1++)
            {

                try {
                    //Right v
                    c = cells[x1][y1];
                    this.countCell(c,neighbors);
                } catch(ArrayIndexOutOfBoundsException e) {}

                try {
                    //Left v
                    c = cells[x2][y1];
                    this.countCell(c,neighbors);
                } catch(ArrayIndexOutOfBoundsException e) {}
            }
            // handle PEER
            if (condClass.equals(PEER)) {
                retVal = neighbors;
            }
            // handle ENEMY, count the number of neighbor
            // cells not being of the same class as the kernel
            else if (condClass.equals(ENEMY)) {
                for (String cl : neighbors.keySet()) {
                    int enemyCount = 0;
                    for (String tmp : neighbors.keySet()) {
                        if(!cl.equals(tmp))
                            enemyCount += neighbors.get(tmp);
                    }
                    retVal.put(cl, enemyCount);
                }
            }
            // handle NEIGHBOR
            else if (condClass.equals(NEIGHBOR)){
                int totCount = 0;
                for (String cl : neighbors.keySet()) {
                    totCount += neighbors.get(cl);
                }
                //everyone has the same number of neighbors
                for (String cl : neighbors.keySet()) {
                    retVal.put(cl, totCount);
                }
            }
            // handle direct class name
            else{
                retVal.put(kernel.className,
                        neighbors.get(kernel.className));
            }
        }
        return retVal;
    }
}