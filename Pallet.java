import java.io.Serializable;


@SuppressWarnings("serial")
public class Pallet implements Serializable {
	/** the kit on the pallet. */
	private Kit kit;
	/** initialize variable */
	public Pallet(Kit kit) {
		this.kit = kit;
	}
	/** add an empty kit to the pallet if it is empty */
	public void addKit(Kit kit)
	{
		if (this.kit == null)
			this.kit = kit;
		else
			throw new IllegalArgumentException("Cannot add a kit to this pallet -- it already has one");
	}
	/** true if pallet has a kit */
	public boolean hasKit()
	{
		return (kit != null);
	}
	/** remove the kit from pallet */
	public Kit removeKit()
	{
		Kit tempKit = kit;
		kit = null;
		return tempKit;
	}

	/** getter for kit */
	public Kit getKit()
	{
		return kit;
	}
}
