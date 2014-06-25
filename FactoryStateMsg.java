import java.io.*;
import java.util.*;

/** networking message containing all information needed to generate factory state
    note that if a client sends an empty FactoryStateMsg, it means they are requesting to be kept up-to-date with the factory state as long as it is connected to the server */
public class FactoryStateMsg implements Serializable {
	/** time (in milliseconds) elapsed since simulation started,
	    calculate using System.currentTimeMillis() - timeStart */
	public long timeElapsed;
	/** time (in milliseconds) that simulation started, value is specific to this client or server,
	    sync with server by setting to System.currentTimeMillis() - timeElapsed */
	public long timeStart;
	/** TreeMap containing paintable factory items with unique integer ID */
	public TreeMap<Integer, GUIItem> items;

	/** constructor to instantiate empty TreeMaps */
	public FactoryStateMsg() {
		timeStart = System.currentTimeMillis();
		timeElapsed = 0;
		items = new TreeMap<Integer, GUIItem>();
	}

	/** adds the specified item to the end of the items map */
	public void add(GUIItem item) {
		if (items.isEmpty()) {
			items.put(0, item);
		}
		else {
			items.put(items.lastKey() + 1, item);
		}
	}

	/** updates the factory state with specified ItemUpdateMsg */
	public void update(FactoryUpdateMsg msg) {
		timeStart = System.currentTimeMillis() - msg.timeElapsed;
		timeElapsed = msg.timeElapsed;
		items.putAll(msg.putItems);
		for (int i : msg.removeItems) {
			items.remove(i);
		}
		for (Map.Entry<Integer, Movement> e : msg.itemMoves.entrySet()) {
			items.get(e.getKey()).setMove(e.getValue());
		}
	}

	/** updates the time elapsed based on system time */
	public void updateTime() {
		timeElapsed = System.currentTimeMillis() - timeStart;
	}
}
