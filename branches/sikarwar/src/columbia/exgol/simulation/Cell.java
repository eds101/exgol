/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package columbia.exgol.simulation;

/**
 *
 * @author sikarwar
 */
public class Cell {

	public String className;
	public String state;

	public Cell() {
		className = "EMPTY";
		state = "EMPTY";
	}

	public boolean isPeer(Cell c) {
		//if we are both empty, its not a match
		if (className.equals("EMPTY") && c.className.equals("EMPTY")) {
			return false;
		}
		//if he is empty, but i am not, a peer is found
		if (!className.equals("EMPTY") && c.className.equals("EMPTY")) {
			return true;
		}
		//else we both gotta be same class
		if (className.equals(c.className)) {
			return true;
		}
		else {
			return false;
		}
	}
}
