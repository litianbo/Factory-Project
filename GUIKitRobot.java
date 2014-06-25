import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;

@SuppressWarnings("serial")
public class GUIKitRobot implements GUIItem, Serializable {
	/** speed of kit robot in pixels per second */
	public static final double SPEED = 200;
	/** length of robot arm */
	public static final double ARM_LENGTH = 180;

	/** used to access kit robot data */
	public KitRobot kitRobot;
	/** non-moving base position of arm */
	private Point2D.Double basePos;
	/** (linear) movement of arm gripper */
	public Movement movement;

	/** Initialization */
	public GUIKitRobot(KitRobot kitRobot, Point2D.Double basePos) {
		this.kitRobot = kitRobot;
		this.basePos = basePos;
		movement = new Movement(new Point2D.Double(basePos.x, basePos.y - ARM_LENGTH),
				0);
	}

	/** draws the kitrobot's actions using Painter and movement */
	public void draw(Graphics2D g, long currentTime) {
		// do position calculations
		Point2D.Double handPos = movement.calcPos(currentTime);
		double theta = Math.atan2(handPos.y - basePos.y, handPos.x - basePos.x);
		Movement armMove = new Movement(basePos, theta + Math.PI / 2);
		Movement handMove = new Movement(handPos, theta + Math.PI / 2);

		// draw images
		// all images rotated about center, last parameter to draw() says
		// whether movement pos is center or corner of image (true means center)
		Painter.draw(g, Painter.ImageEnum.ROBOT_RAIL, currentTime,
				new Movement(basePos, 0), true);
		Painter.draw(g, Painter.ImageEnum.ROBOT_BASE, currentTime,
				new Movement(basePos, 0), true);
		Painter.draw(g, Painter.ImageEnum.KIT_ROBOT_HAND, currentTime,
				handMove, true);
		Painter.draw(g, Painter.ImageEnum.ROBOT_ARM_1, currentTime,
				armMove, true);

		if (kitRobot.getKit() != null) {
			new GUIKit(kitRobot.getKit(), handMove).draw(g, currentTime);
		}
	}

	/** returns whether arrived */
	public boolean arrived(long currentTime) {
		return (movement.arrived(currentTime));
	}

	/** make the robot stop at the base */
	public void park(long currentTime) {
		movement = movement.moveToAtSpeed(currentTime, new Point2D.Double(
				basePos.x, basePos.y - ARM_LENGTH), 0, SPEED);
	}

	/** returns target within reachable distance (same algorithm as in GUIPartRobot.java) */
	public Point2D.Double fixTarget(Point2D.Double target) {
		double dist = Math.sqrt(Math.pow(target.x - basePos.x, 2) + Math.pow(target.y - basePos.y, 2));
		if (dist <= ARM_LENGTH) return target; // target is within reach
		// target is too far away, scale to arm length
		return new Point2D.Double(basePos.x + (target.x - basePos.x) * ARM_LENGTH / dist, basePos.y + (target.y - basePos.y) * ARM_LENGTH / dist);
	}

	/** getter for basePos */
	public Point2D.Double getBasePos() {
		return basePos;
	}

	/** setter for movement */
	public void setMove(Movement movement) {
		this.movement = movement;
	}

	/** getter for movement */
	public Movement getMove() {
		return movement;
	}
}
