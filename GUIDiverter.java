import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
/** Contains data and methods for drawing and animating a diverter */
public class GUIDiverter implements Serializable {
	/** used to access the movement data */
	public Movement movement;
	/** initialize variables */
	public GUIDiverter( double x, double y )
	{
		movement = new Movement(new Point2D.Double(x,y), 0);
	}
	/** draws the diverter */
	public void draw( Graphics2D g, long currentTime ){
		Painter.draw(g, Painter.ImageEnum.DIVERTER, currentTime, movement, true);
	}
}
