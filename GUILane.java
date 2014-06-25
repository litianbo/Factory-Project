import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;

/** represents a drawable lane */
@SuppressWarnings("serial")
public class GUILane implements GUIItem, Serializable
{
	/** width (in pixels) of one segment */
	public static final int SEG_WIDTH = 60;
	/** distance that items stop between each other */
	private static final int ITEM_SPACING = 30;

	/** reference to lane instance */
	private Lane lane;
	/** location of top left corner of lane */
	private Point2D.Double pos;
	/** movement of conveyor belt */
	public Movement movement;
	
	/** whether this lane is for parts (as opposed to pallets) */
	boolean isForParts;
	/** relative positions of items on lane */
	private ArrayList<Point2D.Double> offsets;
	
	/** number of lane segments */
	private int nSegments;
	
	/** distance that items stop from end of lane */
	private final int conveyorEndPadding = 30;

	/** constructor for GUILane */
	public GUILane(Lane lane, boolean isForParts, int nSegments, double x, double y)
	{
		this.lane = lane;
		
		this.isForParts = isForParts;
		
		this.nSegments = nSegments;
		pos = new Point2D.Double(x, y);
		movement = new Movement(new Point2D.Double(), 0);
		offsets = new ArrayList<Point2D.Double>();
	}

	/** draw lane at specified time */
	public void draw(Graphics2D g, long currentTime)
	{
		int i;
		BufferedImage i_temp = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g_temp = i_temp.createGraphics();
		
		// Draw to the bufferedImage
		for (i = 0; i < nSegments + 2; i++) // draw more segments than lane width because partial segments will be drawn at ends
		{
			Painter.draw(g_temp, Painter.ImageEnum.LANE, currentTime,
			             movement.offset(new Point2D.Double(pos.x + SEG_WIDTH*((nSegments-1) - (i-1)), pos.y), 0), false);
		}
		
		// Crop the bufferedImage
		BufferedImage croppedBI = i_temp.getSubimage(Math.max((int)pos.x, 0), Math.max((int)pos.y, 0), getLength(), 200);
		// Draw it to the original graphics object
		g.drawImage(croppedBI, (int)pos.x, (int)pos.y, null);
		
		// Draw the shadows at the ends
		if (pos.y < 0)
		{
			Painter.draw(g, Painter.ImageEnum.SHADOW2, 10, 82, currentTime, new Movement(pos, 0), false);
			Painter.draw(g, Painter.ImageEnum.SHADOW2, 10, 82, currentTime, 
					 new Movement(new Point2D.Double(pos.x + getLength()-10, pos.y), Math.PI), false);
		} else {
			Painter.draw(g, Painter.ImageEnum.SHADOW2, 10, 91, currentTime, new Movement(pos, 0), false);
			Painter.draw(g, Painter.ImageEnum.SHADOW2, 10, 91, currentTime, 
						 new Movement(new Point2D.Double(pos.x + getLength()-10, pos.y), Math.PI), false);
		}
		
		
		// Draw the items
		for (i = 0; i < lane.getItems().size(); i++)
		{
			lane.getItems().get(i).setMove(new Movement(getItemLocation(i, currentTime), Math.PI / 2)
					.offset(new Point2D.Double(0, (Math.random() - 0.5) * lane.getAmplitude() * 5), 0));
			lane.getItems().get(i).draw(g, currentTime);
		}
		
	}

	/** returns whether lane is turned on */
	public boolean isLaneOn()
	{
		return lane.isLaneOn();
	}

	/** turn off lane and stop conveyor */
	public void turnOff(long currentTime)
	{
		lane.turnOff();
		movement = movement.freeze(currentTime); // stop the lane
	}

	/** turn on lane and start conveyor */
	public void turnOn(long currentTime)
	{
		lane.turnOn();
		movement = movement.moveToAtSpeed(currentTime, new Point2D.Double(-SEG_WIDTH, 0), 0, lane.getSpeed()); // start the lane
	}

	/** getter for broken */
	public boolean isLaneBroken()
	{
		return lane.getBroken();
	}

	/** set whether lane is broken */
	public void setBroken(boolean newBroken, long currentTime)
	{
		lane.setBroken(newBroken);
		if (newBroken)
		{
			turnOff(currentTime); // stop the lane if broken
		}
		else
		{
			turnOn(currentTime); // start lane if fixed
		}
	}
	
	/** set the amplitude of lane */
	public void setAmplitude( double amplitude, long currentTime ) {
		if (lane.getAmplitude() != Lane.AMP_HIGH && amplitude == Lane.AMP_HIGH && Math.random() < 0.3)
		{
			setBroken(false, currentTime); // increasing amplitude has 30% chance of un-jamming lane (but this can be done multiple times to un-jam lane)
		}
		lane.setAmplitude( amplitude );
	}
	
	/** get the amplitude of the lane */
	public double getAmplitude () {
		return lane.getAmplitude();
	}

	/** whether should move all segments back 1 segment width */
	public boolean shouldReset(long currentTime) {
		return lane.isLaneOn() && movement.arrived(currentTime + Server.UPDATE_RATE);
	}

	/** move lane segments back 1 segment width (must call this before lane moves a segment width) */
	public void reset(long currentTime)
	{
		for (int i = 0; i < offsets.size(); i++)
		{
			offsets.get(i).x -= SEG_WIDTH;
		}
		movement = movement.offset(new Point2D.Double(SEG_WIDTH, 0), 0)
		                   .moveToAtSpeed(currentTime, new Point2D.Double(-SEG_WIDTH, 0), 0, lane.getSpeed());
	}

