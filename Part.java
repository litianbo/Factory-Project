import java.io.Serializable;


/** defines a part and its attributes */
@SuppressWarnings("serial")
public class Part implements Serializable 
{
	/** name and description of the part */
	private String name, description;
	/** the part number */
	private int number;
	/** image of the part */
	private Painter.ImageEnum image;
	
	/** define a part with a blank name and description with number 0 */
	public Part() {
		name = "";
		description = "";
		number = 0;
		image = Painter.ImageEnum.BANANA;
	}
	
	/** define a part with specific name, description, part number, and image */
	public Part(String name, String description, int partNumber, Painter.ImageEnum image)
	{
		this.name = name;
		this.description = description;
		this.number = partNumber;
		this.image = image;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}

	public Painter.ImageEnum getImage()
	{
		return image;
	}

	public void setImage(Painter.ImageEnum image) {
		this.image = image;
	}
}
