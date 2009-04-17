/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package columbia.exgol.intermediate;

import java.util.Vector;

/**
 *
 * @author sikarwar
 */
public class Populate {
	public String className;
	public String stateName;
	public PopulateType populateType;
	public Vector<Float> populateArgs;

	public Populate(String className, String stateName, PopulateType type, Vector<Float> initArgs) {
		this.className = className;
		this.stateName = stateName;
		this.populateType = type;
		populateArgs = initArgs;
	}
}