	/** add an empty pallet to start of lane */
	public void addEmptyPallet(long currentTime)
	{
		addItem(new GUIPallet(new Pallet(new Kit()), 0, 0), new Point2D.Double(pos.x-50+getLength(), 0), currentTime);
	}

	/** add specified item at specified position in lane, returns index of added item */
	public int addItem(GUIItem p, Point2D.Double itemPos, long currentTime)
	{
		// find index to insert item at
		int i;
		if (itemPos.x >= pos.x + getLength() - 1) {
			i = offsets.size(); // insert item at end if at end of lane
		}
		else {
			for (i = 0; i < offsets.size(); i++)
			{
				if (itemPos.x < getItemLocation(i, currentTime).x) break;
			}
		}
		// insert item
		lane.addItem(i, p);
		offsets.add(i, new Point2D.Double(itemPos.x - pos.x, itemPos.y));
		return i;
	}

	/** remove item with specified index from the lane, returns removed item */
	public GUIItem removeItem(int index, long currentTime)
	{
		// set all item offsets to their actual offsets
		for (int i = 0; i < offsets.size(); i++)
		{
			offsets.get(i).x = actualOffsetX(i, currentTime);
		}
		// remove item
		offsets.remove(index);
		return lane.removeItem(index);
	}
	
	/** remove kit from last pallet in lane, returns removed kit */
	public Kit removeEndPalletKit()
	{
		Object item = lane.getItems().get(0);
		if (!(item instanceof GUIPallet)) return null;
		return ((GUIPallet)item).pallet.removeKit();
	}

	/** returns whether there's an empty pallet at end of lane */
	public boolean hasEmptyPalletAtEnd(long currentTime)
	{
		if (!itemAtEnd(0, currentTime)) return false;
		Object item = lane.getItems().get(0);
		if (!(item instanceof GUIPallet)) return false;
		return !((GUIPallet)item).pallet.hasKit();
	}

	/** returns whether there's a full pallet at end of lane */
	public boolean hasFullPalletAtEnd(long currentTime)
	{
		if (!itemAtEnd(0, currentTime)) return false;
		Object item = lane.getItems().get(0);
		if (!(item instanceof GUIPallet)) return false;
		return ((GUIPallet)item).pallet.hasKit();
	}

	/** returns index of item at end of specified sub-lane, or -1 if no item in that sub-lane */
	public int endItem(int offsetY)
	{
		for (int i = 0; i < offsets.size(); i++)
		{
			if (Math.round(offsets.get(i).y) == offsetY) return i;
		}
		return -1;
	}

	/** returns whether item with specified index is at end of lane */
	public boolean itemAtEnd(int index, long currentTime)
	{
		if (index < 0 || index >= offsets.size()) return false;
		return subIndex(index) == 0 && conveyorEndPadding >= movement.calcPos(currentTime).x + offsets.get(index).x;
	}
	
	/** returns whether lane contains any items */
	public boolean containsItems()
	{
		return lane.hasItems();
	}

	/** returns current location of item with specified index */
	public Point2D.Double getItemLocation(int index, long currentTime)
	{
		if (index < 0 || index >= offsets.size()) return null;
		double offsetY = -12;
		if (offsets.get(index).y < -0.5) offsetY = 90 * 0.25;
		if (offsets.get(index).y > 0.5) offsetY = 90 * 0.75;
		return movement.offset(new Point2D.Double(pos.x + actualOffsetX(index, currentTime), pos.y + offsetY), 0).calcPos(currentTime);
	}

	/** returns actual x offset of item considering that it might be at end of lane */
	public double actualOffsetX(int index, long currentTime)
	{
		if (index < 0 || index >= offsets.size()) throw new IllegalArgumentException("invalid item index in lane");
		if (conveyorEndPadding + ITEM_SPACING * subIndex(index) >= movement.calcPos(currentTime).x + offsets.get(index).x)
		{
			// item is waiting in line
			return conveyorEndPadding + ITEM_SPACING * subIndex(index) - movement.calcPos(currentTime).x;
		}
		else {
			// specified offset is correct
			return offsets.get(index).x;
		}
	}

	/** returns item number within "sub-lane" only containing items at that y offset */
	public int subIndex(int index)
	{
		if (index < 0 || index >= offsets.size()) throw new IllegalArgumentException("invalid item index in lane");
		int ret = 0;
		for (int i = 0; i < index; i++)
		{
			if (Math.round(offsets.get(i).y) == Math.round(offsets.get(index).y)) ret++;
		}
		return ret;
	}

	/** getter for items */
	public ArrayList<GUIItem> getItems() {
		return lane.getItems();
	}

	/** getter for offsets */
	public ArrayList<Point2D.Double> getOffsets() {
		return offsets;
	}

	/** returns length of lane */
	public int getLength()
	{
		return nSegments * SEG_WIDTH;
	}
	
	/** getter for nSegments */
	public int getNSegments()
	{
		return nSegments;
	}

	/** getter for position */
	public Point2D.Double getPos()
	{
		return pos;
	}

	/** setter for movement */
	public void setMove(Movement movement)
	{
		this.movement = movement;
	}

	/** getter for movement */
	public Movement getMove()
	{
		return movement;
	}
}
