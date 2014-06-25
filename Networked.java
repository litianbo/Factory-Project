/** indicates that implementing class is capable of receiving messages across the network */
public interface Networked {
	/** handle message received from the network */
	public void msgReceived(Object msgObj, NetComm sender);
}
