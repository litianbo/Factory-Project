import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;


@SuppressWarnings("serial")
public class GUIPallet implements GUIItem, Serializable
{
	public Pallet pallet;
	public Movement movement;
	
	
	public GUIPallet(Pallet pallet, double x, double y)
	{
		this.pallet = pallet;
		movement = new Movement(new Point2D.Double(x,y), Math.PI/2);
	}
	
	public GUIPallet(Pallet pallet, Movement movement)
	{
		this.pallet = pallet;
		this.movement = movement;
	}

	public void draw(Graphics2D g, long currentTime)
	{
		Painter.draw(g, Painter.ImageEnum.PALLET, currentTime, movement, false);
		if (pallet.hasKit())
		{
			GUIKit guiKit = new GUIKit(pallet.getKit(), movement.offset(new Point2D.Double(40, 60), -Math.PI / 2));
			guiKit.draw(g, currentTime);
		}
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
