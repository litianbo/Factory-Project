import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.Timer;

/** shows the moving part robot, kit robot, kit stand, and kit delivery station (communicate with server) */
public class KitAssemblyClient extends JFrame implements ActionListener, Networked {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @param args
	 */
	/** use this variable to send Msg classes to the server (communicate with server) */
	private NetComm netComm;
	/** cardlayout to switch between connection panel and part manager */
	private CardLayout cardlayout;
	/** connection panel for connecting to the server  */
	private ConnectPanel cPanel;
	/** KitAssemblyManager variable */
	private KitAssemblyManager kam;
	/** initialize variables */
	public KitAssemblyClient(){
		Painter.loadImages();
		cPanel = new ConnectPanel(this);
		cardlayout = new CardLayout();
		kam = new KitAssemblyManager(this);
		setLayout(cardlayout);
		add(cPanel, "connect");
		add(kam, "kam");
		
		Dimension gfxSize = FactoryPainter.getAreaSize(FactoryPainter.FactoryArea.KIT_MANAGER);
		setSize(gfxSize.width, gfxSize.height + 80); // +80 is for the button strip along the bottom
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Kit Assembly Manager");
		setVisible(true);
		new Timer(50, this).start();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new KitAssemblyClient();
	}

	@Override
	public void msgReceived(Object msgObj, NetComm sender) {
		// TODO Auto-generated method stub
		if (msgObj instanceof CloseConnectionMsg) { //handles CloseConnectionMsg
			netComm.close();
			netComm = null;
			cPanel.reset();
			cPanel.setActionMsg("Unexpectedly disconnected from server");
			cardlayout.first(this.getContentPane());
		} else if (msgObj instanceof FactoryStateMsg) { //handles request for factory state
			FactoryStateMsg state = (FactoryStateMsg)msgObj;
			kam.getKitAssemblyGraphics().setFactoryState(state);
			cardlayout.last(this.getContentPane());
		}
		else if (msgObj instanceof FactoryUpdateMsg) { //handles request for factory update
			FactoryUpdateMsg update = (FactoryUpdateMsg)msgObj;
			kam.getKitAssemblyGraphics().update(update);
		}
		
		else {
			System.out.println("Warning: received unknown message " + msgObj);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cPanel) { //connect to server
			try {
				netComm = new NetComm(new Socket(e.getActionCommand(), Server.PORT), this);
				netComm.write( new FactoryStateMsg() );
			}
			catch (Exception ex) {
				netComm = null;
				cPanel.setActionError("Could not connect to server; check that it was entered correctly");
			}
		}
		else if (e.getSource() instanceof Timer) {
			repaint();
		}
	}
	public NetComm getNetComm(){
		
		return netComm;
	}
}
