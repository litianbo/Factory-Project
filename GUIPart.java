import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

/** Contains data and methods for drawing and animating a part */
public class GUIPart implements GUIItem, Serializable 
{
	/** reference to part instance */
	public Part part;
	/** movement of the part */
	public Movement movement;
	
	/** Constructor, use x and y to locate part */
	public GUIPart(Part part, double x, double y )
	{
		this.part = part;
		movement = new Movement(new Point2D.Double(x,y), 0);
	}
	/** Constructor (part can rotate) */
	public GUIPart(Part part, double x, double y, double rotation)
	{
		this.part = part;
		movement = new Movement(new Point2D.Double(x,y), rotation);
	}
	/** Constructor (with the given movement variable) */
	public GUIPart(Part part, Movement movement)
	{
		this.part = part;
		this.movement = movement;
	}

	/** draws the part */
	public void draw(Graphics2D g, long currentTime)
	{
		Painter.draw(g, part.getImage(), currentTime, movement, true);
	}

	/** setter for movement */
	public void setMove(Movement movement)
	{
		this.movement = movement;
	}

	/** getter for movement */
	public Movement getMove() {
		return movement;
	}
}
