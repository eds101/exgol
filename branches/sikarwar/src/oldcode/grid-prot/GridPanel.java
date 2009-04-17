import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class GridPanel extends JPanel{
    private int cell_size = 10;
    private int sizex, sizey;

    public void paintComponent(Graphics g) {
	super.paintComponent(g);    // paints background
	Graphics2D g2d = (Graphics2D)g;
	Line2D.Double line = new Line2D.Double();
	
	sizex = this.getSize().width;
	sizey = this.getSize().height;

	for(int x=0;x<=sizex;x+=cell_size){
	    line.setLine(x,0,x,sizey);
	    g2d.draw(line); 
	}
	
	for(int y=0;y<=sizey;y+=cell_size){
	    line.setLine(0,y,sizex,y);
	    g2d.draw(line);
	}
		
	g2d.draw(line);
    }
}