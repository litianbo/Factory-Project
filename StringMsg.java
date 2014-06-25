import java.io.*;

/** networking message containing a string;
    this class is modified from StringMsg.java in Andrew's HW3 submission */
public class StringMsg implements Serializable {
	/** categories that can be associated with a StringMsg */
	public enum MsgType {
		NEW_PART, CHANGE_PART, DELETE_PART, NEW_KIT, CHANGE_KIT, DELETE_KIT, PRODUCE_KITS, NON_NORMATIVE
	}

	/** type of message */
	public MsgType type;
	/** content of string message (generally empty strings indicate success and non-empty strings are an error description) */
	public String message;

	/** constructor to set up StringMsg with specified type and message */
	public StringMsg(MsgType newType, String newMessage) {
		type = newType;
		message = newMessage;
	}
}
