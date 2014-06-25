import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class is the control panel inside FactoryControlManager
 * that controls all the Feeder devices
 *
 */
@SuppressWarnings("serial")
public class FeederControlPanel extends JPanel implements ActionListener {
		FactoryControlManager fcm;
		Dimension feederNumberLabelSize;
		JPanel feederTitleLabelPanel;
		JLabel feederTitleLabel;
		ArrayList<JPanel> feederPanels, feederHeaderPanels,feederNumberLabelPanels, partsLowLabelPanels, controlPanels, feederOnOffPanels, diverterPanels, feedPartsPanels, rearGatePanels, partsFedPanels;
		ArrayList<JLabel> feederNumberLabels, partsLowLabels, feederOnOffLabels, diverterLabels, feedPartsLabels, rearGateLabels, partsFedLabels, partsFedNumberLabels;
		ArrayList<JRadioButton> feederOnRadioButtons, feederOffRadioButtons, diverterTopRadioButtons, diverterBottomRadioButtons, feedPartsOnRadioButtons, feedPartsOffRadioButtons, rearGateRaisedRadioButtons, rearGateLoweredRadioButtons;
		ArrayList<ButtonGroup> feederOnOffRadioButtonGroups, diverterRadioButtonGroups, feedPartsRadioButtonGroups, rearGateRadioButtonGroups;
		int feederNumber;
		Timer updatePartLowAndCount;
		
