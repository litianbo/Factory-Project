import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Map;

public class GUIBin implements GUIItem, Serializable 
{
	public Bin bin;
	public Movement movement;
	
	public GUIBin( Bin bin, double x, double y )
	{
		movement = new Movement(new Point2D.Double(x,y), 0);
		this.bin = bin;
	}

	public GUIBin( Bin bin, Movement movement)
	{
		this.movement = movement;
		this.bin = bin;
	}

	public void draw( Graphics2D g, long currentTime)
	{
		draw(g, currentTime, false);
	}
	
	public void draw( Graphics2D g, long currentTime, boolean isInGantry )
	{		
		double pickUpScale = 1;
		if (isInGantry)
			pickUpScale = 1.2;
		
		Painter.draw(g, Painter.ImageEnum.PARTS_BOX, (int)(100*pickUpScale), (int)(100*pickUpScale), 
					 currentTime, movement, true);
		
		// Draw sticker
		Point2D.Double pt = movement.calcPos(currentTime);
		int stickerDiameter = 50;
		// Sticker border
		g.setColor(Color.GRAY);
		g.fillOval((int)pt.x+1 - stickerDiameter/2, (int)pt.y+1 - stickerDiameter/2, stickerDiameter, stickerDiameter);
		g.setColor(Color.WHITE);
		g.fillOval((int)pt.x+1+5 - stickerDiameter/2, (int)pt.y+1+5 - stickerDiameter/2, stickerDiameter-10, stickerDiameter-10);
		
		if (hasParts())
		{
			new GUIPart(bin.part, movement).draw(g, currentTime);
		}
	}
	
	public boolean inPosition(long currentTime)
	{
		return movement.arrived(currentTime);
	}
	
	/** Returns false if the bin is not empty, returns true if filling was successful */
	public boolean fillBin( Part part, int numParts )
	{
		return bin.fillBin(part, numParts);
	}
	
	public boolean isEmpty()
	{
		return bin.isEmpty();
	}
	
	public boolean hasParts()
	{
		return bin.hasParts();
	}

	/** Empties the bin, returns the number of parts that was in it */
	public int dumpBin()
	{
		return bin.dumpBin();
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
