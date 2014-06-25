// client modified from Andrew's lab 6 made with the sole purpose of testing Server.java
// search "ALL CLIENTS" for things that all clients need to do to communicate with server
// delete this after we start the actual factory project code

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestClient extends JFrame implements ActionListener, Networked {
	private NetComm netComm; // ALL CLIENTS must have a NetComm instance

	private CardLayout layout;
	private ConnectPanel panelConnect; // ALL CLIENTS must have a connect to server panel
	private JPanel panelChat;
	private JTextField text;
	private JButton button;
	private JLabel label;

	public TestClient() {
		// set up chat GUI
		// (normally the chat panel should have its own class, but this is a test program so I'm being lazy)
		panelChat = new JPanel();
		panelChat.setLayout(new BoxLayout(panelChat, BoxLayout.Y_AXIS));
		text = new JTextField();
		button = new JButton("Submit");
		label = new JLabel(" ");
		panelChat.add(text);
		panelChat.add(button);
		panelChat.add(label);
		button.addActionListener(this);
		// set up window
		layout = new CardLayout();
		setLayout(layout);
		// ALL CLIENTS must instantiate their connect to server panel and add it to the JFrame
		panelConnect = new ConnectPanel(this);
		add(panelConnect, "connect");
		add(panelChat, "chat");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		TestClient client = new TestClient();
	}

	// ALL CLIENTS must implement Networked and have this method (though what the method does may be different)
	public void msgReceived(Object msgObj, NetComm sender) {
		if (msgObj instanceof CloseConnectionMsg) { // ALL CLIENTS must handle receiving CloseConnectionMsg
			netComm.close();
			netComm = null;
			panelConnect.reset();
			panelConnect.setActionMsg("Unexpectedly disconnected from server");
			layout.show(this.getContentPane(), "connect");
		}
		else if (msgObj instanceof String) { // for the actual factory project we will never send strings, and instead have dedicated Msg objects like CloseConnectionMsg
			String msg = (String)msgObj;
			label.setText(msg);
		}
		else {
			System.out.println("Warning: received unknown message " + msgObj);
		}
	}

	public void actionPerformed(ActionEvent ae) {
		// ALL CLIENTS must implement ActionListener and instantiate their NetComm when connect panel says user pressed "Connect to Server" button
		if (ae.getSource() == panelConnect) {
			try {
				netComm = new NetComm(new Socket(ae.getActionCommand(), Server.PORT), this);
				layout.show(this.getContentPane(), "chat");
			}
			catch (Exception ex) {
				netComm = null;
				panelConnect.setActionError("Could not connect to server; check that it was entered correctly");
			}
		}
		else if (ae.getSource() == button) {
			netComm.write(text.getText());
			text.setText("");
		}
	}
}
