import java.io.Serializable;


public class ProduceUpdateMsg implements Serializable{

	/**
	 * @param args
	 */
	
	public ProduceStatusMsg updateStatus;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public ProduceUpdateMsg(ProduceStatusMsg status){
		updateStatus = status;
		
	}
}
