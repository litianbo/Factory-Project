import java.io.*;

/** networking message indicating to add a new part
    (is a separate class even though it only contains 1 instance variable because it specifies that the command is to add a new part) */
public class NewPartMsg implements Serializable {
	/** Part instance to add */
	public Part part;

	/** constructor to set up NewPartMsg with specified Part */
	public NewPartMsg(Part newPart) {
		part = newPart;
	}
}
