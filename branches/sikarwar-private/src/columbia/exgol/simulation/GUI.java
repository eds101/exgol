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
	public static final int SCALE = 6;
    public static final long SPEED = 200;
    JButton next;
    JButton run;
    JButton pause;
    JToggleButton grid;

	public GUI() {
        next = new JButton("Next");
        next.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                p.nextGen();
            }
        });

        run = new JButton("Run");
        run.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable(){

                    public void run() {
                        next.setEnabled(false);
                        run.setEnabled(false);
                        pause.setEnabled(true);
                    }

                });
                p.run();
            }
        });

        pause = new JButton("Pause");
        pause.setEnabled(false);
        pause.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable(){

                    public void run() {
                        next.setEnabled(true);
                        run.setEnabled(true);
                        pause.setEnabled(false);
                    }

                });
                p.pause();
            }
        });

        grid = new JToggleButton("Grid");
        grid.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                if (grid.isSelected())
                    f.getGlassPane().setVisible(true);
                else
                    f.getGlassPane().setVisible(false);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(next);
        buttonPanel.add(run);
        buttonPanel.add(pause);
        buttonPanel.add(grid);

        p = new EXGOLPanel(new Logic());
        p.start();

        f = new JFrame("EXGOL");
        f.setLayout(new BorderLayout());
        f.add(buttonPanel, BorderLayout.NORTH);
		f.add(p, BorderLayout.CENTER);
        f.pack();
		f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setGlassPane(new GridGlassPane(p.getHeight()));
	}

	public void run() {
        f.setVisible(true);
	}
}
