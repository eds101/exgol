package columbia.exgol.intermediate;

import java.util.Vector;

/**
 *
 * @author sikarwar
 */
public class Trans {

	public String name;
	public Vector<String> from;
	public String to;

	public Trans() {
		from = new Vector<String>();
	}

	public String getName() {
		return this.name;
	}
	
}
