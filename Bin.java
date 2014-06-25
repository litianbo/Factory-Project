import java.io.Serializable;



@SuppressWarnings("serial")
public class Bin implements Serializable
{
	/** part type in bin */
	public Part part;
	/** number of parts in bin */
	private int numParts;
	
	public Bin(Part p, int numParts)
	{
		part = p;
		this.numParts = numParts;
		
		if (numParts < 0)
			System.err.println("Can't fill bin with a negative number of parts!");
		
	}
	
	/** Returns false if the bin is not empty, returns true if filling was successful */
	public boolean fillBin( Part p, int numParts )
	{
		if (hasParts())
			return false;
		part = p;
		this.numParts = numParts;
		return true;
	}
	
	public boolean isEmpty()
	{
		return numParts == 0;
	}
	
	public boolean hasParts()
	{
		return numParts > 0;
	}

	/** Empties the bin, returns the number of parts that was in it */
	public int dumpBin()
	{
		int oldQty = numParts;
		numParts = 0;
		return oldQty;
	}

	/** getter for number of parts in bin */
	public int getNumParts() {
		return numParts;
	}
}
