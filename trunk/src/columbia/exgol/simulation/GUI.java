/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package columbia.exgol.simulation;

import javax.swing.*;

/**
 *
 * @author sikarwar
 */
public class GUI {

	JFrame f;
	EXGOLPanel p;
	public static int SCALE = 5;

	public GUI() {
		f = new JFrame("EXGOL");
		p = new EXGOLPanel(new Logic());
		f.add(p);
	}

	public void run() {
		f.pack();
		f.setResizable(false);
		f.setLocation(200, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		p.start();
	}
}