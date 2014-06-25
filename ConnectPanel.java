import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/** panel that all clients display to let user connect to server;
    this class is based on SignInPanel.java in Andrew's HW3 submission */
public class ConnectPanel extends JPanel implements ActionListener {
	/** text box to enter server domain name or IP address */
	private JTextField txtServerAddress;
	/** button to connect to server */
	private JButton btnConnect;
	/** label for displaying message or error */
	private JLabel lblActionMsg;
	/** reference to action listener to notify when user presses connect to server button */
	private ActionListener action;

	/** constructor for sign in panel */
	public ConnectPanel(ActionListener connectAction) {
		// start setting up layout and contraints
		GridBagConstraints gbc = new GridBagConstraints();
		// @TODO use prettier background color
		//setBackground(backColor);
		setLayout(new GridBagLayout());
		gbc.weightx = 0.5;
		// @TODO standardize insets between panels
		gbc.insets = new Insets(10, 10, 10, 10);
		// instantiate components
		txtServerAddress = new JTextField("localhost", 20);
                txtServerAddress.selectAll();
		btnConnect = new JButton("Connect to Server");
		lblActionMsg = new JLabel();
		// add components
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(new JLabel("Server domain name or IP address"), gbc);
		gbc.gridy++;
		add(txtServerAddress, gbc);
		gbc.gridy++;
		add(btnConnect, gbc);
		gbc.gridy++;
		add(lblActionMsg, gbc);
		// register button listener
		btnConnect.addActionListener(this);
		// store reference to button action
		action = connectAction;
	}

	/** reset text field and action message text */
	public void reset() {
		txtServerAddress.setText("");
		setActionMsg("");
	}

	/** display specified action message, allowing HTML formatting */
	public void setActionMsg(String message) {
		lblActionMsg.setText("<html>" + message + "</html>");
	}

	/** display specified action message in red text, allowing HTML formatting */
	public void setActionError(String message) {
		lblActionMsg.setText("<html><span style='color: red'>" + message + "</span></html>");
	}

	/** when button clicked, validate sign in info and notify parent action listener */
	public void actionPerformed(ActionEvent ae) {
		// validate entered server address
		if (txtServerAddress.getText().isEmpty()) {
			setActionError("Please enter a server address");
			return;
		}
		// trigger new action event
		setActionMsg("Connecting to server...");
		action.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, txtServerAddress.getText()));
	}
}
