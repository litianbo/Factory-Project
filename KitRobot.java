import java.io.Serializable;


@SuppressWarnings("serial")
/** This class defines and controls a kit robot. */
public class KitRobot implements Serializable {
	/** a Kit variable of the kit in its pallet */
	private Kit kit;

	/** states that a kit robot could be in */
	public enum KRState {
		OFF, BROKEN, IDLE, PICK_UP, DROP_OFF, KIT_STAND
	}
	
	/** what kit robot is currently doing */
	public KRState state;
	/** TreeMap ID corresponding to which kit stand location to move to (if state is KIT_STAND) */
	public int targetID;
	
	/** initialize variables */
	public KitRobot() {
		kit = null;
		state = KRState.IDLE;
	}
	
	public void setKit(Kit kit)
	{
		if (this.kit == null)
			this.kit = kit;
		else
			throw new IllegalStateException("Cannot give the kit robot another kit! It is already holding one.");
	}
	/** remove kit from the robot */
	public Kit removeKit()
	{
		Kit tempKit = kit;
		kit = null;
		return tempKit;
	}

	public Kit getKit()
	{
		return kit;
	}
}
