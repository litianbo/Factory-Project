import java.io.*;
import java.util.*;

/** networking message updating factory state */
public class FactoryUpdateMsg implements Serializable {
	/** time (in milliseconds) elapsed since simulation start on server */
	public long timeElapsed;
	/** TreeMap in which key is ID of new or updated item and entry is new or updated item */
	public TreeMap<Integer, GUIItem> putItems;
	/** ArrayList containing IDs of deleted items */
	public ArrayList<Integer> removeItems;
	/** TreeMap updating the movements of items with given IDs */
	public TreeMap<Integer, Movement> itemMoves;

	/** basic constructor to instantiate empty instance variables */
	public FactoryUpdateMsg() {
		putItems = new TreeMap<Integer, GUIItem>();
		removeItems = new ArrayList<Integer>();
		itemMoves = new TreeMap<Integer, Movement>();
	}

	/** constructor to also initialize time elapsed */
	public FactoryUpdateMsg(FactoryStateMsg state) {
		this(); // call basic constructor
		setTime(state);
	}

	/** adds the specified item to the end of the items map */
	public void add(FactoryStateMsg state, GUIItem item) {
		putItems.put(Math.max(state.items.isEmpty() ? -1 : state.items.lastKey(), putItems.isEmpty() ? -1 : putItems.lastKey()) + 1, item);
	}

	/** set the time elapsed based on system time */
	public void setTime(FactoryStateMsg state) {
		timeElapsed = System.currentTimeMillis() - state.timeStart;
	}
}
