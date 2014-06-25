	import java.net.*;
	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;
	import java.util.*;

public class KitsClient extends JFrame implements ActionListener, Networked 
{
	/** use this variable to send Msg classes to the server (communicate with server) */
	private NetComm netComm;
	/** layout to switch between connection panel and kit manager */
	private CardLayout layout;
	/** connection panel for connecting to the server  */
	private ConnectPanel cPanel;
	/** Kit Manager variable */
	private KitManager mPanel;
	/** Arraylist of kits that are sent from server */
	private ArrayList<Kit> allKits;
	/** Arraylist of parts that are sent from server */
	private ArrayList<Part> allParts;
	/** Initialization of variables */
	public KitsClient(){
		allKits = new ArrayList<Kit>();
		allParts = new ArrayList<Part>();
		cPanel = new ConnectPanel(this);
		mPanel = new KitManager(this);
		
		layout = new CardLayout();
		setLayout(layout);
		add(cPanel, "connect");
		add(mPanel, "manage");
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		KitsClient kClient = new KitsClient();
	}
	/** called when the server sends a message to this client */
	public void msgReceived(Object msgObj, NetComm sender) {
		if (msgObj instanceof CloseConnectionMsg)  //handles CloseConnectionMsg
		{ 
			netComm.close();
			netComm = null;
			cPanel.reset();
			cPanel.setActionMsg("Unexpectedly disconnected from server");
			layout.show(this.getContentPane(), "connect");
		}
		else if (msgObj instanceof StringMsg) //handles messages of kits being added, deleted, changed
		{ 
			StringMsg temp = (StringMsg)msgObj;
			mPanel.setMsg( temp.message );
		}
		else if (msgObj instanceof PartListMsg)  //handles request for a list of parts
		{ 
			PartListMsg temp = (PartListMsg)msgObj;
			allParts = temp.parts;
			mPanel.generatePartList();
		}
		else if(msgObj instanceof KitListMsg) //handles request for a list of kits
		{
			KitListMsg temp = (KitListMsg)msgObj;
			allKits = temp.kits;
			mPanel.displayKits();
		}
		else {
			System.out.println("Warning: received unknown message " + msgObj);
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == cPanel) { //connect to server
			try {
				netComm = new NetComm(new Socket(ae.getActionCommand(), Server.PORT), this);
				layout.show(this.getContentPane(), "manage");
				netComm.write( new KitListMsg() );
				mPanel.requestKits();
				netComm.write( new PartListMsg());
				mPanel.requestParts();
				mPanel.generateKitList();
			}
			catch (Exception ex) {
				netComm = null;
				cPanel.setActionError("Could not connect to server; check that it was entered correctly");
			}
		}
	}
	/** used for easy access of this client's instance of NetComm */
	public NetComm getCom()
	{
		return netComm;
	}
	/** return the kits that are sent from Server */
	public ArrayList<Kit> getKits()
	{
		return allKits;
	}
	/** return the parts that are sent from Server */
	public ArrayList<Part> getParts()
	{
		return allParts;
	}

}
