import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
/** Draws the diverter arm */
public class GUIDiverterArm implements GUIItem, Serializable {
	/** movement rotation when diverter arm is up */
	public static double TOP = 0.7;
	/** movement rotation when diverter arm is down */
	public static double BOTTOM = -0.7;
	/** rotational speed */
	public static double SPEED = (TOP - BOTTOM) / 0.5;

	/** used to access the Movement class */
	public Movement movement;
	/** initialize variables */
	public GUIDiverterArm( double x, double y )
	{
		movement = new Movement(new Point2D.Double(x,y), TOP);
	}
	/** draws the diverter arm */
	public void draw( Graphics2D g, long currentTime )
	{
		// Draw the diverter below the diverter arm
		(new GUIDiverter(movement.calcPos(currentTime).x+42, 
		                 movement.calcPos(currentTime).y)).draw(g, currentTime);
		// draw the diverter arm
		Painter.draw(g, Painter.ImageEnum.DIVERTER_ARM, currentTime, movement, true);
	}

	/** returns movement that moves diverter arm to specified rotation */
	public Movement calcMove(long time, double newRot) {
		return movement.moveToAtAngularSpeed(time, movement.getStartPos(), newRot, SPEED);
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
