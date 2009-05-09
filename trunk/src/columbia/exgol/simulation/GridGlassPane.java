/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package columbia.exgol.simulation;

import columbia.exgol.intermediate.Simulation;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 *
 * @author sikarwar
 */
class GridGlassPane extends JComponent{

    int panelHeight;
    Simulation s;
    public GridGlassPane(int panelHeight) {
        this.panelHeight = panelHeight;
        s = Simulation.getSimulation();
    }

    @Override
    public void paintComponent(Graphics g) {
        int x = s.gridsize.get(0) * GUI.SCALE;
        int y = s.gridsize.get(1) * GUI.SCALE;
        int i, from, to;

        g.setColor(Color.LIGHT_GRAY);
        to = getHeight();
        from = to - panelHeight;
        for (i = GUI.SCALE; i < x; i+= GUI.SCALE ) {
            g.drawLine(i, from, i, to);
        }
        to = getWidth();
        y += from; //offset for the button panel
        for (i = from; i < y; i+= GUI.SCALE) {
            g.drawLine(0, i, to, i);
        }
    }
}
