import java.io.*;
import java.util.*;

/** networking message listing all available parts */
public class PartListMsg implements Serializable {
	/** ArrayList of available parts */
	public ArrayList<Part> parts;

	/** constructor for empty PartListMsg */
	public PartListMsg() {
		parts = new ArrayList<Part>();
	}

	/** constructor for non-empty PartListMsg */
	public PartListMsg(ArrayList<Part> newParts) {
		parts = newParts;
	}
}
