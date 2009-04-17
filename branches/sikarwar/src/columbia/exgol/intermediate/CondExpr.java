/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package columbia.exgol.intermediate;

import columbia.exgol.simulation.Cell;
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
	private String cellClass; //i would name it class; but it's a keyword
	private String state;
	private Vector<Integer> proximity; //for multi dimension

	public CondExpr(int value) {
		isNum = true;
		this.value = value;
	}

	public CondExpr(String cellClass, String state, Vector<Integer> prox) {
		isNum = false;
		this.cellClass = cellClass;
		this.state = state;
		this.proximity = prox;
	}

	int evaluate(Cell[][] cells, int x, int y, GridType gt) {
		int cellCount = 0;
		int x1, x2, y1, y2;

		if (isNum) {
			return value;
		}

		// TODO: handle other states than peers :-)
		for (int i = 0; i < proximity.size(); i++) {
			x1 = Math.max(0, x - proximity.get(i));
			y1 = Math.max(0, y - proximity.get(i));
			x2 = Math.min(cells.length, x + proximity.get(i));
			y2 = Math.min(cells[0].length, y + proximity.get(i));
			for (int xi = x1; xi <= x2; xi++) {
				if (cellClass.equals(PEER)) {
					if (cells[xi][y1].state.equals(cells[x][y].state)) {
						cellCount++;
					}
					if (cells[xi][y2].state.equals(cells[x][y].state)) {
						cellCount++;
					}
				}
			}
			for (int yi = y1 + 1; yi <= y2 - 1; yi++) {
				if (cellClass.equals(PEER)) {
					if (cells[x1][yi].state.equals(cells[x][y].state)) {
						cellCount++;
					}
					if (cells[x2][yi].state.equals(cells[x][y].state)) {
						cellCount++;
					}
				}
			}
		}
		return cellCount;
	}
}