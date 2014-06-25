import java.awt.geom.*;
import java.io.*;

/** represents the movement of an object that starts at a specified position and rotation,
    moves at constant velocity to a specified end position and rotation, then stops */
@SuppressWarnings("serial")
public class Movement implements Serializable {
	/** position at beginning of this move */
	private final Point2D.Double startPos;
	/** counterclockwise rotation in radians at beginning of this move */
	private final double startRot;
	/** time that this move starts, in milliseconds after the simulation started */
	private final long startTime;
	/** position at end of this move */
	private final Point2D.Double endPos;
	/** counterclockwise rotation in radians at end of this move */
	private final double endRot;
	/** time that this move ends, in milliseconds after the simulation started */
	private final long endTime;

	/** Basic constructor. For objects that have an initial position, but aren't moving yet. */
	public Movement(Point2D.Double pos, double rot)
	{
		this(pos, rot, -1, pos, rot, 0);
	}

	/** constructor that sets all instance variables to specified values */
	public Movement(Point2D.Double newStartPos, double newStartRot, long newStartTime,
			Point2D.Double newEndPos, double newEndRot, long newEndTime) {
		double tempStartRot = newStartRot;
		double tempEndRot = newEndRot;
		if (newEndTime <= newStartTime) {
			throw new IllegalArgumentException("end time (" + newEndTime + ") must be later than start time (" + newStartTime + ")");
		}
		// ensure endRot is between -Math.PI and Math.PI
		while (tempEndRot > Math.PI) tempEndRot -= Math.PI * 2;
		while (tempEndRot < -Math.PI) tempEndRot += Math.PI * 2;
		// never rotate more than 180 degrees
		while (tempStartRot > tempEndRot + Math.PI) tempStartRot -= Math.PI * 2;
		while (tempStartRot < tempEndRot - Math.PI) tempStartRot += Math.PI * 2;
		// initialize instance variables
		startPos = newStartPos;
		startRot = tempStartRot;
		startTime = newStartTime;
		endPos = newEndPos;
		endRot = tempEndRot;
		endTime = newEndTime;
	}

	/** returns position at specified time */
	public Point2D.Double calcPos(long time) {
		if (time <= startTime) {
			return startPos;
		}
		if (time >= endTime) {
			return endPos;
		}
		return new Point2D.Double(startPos.getX() + (endPos.getX() - startPos.getX()) / (endTime - startTime) * (time - startTime),
				startPos.getY() + (endPos.getY() - startPos.getY()) / (endTime - startTime) * (time - startTime));
	}

	/** returns rotation at specified time */
	public double calcRot(long time) {
		if (time <= startTime) {
			return startRot;
		}
		if (time >= endTime) {
			return endRot;
		}
		return startRot + (endRot - startRot) / (endTime - startTime) * (time - startTime);
	}

	/** returns whether specified time is past end time */
	public boolean arrived(long time) {
		return (time >= endTime);
	}

	/** getter for startPos */
	public Point2D.Double getStartPos() {
		return startPos;
	}

	/** getter for startRot */
	public double getStartRot() {
		return startRot;
	}

	/** getter for startTime */
	public double getStartTime() {
		return startTime;
	}

	/** getter for endPos */
	public Point2D.Double getEndPos() {
		return endPos;
	}

	/** getter for endRot */
	public double getEndRot() {
		return endRot;
	}

	/** getter for endTime */
	public long getEndTime() {
		return endTime;
	}

	/** returns Movement object frozen at current location */
	public Movement freeze(long time) {
		return new Movement(calcPos(time), calcRot(time));
	}

	/** returns Movement object starting at current location and moving to final location at a specified time */
	public Movement moveToAtTime(long time, Point2D.Double newEndPos, double newEndRot, long newEndTime) {
		return new Movement(calcPos(time), calcRot(time), time, newEndPos, newEndRot, newEndTime);
	}

	/** returns Movement object starting at current location and moving to final location at a specified speed */
	public Movement moveToAtSpeed(long time, Point2D.Double newEndPos, double newEndRot, double speed) {
		return Movement.fromSpeed(calcPos(time), calcRot(time), time, newEndPos, newEndRot, speed);
	}

	/** returns Movement object starting at current location and moving to final location at a specified angular speed */
	public Movement moveToAtAngularSpeed(long time, Point2D.Double newEndPos, double newEndRot, double speed) {
		return Movement.fromAngularSpeed(calcPos(time), calcRot(time), time, newEndPos, newEndRot, speed);
	}

	/** returns current Movement instance offset by specified translation, rotation, and time */
	public Movement offset(Point2D.Double trans, double rot, long time) {
		return new Movement(new Point2D.Double(startPos.x + trans.x, startPos.y + trans.y),
				startRot + rot, startTime + time,
				new Point2D.Double(endPos.x + trans.x, endPos.y + trans.y),
				endRot + rot, endTime + time);
	}

	/** returns current Movement instance offset by specified translation and rotation */
	public Movement offset(Point2D.Double trans, double rot) {
		return offset(trans, rot, 0);
	}

	/** alternate method to create Movement object that asks for speed (in position units per second) instead of end time */
	public static Movement fromSpeed(Point2D.Double newStartPos, double newStartRot, long newStartTime,
			Point2D.Double newEndPos, double newEndRot, double speed) 
	{
		if (speed < 0) {
			throw new IllegalArgumentException("speed (" + speed + ") may not be less than 0");
		}
		if (!newStartPos.equals(newEndPos) && speed > 0) {
			return new Movement(newStartPos, newStartRot, newStartTime, newEndPos, newEndRot,
					(long)(newStartTime + Math.sqrt(Math.pow(newEndPos.x - newStartPos.x, 2) + Math.pow(newEndPos.y - newStartPos.y, 2)) / speed * 1000.0));
		}
		else {
			return new Movement(newStartPos, newStartRot);
		}
	}

	/** alternate method to create Movement object that asks for angular speed (in radians per second) instead of end time */
	public static Movement fromAngularSpeed(Point2D.Double newStartPos, double newStartRot, long newStartTime,
			Point2D.Double newEndPos, double newEndRot, double speed) 
	{
		if (speed < 0) {
			throw new IllegalArgumentException("angular speed (" + speed + ") may not be less than 0");
		}
		if (newStartRot != newEndRot && speed > 0) {
			return new Movement(newStartPos, newStartRot, newStartTime, newEndPos, newEndRot,
					(long)(newStartTime + Math.abs(newEndRot - newStartRot) / speed * 1000.0));
		}
		else {
			return new Movement(newStartPos, newStartRot);
		}
	}
}
