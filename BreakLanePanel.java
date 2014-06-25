import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class BreakLanePanel extends JPanel {
	private LanesClient myClient;

	private JLabel jamLane1;
	private JLabel jamLane2;
	private JLabel jamLane3;
	private JLabel jamLane4;
	private JLabel lane1PartJumps;
	private JLabel lane2PartJumps;
	private JLabel lane3PartJumps;
	private JLabel lane4PartJumps;
	private JRadioButton jamsLane1;
	private JRadioButton unjamsLane1;
	private JRadioButton jamsLane2;
	private JRadioButton unjamsLane2;
	private JRadioButton jamsLane3;
	private JRadioButton unjamsLane3;
	private JRadioButton jamsLane4;
	private JRadioButton unjamsLane4;
	private ButtonGroup jammer1;
	private ButtonGroup jammer2;
	private ButtonGroup jammer3;
	private ButtonGroup jammer4;
	private JButton partJumper1;
	private JButton partJumper2;
	private JButton partJumper3;
	private JButton partJumper4;
	
	
	
	/** Initialize */
	public BreakLanePanel( LanesClient lc ) {
		myClient = lc;
		
		jamLane1 = new JLabel( "Jam Lane 1" );		
		jamLane2 = new JLabel( "Jam Lane 2" );
		jamLane3 = new JLabel( "Jam Lane 3" );		
		jamLane4 = new JLabel( "Jam Lane 4" );
		jamsLane1 = new JRadioButton( "On" );
		jamsLane2 = new JRadioButton( "On" );
		jamsLane3 = new JRadioButton( "On" );
		jamsLane4 = new JRadioButton( "On" );
		unjamsLane1 = new JRadioButton( "Off" );
		unjamsLane2 = new JRadioButton( "Off" );
		unjamsLane3 = new JRadioButton( "Off" );
		unjamsLane4 = new JRadioButton( "Off" );
		unjamsLane1.setSelected( true );
		unjamsLane2.setSelected( true );
		unjamsLane3.setSelected( true );
		unjamsLane4.setSelected( true );
		jammer1 = new ButtonGroup();
		jammer2 = new ButtonGroup();
		jammer3 = new ButtonGroup();
		jammer4 = new ButtonGroup();
		jammer1.add( jamsLane1 );
		jammer1.add( unjamsLane1 );
		jammer2.add( jamsLane2 );
		jammer2.add( unjamsLane2 );
		jammer3.add( jamsLane3 );
		jammer3.add( unjamsLane3 );
		jammer4.add( jamsLane4 );
		jammer4.add( unjamsLane4 );
		lane1PartJumps = new JLabel( "Makes a part on lane1 switch lanes: " );
		lane2PartJumps = new JLabel( "Makes a part on lane2 switch lanes: " );
		lane3PartJumps = new JLabel( "Makes a part on lane3 switch lanes: " );
		lane4PartJumps = new JLabel( "Makes a part on lane4 switch lanes: " );
		partJumper1 = new JButton( "JUMP" );
		partJumper2 = new JButton( "JUMP" );
		partJumper3 = new JButton( "JUMP" );
		partJumper4 = new JButton( "JUMP" );
		
		//layout
		setLayout( new GridBagLayout() );
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 0;
		
		add(jamLane1, c);
		
		c.gridx = 1;
		add(jamsLane1, c);
		
		c.gridx = 2;
		add(unjamsLane1, c);
		
		c.gridx = 0;
		c.gridy = 1;
		
		add(jamLane2, c);
		
		c.gridx = 1;
		add(jamsLane2, c);
		
		c.gridx = 2;
		add(unjamsLane2, c);
		
		c.gridx = 0;
		c.gridy = 2;
		
		add(jamLane3, c);
		
		c.gridx = 1;
		add(jamsLane3, c);
		
		c.gridx = 2;
		add(unjamsLane3, c);
		
		c.gridx = 0;
		c.gridy = 3;
		
		add(jamLane4, c);
		
		c.gridx = 1;
		add(jamsLane4, c);
		
		c.gridx = 2;
		add(unjamsLane4, c);
		
		c.gridx = 0;
		c.gridy = 4;
		add(lane1PartJumps, c);
		
		c.gridx = 2;
		add(partJumper1, c);
		
		c.gridx = 0;
		c.gridy = 5;
		add(lane2PartJumps, c);
		
		c.gridx = 2;
		add(partJumper2, c);
		
		c.gridx = 0;
		c.gridy = 6;
		add(lane3PartJumps, c);
		
		c.gridx = 2;
		add(partJumper3, c);
		
		c.gridx = 0;
		c.gridy = 7;
		add(lane4PartJumps, c);
		
		c.gridx = 2;
		add(partJumper4, c);
		
		//TODO: make the buttons actually work
		//action listeners
		jamsLane1.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 0, NonNormativeMsg.CmdEnum.BREAK ) );
			}
		});
		
		unjamsLane1.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 0, NonNormativeMsg.CmdEnum.FIX ) );
			}
		});
		
		jamsLane2.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 1, NonNormativeMsg.CmdEnum.BREAK ) );
			}
		});
		
		unjamsLane2.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 1, NonNormativeMsg.CmdEnum.FIX ) );
			}
		});
		
		jamsLane3.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 2, NonNormativeMsg.CmdEnum.BREAK ) );
			}
		});
		
		unjamsLane3.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 2, NonNormativeMsg.CmdEnum.FIX ) );
			}
		});
		
		jamsLane4.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 3, NonNormativeMsg.CmdEnum.BREAK ) );
			}
		});
		
		unjamsLane4.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 3, NonNormativeMsg.CmdEnum.FIX ) );
			}
		});
		
		partJumper1.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 0, NonNormativeMsg.CmdEnum.JUMP_LANE ) );
			}
		});
		
		partJumper2.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 1, NonNormativeMsg.CmdEnum.JUMP_LANE ) );
			}
		});
		
		partJumper3.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 2, NonNormativeMsg.CmdEnum.JUMP_LANE ) );
			}
		});
		
		partJumper4.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ){
				myClient.getCom().write( new NonNormativeMsg( NonNormativeMsg.ItemEnum.LANE, 3, NonNormativeMsg.CmdEnum.JUMP_LANE ) );
			}
		});
	}
}
