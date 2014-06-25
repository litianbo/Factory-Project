import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.Serializable;
import java.util.TreeMap;


/** Contains data and methods for drawing and animating a kit stand */
@SuppressWarnings("serial")
public class GUIKitStand implements GUIItem, Serializable
{
	/** 0-1 are positions for incomplete kits, 2 is the inspection position */
	private TreeMap<Integer, GUIKit> kits;
	/** used to access movement data  */
	public Movement movement;

	/** Constructor  */
	public GUIKitStand()
	{
		this.movement = new Movement(new Point2D.Double(100, 300), 0);
		kits = new TreeMap<Integer, GUIKit>();
	}

	/** add kit to the specific location in the station  */
	public void addKit(GUIKit guiKit, int snum)
	{
		if (kits.get(snum) == null)
			kits.put(snum, guiKit);
		else
			throw new IllegalStateException("Couldn't put a kit into station number " + snum + ". There is a kit already there!");
	}

	/** remove kit from the station  */
	public GUIKit removeKit(int snum)
	{
		GUIKit tempKit = kits.get(snum);
		kits.put(snum, null);
		return tempKit;
	}

	/** return the kit in the specific location in the station  */
	public GUIKit getKit(int snum)
	{
		return kits.get(snum);
	}

	/** draw the kits in the station  */
	public void draw(Graphics2D g, long currentTime)
	{
		Painter.draw(g, Painter.ImageEnum.KIT_TABLE, 175, -1, currentTime, movement, true);
		for (int snum : kits.keySet())
		{
			GUIKit kit = getKit(snum);
			if (kit != null)
			{
				int yOffset = 0;
				if (snum == 0)
					yOffset = -90;
				if (snum == 2)
					yOffset = 90;
				
				kit.movement = movement.offset(new Point2D.Double(0, yOffset), 0);
				kit.draw(g, currentTime);
			}
		}
	}

	/** return camera station  */
	public Point2D.Double getCameraStationLocation()
	{
		return new Point2D.Double(movement.getStartPos().x + 175/2, movement.getStartPos().y + 300/2);
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

















