import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUIKitDeliveryStation implements GUIItem, Serializable {
	/** used to access delivery station data */
	public KitDeliveryStation kitDeliveryStation;
	/** used to access movement data */
	public Movement movement;
	/** conveyor lanes */
	public GUILane inConveyor, outConveyor;
	/** whether delivery station is broken */
	public boolean broken;

	/** Constructor */
	public GUIKitDeliveryStation(KitDeliveryStation kitDeliveryStation,
			GUILane inConveyor, GUILane outConveyor, double x, double y) {
		this.kitDeliveryStation = kitDeliveryStation;
		this.inConveyor = inConveyor;
		this.outConveyor = outConveyor;
		this.movement = new Movement(new Point2D.Double(x, y), 0);
		this.broken = false;
	}

	/** Constructor */
	public GUIKitDeliveryStation(KitDeliveryStation kitDeliveryStation,
			GUILane inConveyor, GUILane outConveyor, Movement movement) {
		this.kitDeliveryStation = kitDeliveryStation;
		this.inConveyor = inConveyor;
		this.outConveyor = outConveyor;
		this.movement = movement;
	}

	/** draws the kit delivery station */
	public void draw(Graphics2D g, long currentTime) {
		inConveyor.draw(g, currentTime);
		outConveyor.draw(g, currentTime);
	}

	/** always turn on conveyor */
	public void turnOn(long currentTime) {
		inConveyor.turnOn(currentTime);
		outConveyor.turnOn(currentTime);
	}

	/**
	 * if this conveyor is full, turn off the lane, if this conveyor is empty,
	 * turn this lane on and move all of the pallets down one space;
	 * returns whether anything changed
	 */
	public boolean checkStatus(long currentTime) {
		boolean ret = false;
		if (inConveyor.shouldReset(currentTime)) {
			inConveyor.reset(currentTime);
			ret = true;
		}
		if (outConveyor.shouldReset(currentTime)) {
			outConveyor.reset(currentTime);
			ret = true;
		}
		if (inConveyor.hasEmptyPalletAtEnd(currentTime)) {
			inConveyor.removeItem(inConveyor.endItem(0), currentTime);
			inConveyor.turnOn(currentTime);
			ret = true;
		}
		if (inConveyor.hasFullPalletAtEnd(currentTime)) {
			inConveyor.turnOff(currentTime);
			ret = true;
		}
		return ret;
	}

	/** return outConveyor's location */
	public Point2D.Double getOutConveyorLocation() {
		return new Point2D.Double(outConveyor.getPos().x
				+ outConveyor.getLength() - 50, outConveyor.getPos().y + 60);
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
