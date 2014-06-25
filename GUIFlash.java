import java.awt.*;
import java.awt.geom.Point2D;

public class GUIFlash {
	public Movement movement;
	
	public GUIFlash( double x, double y )
	{
		movement = new Movement(new Point2D.Double(x,y), 0);
	}
	
	public void draw( Graphics2D g, long currentTime ){
		Painter.draw(g, Painter.ImageEnum.CAMERA_FLASH, currentTime, movement, false);
	}
}
