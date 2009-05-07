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

    private boolean performEval(Cell kernel, Cell cell) {
        if(!condState.equals(cell.state) && !condState.equals(""))
            return false;
        if (condClass.equals(PEER)) {
            return cell.isPeer(kernel);
        } else if (condClass.equals(ENEMY)) {
            return cell.isEnemy(kernel);
        } else if (condClass.equals(NEIGHBOR)) {
            return cell.isNeighbor(kernel);
        } else {
            return condClass.equals(cell.className);
        }
    }

    Hashtable<String, Integer> evaluate(Cell[][] cells, int x, int y, GridType gt) {
        Hashtable<String, Integer> retVal = new Hashtable<String, Integer>();
        if (isNum) {
            retVal.put("EMPTY", value);
            return retVal;
        }

        int x1, y1;
        Simulation s = Simulation.getSimulation();
        int maxX = s.gridsize.get(0);
        int maxY = s.gridsize.get(1);
        Integer cellCount = 0;

        Cell kernel = cells[x][y];
        if (!kernel.className.equals("EMPTY")) {
            retVal.put(kernel.className, 0);
        }

        //if (condClass.equals(PEER)) {

        for (int p = 0; p < proximity.size(); p++) {
            int i = proximity.get(p);
            Cell c;

            x1 = x - i;
            y1 = y - i;		//top left
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
                c = cells[x1][y1];
                if (performEval(kernel, c)) {
                    cellCount = retVal.get(c.className);
                    if (cellCount == null) {
                        cellCount = 0;
                    }
                    cellCount++;
                    retVal.put(c.className, cellCount);
                }
            }

            x1 = x + i;
            y1 = y - i;		//top right
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
                c = cells[x1][y1];
                if (performEval(kernel, c)) {
                    cellCount = retVal.get(c.className);
                    if (cellCount == null) {
                        cellCount = 0;
                    }
                    cellCount++;
                    retVal.put(c.className, cellCount);
                }
            }

            x1 = x - i;
            y1 = y + i;		//bottom left
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
                c = cells[x1][y1];
                if (performEval(kernel, c)) {
                    cellCount = retVal.get(c.className);
                    if (cellCount == null) {
                        cellCount = 0;
                    }
                    cellCount++;
                    retVal.put(c.className, cellCount);
                }
            }

            x1 = x + i;
            y1 = y + i;		//bottom right
            if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
                c = cells[x1][y1];
                if (performEval(kernel, c)) {
                    cellCount = retVal.get(c.className);
                    if (cellCount == null) {
                        cellCount = 0;
                    }
                    cellCount++;
                    retVal.put(c.className, cellCount);
                }
            }

            //go clock wise from top left
            y1 = y - i;
            if (y1 >= 0 && y1 < maxY) {
                for (x1 = x - i + 1; x1 < x + i; x1++) //	--->
                {
                    if (x1 >= 0 && x1 < maxX) {
                        if (performEval(kernel, cells[x1][y1])) {
                            cellCount = retVal.get(cells[x1][y1].className);
                            if (cellCount == null) {
                                cellCount = 0;
                            }
                            cellCount++;
                            retVal.put(cells[x1][y1].className, cellCount);
                        }
                    }
                }
            }

            x1 = x + i;
            if (x1 >= 0 && x1 < maxX) {
                for (y1 = y - i + 1; y1 < y + i; y1++) //	|
                {										//	|
                    if (y1 >= 0 && y1 < maxY) //	v
                    {
                        if (performEval(kernel, cells[x1][y1])) {
                            cellCount = retVal.get(cells[x1][y1].className);
                            if (cellCount == null) {
                                cellCount = 0;
                            }
                            cellCount++;
                            retVal.put(cells[x1][y1].className, cellCount);
                        }
                    }
                }
            }

            y1 = y + i;
            if (y1 >= 0 && y1 < maxY) {
                for (x1 = x + i - 1; x1 > x - i; x1--) //	<---
                {
                    if (x1 >= 0 && x1 < maxX) {
                        if (performEval(kernel, cells[x1][y1])) {
                            cellCount = retVal.get(cells[x1][y1].className);
                            if (cellCount == null) {
                                cellCount = 0;
                            }
                            cellCount++;
                            retVal.put(cells[x1][y1].className, cellCount);
                        }
                    }
                }
            }

            x1 = x - i;
            if (x1 >= 0 && x1 < maxX) {
                for (y1 = y + i - 1; y1 > y - i; y1--) //	^
                {										//	|
                    if (y1 >= 0 && y1 < maxY) //	|
                    {
                        if (performEval(kernel, cells[x1][y1])) {
                            cellCount = retVal.get(cells[x1][y1].className);
                            if (cellCount == null) {
                                cellCount = 0;
                            }
                            cellCount++;
                            retVal.put(cells[x1][y1].className, cellCount);
                        }
                    }
                }
            }
        }

        return retVal;
    /*
    else if (condClass.equals(ENEMY)) {
    for (int p = 0; p < proximity.size(); p++) {
    int i = proximity.get(p);
    Cell c;

    x1 = x - i;
    y1 = y - i;		//top left
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isEnemy(kernel)) {
    cellCount++;
    }
    }

    x1 = x + i;
    y1 = y - i;		//top right
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isEnemy(kernel)) {
    cellCount++;
    }
    }

    x1 = x - i;
    y1 = y + i;		//bottom left
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isEnemy(kernel)) {
    cellCount++;
    }
    }

    x1 = x + i;
    y1 = y + i;		//bottom right
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isEnemy(kernel)) {
    cellCount++;
    }
    }

    //go clock wise from top left
    y1 = y - i;
    if (y1 >= 0 && y1 < maxY) {
    for (x1 = x - i + 1; x1 < x + i; x1++)	//	--->
    {
    if (x1 >= 0 && x1 < maxX) {
    if (cells[x1][y1].isEnemy(kernel)) {
    cellCount++;
    }
    }
    }
    }

    x1 = x + i;
    if (x1 >= 0 && x1 < maxX) {
    for (y1 = y - i + 1; y1 < y + i; y1++)	//	|
    {										//	|
    if (y1 >= 0 && y1 < maxY)			//	v
    {
    if (cells[x1][y1].isEnemy(kernel)) {
    cellCount++;
    }
    }
    }
    }

    y1 = y + i;
    if (y1 >= 0 && y1 < maxY) {
    for (x1 = x + i - 1; x1 > x - i; x1--)	//	<---
    {
    if (x1 >= 0 && x1 < maxX) {
    if (cells[x1][y1].isEnemy(kernel)) {
    cellCount++;
    }
    }
    }
    }

    x1 = x - i;
    if (x1 >= 0 && x1 < maxX) {
    for (y1 = y + i - 1; y1 > y - i; y1--)	//	^
    {										//	|
    if (y1 >= 0 && y1 < maxY)			//	|
    {
    if (cells[x1][y1].isEnemy(kernel)) {
    cellCount++;
    }
    }
    }
    }
    }
    }
    else if (condClass.equals(NEIGHBOR)) {
    for (int p = 0; p < proximity.size(); p++) {
    int i = proximity.get(p);
    Cell c;

    x1 = x - i;
    y1 = y - i;		//top left
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isNeighbor(kernel)) {
    cellCount++;
    }
    }

    x1 = x + i;
    y1 = y - i;		//top right
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isNeighbor(kernel)) {
    cellCount++;
    }
    }

    x1 = x - i;
    y1 = y + i;		//bottom left
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isNeighbor(kernel)) {
    cellCount++;
    }
    }

    x1 = x + i;
    y1 = y + i;		//bottom right
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.isNeighbor(kernel)) {
    cellCount++;
    }
    }

    //go clock wise from top left
    y1 = y - i;
    if (y1 >= 0 && y1 < maxY) {
    for (x1 = x - i + 1; x1 < x + i; x1++)	//	--->
    {
    if (x1 >= 0 && x1 < maxX) {
    if (cells[x1][y1].isNeighbor(kernel)) {
    cellCount++;
    }
    }
    }
    }

    x1 = x + i;
    if (x1 >= 0 && x1 < maxX) {
    for (y1 = y - i + 1; y1 < y + i; y1++)	//	|
    {										//	|
    if (y1 >= 0 && y1 < maxY)			//	v
    {
    if (cells[x1][y1].isNeighbor(kernel)) {
    cellCount++;
    }
    }
    }
    }

    y1 = y + i;
    if (y1 >= 0 && y1 < maxY) {
    for (x1 = x + i - 1; x1 > x - i; x1--)	//	<---
    {
    if (x1 >= 0 && x1 < maxX) {
    if (cells[x1][y1].isNeighbor(kernel)) {
    cellCount++;
    }
    }
    }
    }

    x1 = x - i;
    if (x1 >= 0 && x1 < maxX) {
    for (y1 = y + i - 1; y1 > y - i; y1--)	//	^
    {										//	|
    if (y1 >= 0 && y1 < maxY)			//	|
    {
    if (cells[x1][y1].isNeighbor(kernel)) {
    cellCount++;
    }
    }
    }
    }
    }
    }
    else {
    for (int p = 0; p < proximity.size(); p++) {
    int i = proximity.get(p);
    Cell c;

    x1 = x - i;
    y1 = y - i;		//top left
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.className==condClass) {
    cellCount++;
    }
    }

    x1 = x + i;
    y1 = y - i;		//top right
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.className==condClass) {
    cellCount++;
    }
    }

    x1 = x - i;
    y1 = y + i;		//bottom left
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.className==condClass) {
    cellCount++;
    }
    }

    x1 = x + i;
    y1 = y + i;		//bottom right
    if (x1 >= 0 && x1 < maxX && y1 >= 0 && y1 < maxY) {
    c = cells[x1][y1];
    if (c.className==condClass) {
    cellCount++;
    }
    }

    //go clock wise from top left
    y1 = y - i;
    if (y1 >= 0 && y1 < maxY) {
    for (x1 = x - i + 1; x1 < x + i; x1++)	//	--->
    {
    if (x1 >= 0 && x1 < maxX) {
    if (cells[x1][y1].className==condClass) {
    cellCount++;
    }
    }
    }
    }

    x1 = x + i;
    if (x1 >= 0 && x1 < maxX) {
    for (y1 = y - i + 1; y1 < y + i; y1++)	//	|
    {										//	|
    if (y1 >= 0 && y1 < maxY)			//	v
    {
    if (cells[x1][y1].className==condClass) {
    cellCount++;
    }
    }
    }
    }

    y1 = y + i;
    if (y1 >= 0 && y1 < maxY) {
    for (x1 = x + i - 1; x1 > x - i; x1--)	//	<---
    {
    if (x1 >= 0 && x1 < maxX) {
    if (cells[x1][y1].className==condClass) {
    cellCount++;
    }
    }
    }
    }

    x1 = x - i;
    if (x1 >= 0 && x1 < maxX) {
    for (y1 = y + i - 1; y1 > y - i; y1--)	//	^
    {										//	|
    if (y1 >= 0 && y1 < maxY)			//	|
    {
    if (cells[x1][y1].className==condClass) {
    cellCount++;
    }
    }
    }
    }
    }
    }
    return cellCount;
    }
     * */
    }
}