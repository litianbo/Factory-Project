import java.io.*;

/** networking message indicating to add a new kit
    (is a separate class even though it only contains 1 instance variable because it specifies that the command is to add a new kit) */
public class NewKitMsg implements Serializable {
	/** Kit instance to add */
	public Kit kit;

	/** constructor to set up NewKitMsg with the new Kit */
	public NewKitMsg(Kit newKit) {
		kit = newKit;
	}
}