		/**
		 * Constructor; sets layout for panel
		 * 
		 * @param fcm pointer to FactoryControlManager object
		 */
		public FeederControlPanel( FactoryControlManager fcm ) {
			this.fcm = fcm;
			
			//Dimensions
			feederNumberLabelSize = new Dimension( 20, 20 );
			
			//Timers
			updatePartLowAndCount = new Timer( 1000, this );
			updatePartLowAndCount.start();
			
			//JPanels
			feederTitleLabelPanel = new JPanel();
			feederPanels = new ArrayList<JPanel>();
			feederHeaderPanels = new ArrayList<JPanel>();
			feederNumberLabelPanels = new ArrayList<JPanel>();
			partsLowLabelPanels = new ArrayList<JPanel>();
			controlPanels = new ArrayList<JPanel>();
			feederOnOffPanels = new ArrayList<JPanel>();
			diverterPanels = new ArrayList<JPanel>();
			feedPartsPanels = new ArrayList<JPanel>();
			rearGatePanels = new ArrayList<JPanel>();
			partsFedPanels = new ArrayList<JPanel>();
			
			//JLabels
			feederTitleLabel = new JLabel();
			feederTitleLabel.setText( "Feeders" );
			feederTitleLabel.setFont( new Font( "Serif", Font.BOLD, 24 ) );
			feederNumberLabels = new ArrayList<JLabel>();
			partsLowLabels = new ArrayList<JLabel>();
			feederOnOffLabels = new ArrayList<JLabel>();
			diverterLabels = new ArrayList<JLabel>();
			feedPartsLabels = new ArrayList<JLabel>();
			rearGateLabels = new ArrayList<JLabel>();
			partsFedLabels = new ArrayList<JLabel>();
			partsFedNumberLabels = new ArrayList<JLabel>();
			for ( int i = 0; i < 4; i++ ) {
				feederNumberLabels.add( new JLabel() );
				feederNumberLabels.get( i ).setText( "" + ( i + 1 ) );
				feederNumberLabels.get( i ).setPreferredSize( feederNumberLabelSize );
				feederNumberLabels.get( i ).setMaximumSize( feederNumberLabelSize );
				feederNumberLabels.get( i ).setMinimumSize( feederNumberLabelSize );
				feederNumberLabels.get( i ).setVerticalAlignment( JLabel.TOP );
				feederNumberLabels.get( i ).setHorizontalAlignment( JLabel.CENTER );
				
				partsLowLabels.add( new JLabel() );
				partsLowLabels.get( i ).setForeground( Color.red );
				
				feederOnOffLabels.add( new JLabel() );
				feederOnOffLabels.get( i ).setText( "Feeder On/Off" );
				
				diverterLabels.add( new JLabel() );
				diverterLabels.get( i ).setText( "Diverter" );
				
				feedPartsLabels.add( new JLabel() );
				feedPartsLabels.get( i ).setText( "Feed Parts" );
				
				rearGateLabels.add( new JLabel() );
				rearGateLabels.get( i ).setText( "Rear Gate" );
				
				partsFedLabels.add( new JLabel() );
				partsFedLabels.get( i ).setText( "Parts Fed" );
				
				partsFedNumberLabels.add( new JLabel() );
				partsFedNumberLabels.get( i ).setText( "0" );
				partsFedNumberLabels.get( i ).setFont( new Font( "Serif", Font.BOLD, 18 ) );	
			}
			
			//JRadioButtons
			feederOnRadioButtons = new ArrayList<JRadioButton>();
			feederOffRadioButtons = new ArrayList<JRadioButton>();
			diverterTopRadioButtons = new ArrayList<JRadioButton>();
			diverterBottomRadioButtons = new ArrayList<JRadioButton>();
			feedPartsOnRadioButtons = new ArrayList<JRadioButton>();
			feedPartsOffRadioButtons = new ArrayList<JRadioButton>();
			rearGateRaisedRadioButtons = new ArrayList<JRadioButton>();
			rearGateLoweredRadioButtons = new ArrayList<JRadioButton>();
			for( int i = 0; i < 4; i++ ) {
				feederOnRadioButtons.add( new JRadioButton() );
				feederOnRadioButtons.get( i ).setText( "On" );
				feederOnRadioButtons.get( i ).addActionListener( this );
				feederOnRadioButtons.get( i ).setActionCommand( "feeder_on" );
				feederOffRadioButtons.add( new JRadioButton() );
				feederOffRadioButtons.get( i ).setText( "Off" );
				feederOffRadioButtons.get( i ).addActionListener( this );
				feederOffRadioButtons.get( i ).setActionCommand( "feeder_off" );
				
				diverterTopRadioButtons.add( new JRadioButton() );
				diverterTopRadioButtons.get( i ).setText( "Top" );
				diverterTopRadioButtons.get( i ).addActionListener( this );
				diverterTopRadioButtons.get( i ).setActionCommand( "diverter_top" );
				diverterBottomRadioButtons.add( new JRadioButton() );
				diverterBottomRadioButtons.get( i ).setText( "Bottom" );
				diverterBottomRadioButtons.get( i ).addActionListener( this );
				diverterBottomRadioButtons.get( i ).setActionCommand( "diverter_bottom" );
				
				feedPartsOnRadioButtons.add( new JRadioButton() );
				feedPartsOnRadioButtons.get( i ).setText( "On" );
				feedPartsOnRadioButtons.get( i ).addActionListener( this );
				feedPartsOnRadioButtons.get( i ).setActionCommand( "feed_parts_on" );
				feedPartsOffRadioButtons.add( new JRadioButton() );
				feedPartsOffRadioButtons.get( i ).setText( "Off" );
				feedPartsOffRadioButtons.get( i ).addActionListener( this );
				feedPartsOffRadioButtons.get( i ).setActionCommand( "feed_parts_off" );
				
				rearGateRaisedRadioButtons.add( new JRadioButton() );
				rearGateRaisedRadioButtons.get( i ).setText( "Raised" );
				rearGateRaisedRadioButtons.get( i ).addActionListener( this );
				rearGateRaisedRadioButtons.get( i ).setActionCommand( "rear_gate_raised" );
				rearGateLoweredRadioButtons.add( new JRadioButton() );
				rearGateLoweredRadioButtons.get( i ).setText( "Lowered" );
				rearGateLoweredRadioButtons.get( i ).addActionListener( this );
				rearGateLoweredRadioButtons.get( i ).setActionCommand( "rear_gate_lowered" );
			}
			
			//ButtonGroups
			feederOnOffRadioButtonGroups = new ArrayList<ButtonGroup>();
			diverterRadioButtonGroups = new ArrayList<ButtonGroup>();
			feedPartsRadioButtonGroups = new ArrayList<ButtonGroup>();
			rearGateRadioButtonGroups = new ArrayList<ButtonGroup>();
			for( int i = 0; i < 4; i++ ) {
				feederOnOffRadioButtonGroups.add( new ButtonGroup() );
				feederOnOffRadioButtonGroups.get( i ).add( feederOnRadioButtons.get( i ) );
				feederOnOffRadioButtonGroups.get( i ).add( feederOffRadioButtons.get( i ) );
				
				diverterRadioButtonGroups.add( new ButtonGroup() );
				diverterRadioButtonGroups.get( i ).add( diverterTopRadioButtons.get( i ) );
				diverterRadioButtonGroups.get( i ).add( diverterBottomRadioButtons.get( i ) );
				
				feedPartsRadioButtonGroups.add( new ButtonGroup() );
				feedPartsRadioButtonGroups.get( i ).add( feedPartsOnRadioButtons.get( i ) );
				feedPartsRadioButtonGroups.get( i ).add( feedPartsOffRadioButtons.get( i ) );
				
				rearGateRadioButtonGroups.add( new ButtonGroup() );
				rearGateRadioButtonGroups.get( i ).add( rearGateRaisedRadioButtons.get( i ) );
				rearGateRadioButtonGroups.get( i ).add( rearGateLoweredRadioButtons.get( i ) );				
			}
			
			//Layout
			feederTitleLabelPanel.setLayout( new BoxLayout( feederTitleLabelPanel, BoxLayout.X_AXIS ) );
			feederTitleLabelPanel.add( Box.createGlue() );
			feederTitleLabelPanel.add( feederTitleLabel );
			feederTitleLabelPanel.add( Box.createGlue() );
			
			for( int i = 0; i < 4; i ++ ) {
				feederNumberLabelPanels.add( new JPanel() );
				feederNumberLabelPanels.get( i ).setPreferredSize( feederNumberLabelSize );
				feederNumberLabelPanels.get( i ).setMaximumSize( feederNumberLabelSize );
				feederNumberLabelPanels.get( i ).setMinimumSize( feederNumberLabelSize );
				feederNumberLabelPanels.get( i ).add( feederNumberLabels.get( i ) );
				
				partsLowLabelPanels.add( new JPanel() );
				partsLowLabelPanels.get( i ).setLayout( new BoxLayout( partsLowLabelPanels.get( i ), BoxLayout.X_AXIS ) );
				partsLowLabelPanels.get( i ).add( Box.createGlue() );
				partsLowLabelPanels.get( i ).add( partsLowLabels.get( i ) );
				partsLowLabelPanels.get( i ).add( Box.createGlue() );
				
				feederHeaderPanels.add( new JPanel() );
				feederHeaderPanels.get( i ).setLayout( new BoxLayout( feederHeaderPanels.get( i ), BoxLayout.X_AXIS ) );
				feederHeaderPanels.get( i ).setBorder( BorderFactory.createLineBorder( Color.black ) );
				feederHeaderPanels.get( i ).add( feederNumberLabelPanels.get( i ) );
				feederHeaderPanels.get( i ).add( partsLowLabelPanels.get( i ) );
				
				feederOnOffPanels.add( new JPanel() );
				feederOnOffPanels.get( i ).setLayout( new BoxLayout( feederOnOffPanels.get( i ), BoxLayout.Y_AXIS ) );
				feederOnOffPanels.get( i ).add( feederOnOffLabels.get( i ) );
				feederOnOffPanels.get( i ).add( Box.createGlue() );
				feederOnOffPanels.get( i ).add( feederOnRadioButtons.get( i ) );
				feederOnOffPanels.get( i ).add( feederOffRadioButtons.get( i ) );
				feederOnOffPanels.get( i ).add( Box.createGlue() );
				
				diverterPanels.add( new JPanel() );
				diverterPanels.get( i ).setLayout( new BoxLayout( diverterPanels.get( i ), BoxLayout.Y_AXIS ) );
				diverterPanels.get( i ).add( diverterLabels.get( i ) );
				diverterPanels.get( i ).add( Box.createGlue() );
				diverterPanels.get( i ).add( diverterTopRadioButtons.get( i ) );
				diverterPanels.get( i ).add( diverterBottomRadioButtons.get( i ) );
				diverterPanels.get( i ).add( Box.createGlue() );
				
				feedPartsPanels.add( new JPanel() );
				feedPartsPanels.get( i ).setLayout( new BoxLayout( feedPartsPanels.get( i ), BoxLayout.Y_AXIS ) );
				feedPartsPanels.get( i ).add( feedPartsLabels.get( i ) );
				feedPartsPanels.get( i ).add( Box.createGlue() );
				feedPartsPanels.get( i ).add( feedPartsOnRadioButtons.get( i ) );
				feedPartsPanels.get( i ).add( feedPartsOffRadioButtons.get( i ) );
				feedPartsPanels.get( i ).add( Box.createGlue() );
				
				rearGatePanels.add( new JPanel() );
				rearGatePanels.get( i ).setLayout( new BoxLayout( rearGatePanels.get( i ), BoxLayout.Y_AXIS ) );
				rearGatePanels.get( i ).add( rearGateLabels.get( i ) );
				rearGatePanels.get( i ).add( Box.createGlue() );
				rearGatePanels.get( i ).add( rearGateRaisedRadioButtons.get( i ) );
				rearGatePanels.get( i ).add( rearGateLoweredRadioButtons.get( i ) );
				rearGatePanels.get( i ).add( Box.createGlue() );
				
				partsFedPanels.add( new JPanel() );
				partsFedPanels.get( i ).setLayout( new BoxLayout( partsFedPanels.get( i ), BoxLayout.Y_AXIS ) );
				partsFedPanels.get( i ).setBorder( BorderFactory.createLineBorder( Color.black ) );
				partsFedPanels.get( i ).add( partsFedLabels.get( i ) );
				partsFedPanels.get( i ).add( Box.createVerticalStrut( 15 ) );
				partsFedPanels.get( i ).add( partsFedNumberLabels.get( i ) );
				partsFedPanels.get( i ).add( Box.createGlue() );
				
				controlPanels.add( new JPanel() );
				controlPanels.get( i ).setLayout( new BoxLayout( controlPanels.get( i ), BoxLayout.X_AXIS ) );
				controlPanels.get( i ).setBorder( BorderFactory.createLineBorder( Color.black ) );
				controlPanels.get( i ).add( Box.createGlue() );
				controlPanels.get( i ).add( feederOnOffPanels.get( i ) );
				controlPanels.get( i ).add( Box.createGlue() );
				controlPanels.get( i ).add( diverterPanels.get( i ) );
				controlPanels.get( i ).add( Box.createGlue() );
				controlPanels.get( i ).add( feedPartsPanels.get( i ) );
				controlPanels.get( i ).add( Box.createGlue() );
				controlPanels.get( i ).add( rearGatePanels.get( i ) );
				controlPanels.get( i ).add( Box.createGlue() );
				controlPanels.get( i ).add( partsFedPanels.get( i ) );				
			}
			
			setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
			setBorder( BorderFactory.createLineBorder( Color.black ) );
			add( feederTitleLabelPanel );
			add( Box.createVerticalStrut( 15 ) );
			for ( int i = 0; i < 4; i++ ) {
				add( feederHeaderPanels.get( i ) );
				add( controlPanels.get( i ) );
			}
			
			//Initialize factory states
			for( int feederNumber = 0; feederNumber < 4; feederNumber++ ) {
				int key = fcm.server.feederIDs.get(feederNumber);
				Object stateObj = fcm.server.getState().items.get(key);
				if (stateObj instanceof GUIFeeder) {
					GUIFeeder feeder = (GUIFeeder)stateObj;
					setFeederOn( feeder.isOn(), feederNumber );
					setFeedPartsOn( feeder.isFeeding(), feederNumber );
					setRearGateRaised( feeder.isGateRaised(), feederNumber );
					if ( feeder.getDiverter() == -1 )
						setDiverterTop( true, feederNumber );
					else if ( feeder.getDiverter() == 1 )
						setDiverterTop( false, feederNumber );
					else
						System.out.println( "Invalid diverter position received" );
				}
			}
		}

