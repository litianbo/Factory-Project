import java.io.*;

/** networking message indicating to break or fix an item */
public class NonNormativeMsg implements Serializable {
	/** items that can be broken or fixed */
	public enum ItemEnum {
		KIT_ROBOT, PART_ROBOT, KIT_DELIV, NEST, LANE, FEEDER, GANTRY
	}
	/** non-normative scenarios */
	public enum CmdEnum {
		FIX, BREAK, DROP_PART, JUMP_LANE
	}

	/** type of item involved */
	ItemEnum type;
	/** index of item involved, if more than 1 of this item (e.g. 0 to 3 for lanes) */
	int index;
	/** non-normative command */
	CmdEnum cmd;

	/** constructor for NonNormativeMsg */
	public NonNormativeMsg(ItemEnum newType, int newIndex, CmdEnum newCmd) {
		type = newType;
		index = newIndex;
		cmd = newCmd;
	}
}
