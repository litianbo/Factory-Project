import java.io.*;
import java.util.*;

/** networking message listing status of all kits in production */
public class ProduceStatusMsg implements Serializable {
	/** possible statuses that a kit command can have */
	public enum KitStatus {
		QUEUED, PRODUCTION, COMPLETE
	}

	/** ArrayList of ProduceKitsMsg's that have been sent to server */
	public ArrayList<ProduceKitsMsg> cmds;
	/** ArrayList indicating status of each kit command */
	public ArrayList<KitStatus> status;



	/** constructor for empty ProduceStatusMsg */
	public ProduceStatusMsg() {
		cmds = new ArrayList<ProduceKitsMsg>();
		status = new ArrayList<KitStatus>();
	}

}
