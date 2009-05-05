/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package columbia.exgol.simulation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author sikarwar
 */
public class GUI {

	JFrame f;
	EXGOLPanel p;
	public static int SCALE = 5;
    JButton next;

	public GUI() {
		f = new JFrame("EXGOL");
        f.setLayout(new BorderLayout());
        p = new EXGOLPanel(new Logic());

        next = new JButton("NEXT");
        next.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                p.nextGen();
            }
        });

		f.add(next, BorderLayout.NORTH);
		f.add(p, BorderLayout.CENTER);
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
