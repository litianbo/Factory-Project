import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;


/** defines a kit and its attributes */
@SuppressWarnings("serial")
public class Kit implements Serializable
{
	public static final int MIN_PARTS = 4;
	public static final int MAX_PARTS = 8;

	// Kit statuses
	/** all kits initialized to incomplete */
	public static final int INCOMPLETE = 0;
	/** used when kit contains incorrect part in any location */
	public static final int INCORRECT = 1;
	/** signifies completed kit with correct parts */
	public static final int COMPLETE = 2;
	
	private int number;
	private String name, description;
	private int kitStatus; // Use kit statuses above
	/** TreeMap of parts contained in kit */
	public TreeMap<Integer, Part> parts;

	/**
	 * Constructor of empty kit for use on Kit Delivery Station
	 * when conveyer brings in empty kits.
	 * 
	 */
	public Kit()
	{
		name = "";
		description = "";
		number = 0;
		kitStatus = INCOMPLETE;
		parts = new TreeMap<Integer, Part>();
	}
		
	/** normal constructor for Kit */
	public Kit(String name, String description, int kitNumber)
	{
		this.name = name;
		this.description = description;
		this.number = kitNumber;
		kitStatus = INCOMPLETE;
		parts = new TreeMap<Integer, Part>();
	}

	public Part getPart(int index) {
		return parts.get(index);
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public int getStatus()
	{
		return kitStatus;
	}
	
	/** add part to the kit, returns whether succeeded */
	public boolean addPart(int index, Part p)
	{
		if(parts.size() <= 8)
		{
			parts.put(index, p);
			return true;
		}
		else
		{
			return false;
		}	
	}

	/** remove part from kit, returns whether succeeded */
	public boolean removePart(int index)
	{
		if(parts.containsKey(index))
		{
			parts.remove(index);
			return true;
		}
		return false;
	}
	
	public void removeAllParts()
	{
		parts.clear();
	}
	
	public TreeMap<Integer, Part> getParts()
	{
		return parts;
	}
}