		/**
		 * Sets the parts low message for a specified feeder
		 * 
		 * @param partsLow boolean variable 
		 * @param feederNumber specifies which feeder is low on parts
		 */
		public void setPartsLow ( boolean partsLow, int feederNumber ) {
			if ( partsLow )
				partsLowLabels.get( feederNumber ).setText( "Parts Low" );
			else
				partsLowLabels.get( feederNumber ).setText( "" );
		}
		
		/**
		 * Updates the number of parts fed from the specified feeder
		 * 
		 * @param partsFed number of parts fed
		 * @param feederNumber specifies which feeder the "partsFed" number corresponds to
		 */
		public void setPartsFedCount ( int partsFed, int feederNumber ) {
			partsFedNumberLabels.get( feederNumber ).setText( "" + partsFed );
		}
		
		/**
		 * Turns the feeder on or off
		 * 
		 * @param on boolean variable if the feeder is on/off
		 * @param feederNumber specifies the feeder to control
		 */
		public void setFeederOn( boolean on, int feederNumber ) {
			feederOnRadioButtons.get( feederNumber ).setSelected( on );
			feederOffRadioButtons.get( feederNumber ).setSelected( !on );
		}
		
		/**
		 * Sets the direction of the diverter for a specified feeder. "right" would correspond to up
		 * as viewed from the factory manager graphics panels. The use of "right" and "left" is to fulfill
		 * the original kitting cell description
		 * 
		 * @param setRight boolean variable specifying if the diverter should be moved to the right
		 * @param feederNumber specifies which feeder's diverter should be controlled
		 */
		public void setDiverterTop( boolean setTop, int feederNumber ) {
			diverterTopRadioButtons.get( feederNumber ).setSelected( setTop );
			diverterBottomRadioButtons.get( feederNumber ).setSelected( !setTop );
		}
		
