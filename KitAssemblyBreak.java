import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class KitAssemblyBreak extends JPanel implements ActionListener{


	private JRadioButton rbtDrop,rbtBreakPartRobot,rbtFixPartRobot,rbtBreakKitRobot,rbtFixKitRobot,rbtFixKitDeli,rbtBreakKitDeli;
	private static final long serialVersionUID = 1L;
	private ButtonGroup partButtonGroup,kitButtonGroup,kitDeliButtonGroup;
	private JButton btnDropParts;
	private GridBagConstraints c = new GridBagConstraints();
	private NetComm netcomm;
	private KitAssemblyClient client;
	/**
	 * 
	 */
	public KitAssemblyBreak(KitAssemblyClient kac){
		client = kac;
		//buttons for drop parts
		setLayout(new GridBagLayout());
		btnDropParts = new JButton("Drop Parts in Part Robot Grippers");
		//group buttons
		rbtBreakPartRobot = new JRadioButton("Break Part Robot");
		rbtFixPartRobot = new JRadioButton("Fix Part Robot");
		rbtBreakKitRobot = new JRadioButton("Break Kit Robot");
		rbtFixKitRobot = new JRadioButton("Fix Kit Robot");
		rbtFixKitDeli = new JRadioButton("Fix Kit Delivery Station");
		rbtBreakKitDeli = new JRadioButton("Break Kit Delivery Station");
		kitButtonGroup = new ButtonGroup();
		kitButtonGroup.add(rbtBreakKitRobot);
		kitButtonGroup.add(rbtFixKitRobot);
		partButtonGroup = new ButtonGroup();
		partButtonGroup.add(rbtBreakPartRobot);
		partButtonGroup.add(rbtFixPartRobot);
		kitDeliButtonGroup = new ButtonGroup();
		kitDeliButtonGroup.add(rbtBreakKitDeli);
		kitDeliButtonGroup.add(rbtFixKitDeli);
		//layout
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(20,10,0,0);
		c.weightx = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.gridx = 0;
		c.gridy = 0;
		add(rbtBreakPartRobot,c);
		c.gridx = 1;
		c.gridy = 0;
		add(rbtFixPartRobot,c);
		c.gridx = 0;
		c.gridy = 1;
		add(rbtBreakKitRobot,c);
		c.gridx = 1;
		c.gridy = 1;
		add(rbtFixKitRobot,c);
		c.gridx = 0;
		c.gridy = 2;
		add(rbtBreakKitDeli,c);
		c.gridx = 1;
		c.gridy = 2;
		add(rbtFixKitDeli,c);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		add(btnDropParts,c);
		btnDropParts.addActionListener(this);
		rbtBreakPartRobot.addActionListener(this);
		rbtFixPartRobot.addActionListener(this);
		rbtBreakKitRobot.addActionListener(this);
		rbtFixKitRobot.addActionListener(this);
		rbtFixKitDeli.addActionListener(this);
		rbtBreakKitDeli.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnDropParts){
			client.getNetComm().write(new NonNormativeMsg( NonNormativeMsg.ItemEnum.PART_ROBOT, 0, NonNormativeMsg.CmdEnum.DROP_PART));
		}
		if(e.getSource() == rbtBreakPartRobot){
			client.getNetComm().write(new NonNormativeMsg( NonNormativeMsg.ItemEnum.PART_ROBOT, 0, NonNormativeMsg.CmdEnum.BREAK));
		}
		if(e.getSource() == rbtFixPartRobot){
			client.getNetComm().write(new NonNormativeMsg( NonNormativeMsg.ItemEnum.PART_ROBOT, 0, NonNormativeMsg.CmdEnum.FIX));
		}
		if(e.getSource() == rbtBreakKitRobot){
			client.getNetComm().write(new NonNormativeMsg( NonNormativeMsg.ItemEnum.KIT_ROBOT, 0, NonNormativeMsg.CmdEnum.BREAK));
		}
		if(e.getSource() == rbtFixKitRobot){
			client.getNetComm().write(new NonNormativeMsg( NonNormativeMsg.ItemEnum.KIT_ROBOT, 0, NonNormativeMsg.CmdEnum.FIX));
		}
		if(e.getSource() == rbtFixKitDeli){
			client.getNetComm().write(new NonNormativeMsg( NonNormativeMsg.ItemEnum.KIT_DELIV, 0, NonNormativeMsg.CmdEnum.FIX));
		}
		if(e.getSource() == rbtBreakKitDeli){
			client.getNetComm().write(new NonNormativeMsg( NonNormativeMsg.ItemEnum.KIT_DELIV, 0, NonNormativeMsg.CmdEnum.BREAK));
		}
	}

}
