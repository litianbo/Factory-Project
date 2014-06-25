import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.net.Socket;

@SuppressWarnings("serial")
public class LanesClient extends JFrame implements ActionListener, Networked {
	/** sends Msg classes to the server (communicate with server) */
	private NetComm netComm;
	/** cardlayout for switching between ConnectPanel and LanePanel */
	private CardLayout layout;
	/** connect panel for connecting to the server  */
	private ConnectPanel cPanel;
	/** laneManager for viewing the lane */
	private LaneManager lPanel;
	
	/** initialize variables */
	public LanesClient(){
		Painter.loadImages();
		
		cPanel = new ConnectPanel(this);
		lPanel = new LaneManager(this);
		
		layout = new CardLayout();
		setLayout(layout);
		add(cPanel, "connect");
		add(lPanel, "lanes");
		
		setTitle("Lane Client");
		setSize( 1100, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		new Timer(LaneGraphics.UPDATE_RATE, this).start();
	}
	
	public static void main(String[] args){
		LanesClient LClient = new LanesClient();
	}
	
	/** handles message from the server */
	public void msgReceived(Object msgObj, NetComm sender) {
		if (msgObj instanceof CloseConnectionMsg) { //handles CloseConnectionMsg
			netComm.close();
			netComm = null;
			cPanel.reset();
			cPanel.setActionMsg("Unexpectedly disconnected from server");
			layout.show(this.getContentPane(), "connect");
		} else if (msgObj instanceof FactoryUpdateMsg) { //handles update message
			lPanel.update( (FactoryUpdateMsg) msgObj );
		} else if (msgObj instanceof FactoryStateMsg) { //handles state message
			lPanel.setFactoryState( (FactoryStateMsg) msgObj );
		}
		else {
			System.out.println("Warning: received unknown message " + msgObj);
		}
	}
	
	/** Connects to server when connect button is pressed or repaints each time the Timer goes off */
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == cPanel) {
			try {
				netComm = new NetComm(new Socket(ae.getActionCommand(), Server.PORT), this);
				layout.show(this.getContentPane(), "lanes");
				netComm.write( new FactoryStateMsg() );
			}
			catch (Exception ex) {
				netComm = null;
				cPanel.setActionError("Could not connect to server; check that it was entered correctly");
			}
		} else if (ae.getSource() instanceof Timer) {
			repaint();
		}
	}
	
	public NetComm getCom(){
		return netComm;
	}
}