		/**
		 * Sets a feeding of parts on/off
		 * 
		 * @param on boolean variable if the feeder is feeding or not feeding
		 * @param feederNumber specifies the feeder to control
		 */
		public void setFeedPartsOn( boolean on, int feederNumber ) {
			feedPartsOnRadioButtons.get( feederNumber ).setSelected( on );
			feedPartsOffRadioButtons.get( feederNumber ).setSelected( !on );
		}
		
		/**
		 * Sets the rear gate to raised/lowered
		 * 
		 * @param raised boolean variable, true if rear gate is raised
		 * @param feederNumber specifies the feeder to control
		 */
		public void setRearGateRaised( boolean raised, int feederNumber ) {
			rearGateRaisedRadioButtons.get( feederNumber ).setSelected( raised );
			rearGateLoweredRadioButtons.get( feederNumber ).setSelected( !raised );
		}
		
		/**
		 * Gives functionality to all the JRadioButtons in the panel
		 */
		public void actionPerformed( ActionEvent ae ) {
			//each "if" or "if else" statement checks for the command origination button
			
			if( ae.getSource() == updatePartLowAndCount ) {
				for( int feederNumber = 0; feederNumber < 4; feederNumber++ ) {
					// get entry corresponding to this feeder
					int key = fcm.server.feederIDs.get( feederNumber );
					Object stateObj = fcm.server.getState().items.get(key);
					if (stateObj instanceof GUIFeeder) {
						GUIFeeder feeder = (GUIFeeder)stateObj;
						setPartsLow( feeder.getPartsLow(), feederNumber );
						setPartsFedCount( feeder.partsFed(), feederNumber );
					}
					else {
						System.out.println("Error: feeder index variable does not point to a feeder");
					}
				}
			}
			
			else if ( ae.getActionCommand().equals( "feeder_on" ) ) {
				for( int i = 0; i < feederOnRadioButtons.size(); i++ ) {
					if ( ae.getSource() == feederOnRadioButtons.get( i ) ){
						feederNumber = i;
						// get entry corresponding to this feeder
						int key = fcm.server.feederIDs.get(feederNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUIFeeder) {
							GUIFeeder feeder = (GUIFeeder)stateObj;
							if ( !feeder.isOn() ) { // only turn on feeder if feeder is off
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								feeder.turnOn(); // turn on feeder
								update.putItems.put(key,feeder); // put updated lane in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: feeder index variable does not point to a feeder" );
						}
						return; // no need to check if other buttons selected
					}
				}
			}
			
			else if ( ae.getActionCommand().equals( "feeder_off" ) ) {
				for( int i = 0; i < feederOffRadioButtons.size(); i++ ) {
					if ( ae.getSource() == feederOffRadioButtons.get( i ) ){
						feederNumber = i;
						// get entry corresponding to this feeder
						int key = fcm.server.feederIDs.get(feederNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUIFeeder) {
							GUIFeeder feeder = (GUIFeeder)stateObj;
							if ( feeder.isOn() ) { // only turn off feeder if feeder is on
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								feeder.turnOff(); // turn off feeder
								update.putItems.put(key,feeder); // put updated lane in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: feeder index variable does not point to a feeder" );
						}
						return; // no need to check if other buttons selected
					}
				}
			}
			
			else if ( ae.getActionCommand().equals( "diverter_top" ) ) {
				for( int i = 0; i < diverterTopRadioButtons.size(); i++ ) {
					if ( ae.getSource() == diverterTopRadioButtons.get( i ) ) {
						feederNumber = i;
						// get entry corresponding to this feeder
						int fKey = fcm.server.feederIDs.get(feederNumber);
						GUIFeeder feeder = fcm.server.getFeeder(feederNumber);
						// get entry corresponding to this diverter arm
						int dKey = fcm.server.diverterArmIDs.get( feederNumber );
						GUIDiverterArm diverterArm = fcm.server.getDiverterArm(feederNumber);
						// prepare factory update message
						FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
						feeder.setDiverter(-1);
						update.putItems.put(fKey, feeder);
						update.itemMoves.put(dKey, diverterArm.calcMove(update.timeElapsed, GUIDiverterArm.TOP));
						fcm.server.applyUpdate(update); // apply and broadcast update message
						return; // no need to check if other buttons selected
					}
				}
			}
			else if ( ae.getActionCommand().equals( "diverter_bottom" ) ) {
				for( int i = 0; i < diverterBottomRadioButtons.size(); i++ ) {
					if ( ae.getSource() == diverterBottomRadioButtons.get( i ) ){
						feederNumber = i;
						// get entry corresponding to this feeder
						int fKey = fcm.server.feederIDs.get(feederNumber);
						GUIFeeder feeder = fcm.server.getFeeder(feederNumber);
						// get entry corresponding to this diverter arm
						int dKey = fcm.server.diverterArmIDs.get( feederNumber );
						GUIDiverterArm diverterArm = fcm.server.getDiverterArm(feederNumber);
						// prepare factory update message
						FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
						feeder.setDiverter(1);
						update.putItems.put(fKey, feeder);
						update.itemMoves.put(dKey, diverterArm.calcMove(update.timeElapsed, GUIDiverterArm.BOTTOM));
						fcm.server.applyUpdate(update); // apply and broadcast update message
						return; // no need to check if other buttons selected
					}
				}
			}
			else if ( ae.getActionCommand().equals( "feed_parts_on" ) ) {
				for( int i = 0; i < feedPartsOnRadioButtons.size(); i++ ) {
					if ( ae.getSource() == feedPartsOnRadioButtons.get( i ) ){
						feederNumber = i;
						// get entry corresponding to this feeder
						int key = fcm.server.feederIDs.get(feederNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUIFeeder) {
							GUIFeeder feeder = (GUIFeeder)stateObj;
							if ( !feeder.isFeeding() ) { // only start feeding if feeder is not feeding
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								feeder.setFeeding( true ); // start feeding
								update.putItems.put(key,feeder); // put updated lane in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: feeder index variable does not point to a feeder" );
						}
						return; // no need to check if other buttons selected
					}
				}
			}
			else if ( ae.getActionCommand().equals( "feed_parts_off" ) ) {
				for( int i = 0; i < feedPartsOffRadioButtons.size(); i++ ) {
					if ( ae.getSource() == feedPartsOffRadioButtons.get( i ) ){
						feederNumber = i;
						// get entry corresponding to this feeder
						int key = fcm.server.feederIDs.get(feederNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUIFeeder) {
							GUIFeeder feeder = (GUIFeeder)stateObj;
							if ( feeder.isFeeding() ) { // only stop feeding if feeder is feeding
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								feeder.setFeeding( false ); // stop feeding
								update.putItems.put(key,feeder); // put updated lane in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: feeder index variable does not point to a feeder" );
						}
						return; // no need to check if other buttons selected
					}
				}
			}
			else if ( ae.getActionCommand().equals( "rear_gate_raised" ) ) {
				for( int i = 0; i < rearGateRaisedRadioButtons.size(); i++ ) {
					if ( ae.getSource() == rearGateRaisedRadioButtons.get( i ) ){
						feederNumber = i;
						// get entry corresponding to this feeder
						int key = fcm.server.feederIDs.get(feederNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUIFeeder) {
							GUIFeeder feeder = (GUIFeeder)stateObj;
							if ( !feeder.isGateRaised() ) { // only raise gate if gate is lowered
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								feeder.setGateRaised( true ); // raise gate
								update.putItems.put(key,feeder); // put updated lane in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: feeder index variable does not point to a feeder" );
						}
						return; // no need to check if other buttons selected
					}
				}
			}
			else if ( ae.getActionCommand().equals( "rear_gate_lowered" ) ) {
				for( int i = 0; i < rearGateLoweredRadioButtons.size(); i++ ) {
					if ( ae.getSource() == rearGateLoweredRadioButtons.get( i ) ){
						feederNumber = i;
						// get entry corresponding to this feeder
						int key = fcm.server.feederIDs.get(feederNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUIFeeder) {
							GUIFeeder feeder = (GUIFeeder)stateObj;
							if ( feeder.isGateRaised() ) { // only lower gate if gate is raised
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								feeder.setGateRaised( false ); // lower gate (this automatically purges feeder)
								
								update.putItems.put(key,feeder); // put updated lane in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: feeder index variable does not point to a feeder" );
						}
						return; // no need to check if other buttons selected
					}
				}
			}	
		}
	}
