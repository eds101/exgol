/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package columbia.exgol.simulation;

import columbia.exgol.intermediate.Simulation;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Hashtable;
import javax.swing.JPanel;

/**
 *
 * @author sikarwar
 */
public class EXGOLPanel extends JPanel {

    Logic l;
    Cell cells[][] = null;
    Hashtable<String, Color> classColors;
    Hashtable<String, AlphaComposite> stateComposites;
    Simulation s;
    boolean pauseFlag = false;
    final Object lock = new Object();
    final Object updateLock = new Object();

    public EXGOLPanel(Logic l) {
        super();
        s = Simulation.getSimulation();
        this.l = l;
        setPreferredSize(l.getDimension());
        this.stateComposites = new Hashtable<String, AlphaComposite>();
        classColors = l.initColorMaps();
        stateComposites = l.initCompositeMap();
    }

    public void nextGen() {
        synchronized (updateLock) {	//this method is not re-entrant
            Object temp = l.getNextGen();
            synchronized (lock) { //to avoid race with paintComponent
                cells = (Cell[][]) temp;
            }
            repaint();
        }
    }

    public void run() {
        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    synchronized (updateLock) {	//this method is not re-entrant
                        Object temp = l.getNextGen();
                        synchronized (lock) { //to avoid race with paintComponent
                            cells = (Cell[][]) temp;
                            if (pauseFlag) {
                                return;
                            }
                        }
                        repaint();
                    }
                    try {Thread.sleep(GUI.SPEED);}catch (Exception e) {}
                }
            }
        }).start();
    }

    public void pause() {
        pauseFlag = true;
    }

    public void start() {
        cells = l.populate();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                s.gridsize.get(0) * GUI.SCALE,
                s.gridsize.get(1) * GUI.SCALE);
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (cells == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        int x, y;
        synchronized (lock) {
            for (y = 0; y < cells[0].length; y++) {
                for (x = 0; x < cells.length; x++) {
                    g2d.setColor(classColors.get(cells[x][y].className));
                    g2d.setComposite(stateComposites.get(cells[x][y].state));
                    Rectangle r =
                            new Rectangle(x * GUI.SCALE, y * GUI.SCALE, GUI.SCALE, GUI.SCALE);
                    g2d.fill(r);
                }
            }
        }
    }
}
