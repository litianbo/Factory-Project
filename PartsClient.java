import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class PartsClient extends JFrame implements ActionListener, Networked {
	/** use this variable to send Msg classes to the server (communicate with server) */
	private NetComm netComm;
	/** cardlayout to switch between connection panel and part manager */
	private CardLayout layout;
	/** connection panel for connecting to the server  */
	private ConnectPanel cPanel;
	/** Part Manager variable */
	private PartManager mPanel;
	/** Arraylist of parts that are sent from server */
	private ArrayList<Part> allParts;
	/** initialize variables */
	public PartsClient(){
		allParts = new ArrayList<Part>();
		cPanel = new ConnectPanel(this);
		mPanel = new PartManager(this);
		
		layout = new CardLayout();
		setLayout(layout);
		add(cPanel, "connect");
		add(mPanel, "manage");
		
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle( "Parts Client" );
		setVisible(true);
	}
	
	public static void main(String[] args) {
		PartsClient pClient = new PartsClient();
	}
	
	public void msgReceived(Object msgObj, NetComm sender) {
		if (msgObj instanceof CloseConnectionMsg) { //handles CloseConnectionMsg
			netComm.close();
			netComm = null;
			cPanel.reset();
			cPanel.setActionMsg("Unexpectedly disconnected from server");
			layout.show(this.getContentPane(), "connect");
		}
		else if (msgObj instanceof StringMsg) { //handles messages of parts being added, deleted, changed
			StringMsg temp = (StringMsg)msgObj;
			mPanel.setMsg( temp.message );
		}
		else if (msgObj instanceof PartListMsg) { //handles request for a list of parts
			PartListMsg temp = (PartListMsg)msgObj;
			allParts = temp.parts;
			mPanel.displayParts();
		}
		else {
			System.out.println("Warning: received unknown message " + msgObj);
		}
	}
	/** when button "connect" is pressed, client is connecting to the server */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == cPanel) { //connect to server
			try {
				netComm = new NetComm(new Socket(ae.getActionCommand(), Server.PORT), this);
				layout.show(this.getContentPane(), "manage");
				netComm.write( new PartListMsg() );
			}
			catch (Exception ex) {
				netComm = null;
				cPanel.setActionError("Could not connect to server; check that it was entered correctly");
			}
		}
	}
	/** return NetComm variable */
	public NetComm getCom(){
		return netComm;
	}
	/** return the parts that are sent from Server */
	public ArrayList<Part> getParts(){
		return allParts;
	}

}
