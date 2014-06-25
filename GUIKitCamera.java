import java.awt.Graphics2D;
import java.io.Serializable;


@SuppressWarnings("serial")
public class GUIKitCamera implements GUIItem, Serializable 
{
	/** used to access KitCamera data */
	KitCamera kitCamera;
	/** used to access Movement data */
	Movement movement;
	/** used to determine the time the kit camera appears */
	long birthTime, lifeLength;
	/** initialize variables */
	public GUIKitCamera ( KitCamera kitCamera, Movement movement, long currentTime, long lifeLength ) {
		this.kitCamera = kitCamera;
		this.movement = movement;
		this.birthTime = currentTime;
		this.lifeLength = lifeLength;
	}
	/** true if camera should go off */
	public boolean isExpired(long currentTime)
	{
		if (birthTime - currentTime > lifeLength)
			return true;
		return false;
	}
	/** draws the kit camera */
	public void draw(Graphics2D g, long currentTime)
	{
		if (!isExpired(currentTime))
			Painter.draw(g, Painter.ImageEnum.CAMERA_FLASH, currentTime, movement, true);
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
