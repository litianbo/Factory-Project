import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class is the control panel inside FactoryControlManager
 * that controls the Kit Robot device
 *
 */
@SuppressWarnings("serial")
public class KitRobotControlPanel extends JPanel implements ActionListener {
		FactoryControlManager fcm;
		ImageIcon kitRobotImage, kitStandImage, kitDeliveryStationImage;
		JPanel kitRobotLabelPanel, kitRobotImageLabelPanel, robotOnOffButtonPanel, dropOffPickUpButtonPanel, instructionPanel;
		JPanel posButtonPanel, blankPanel1, blankPanel2, pictureConfirmationPanel, cameraPanel, lightKeyPanel, redLightDescPanel, yellowLightDescPanel, greenLightDescPanel, kitPanel;
		JLabel kitRobotLabel, takePictureLabel, kitStatusLabel, kitRobotImageLabel, kitStandImageLabel, kitDeliveryStationImageLabel, redColorLabel, yellowColorLabel, greenColorLabel;
		JLabel redLightDescLabel, yellowLightDescLabel, greenLightDescLabel, instructionsLabel;
		JButton dropOffButton, pickUpButton, takePictureButton;	
		JRadioButton kitRobotOnButton, kitRobotOffButton;
		ButtonGroup onOffButtonGroup;
		Dimension posButtonSize, blankPanel1Size, blankPanel2Size, takePictureButtonSize, pictureConfirmationPanelSize, instructionPanelSize;
		ArrayList<JLabel> lightKeyColors;
		ArrayList<JButton> kitStandPositionButtons;
		ArrayList<ImageIcon> pictureConfirmationColors;
		Timer cameraLightTimer;
		boolean firstButtonSelected = false; // tracks if the user has already made a source selection, i.e. the next button selected will be the destination
		
		/**
		 * Constructor; sets layout for panel
		 * 
		 * @param fcm pointer to FactoryControlManager object
		 */
		public KitRobotControlPanel( FactoryControlManager fcm ) {
			this.fcm = fcm;
			
			//ImageIcons
			kitRobotImage = new ImageIcon( "images/guiserver_thumbs/kitRobot_thumb.png" );
			kitStandImage = new ImageIcon( "images/guiserver_thumbs/kit_table_thumb.png" );
			kitDeliveryStationImage = new ImageIcon( "images/guiserver_thumbs/kit_delivery_station_thumb.png" );
			pictureConfirmationColors = new ArrayList<ImageIcon>();
			for( int i = 0; i < 4; i++ ) {
				pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/red_kit.png" ) );
				pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/yellow_kit.png" ) );
				pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/green_kit.png" ) );
				pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/dark_red_kit.png" ) );
				pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/dark_yellow_kit.png" ) );
				pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/dark_green_kit.png" ) );
			}
			
			//Dimensions
			posButtonSize = new Dimension( 40, 40 );
			blankPanel1Size = new Dimension( 80, 300 );
			blankPanel2Size = new Dimension( 100, 100 );
			takePictureButtonSize = new Dimension( 40, 40 );
			pictureConfirmationPanelSize = new Dimension( 20, 40 );
			instructionPanelSize = new Dimension( 250, 35 );
			
			//JPanels
			kitRobotLabelPanel = new JPanel();
			kitRobotImageLabelPanel = new JPanel();
			robotOnOffButtonPanel = new JPanel();
			dropOffPickUpButtonPanel = new JPanel();
			posButtonPanel = new JPanel();
			blankPanel1 = new JPanel();
			blankPanel2 = new JPanel();
			pictureConfirmationPanel = new JPanel();
			cameraPanel = new JPanel();
			lightKeyPanel = new JPanel();
			redLightDescPanel = new JPanel();
			yellowLightDescPanel = new JPanel();
			greenLightDescPanel = new JPanel();
			instructionPanel = new JPanel();
			
			//JLabels
			kitRobotLabel = new JLabel();
			kitRobotLabel.setText( "Kit Robot" );
			kitRobotLabel.setFont( new Font( "Serif", Font.BOLD, 24 ) );
			takePictureLabel = new JLabel();
			takePictureLabel.setText( "<-- Click for Kit Inspection" );
			kitStatusLabel = new JLabel();
			kitRobotImageLabel = new JLabel();
			kitRobotImageLabel.setIcon( kitRobotImage );
			kitStandImageLabel = new JLabel();
			kitStandImageLabel.setIcon( kitStandImage );
			kitDeliveryStationImageLabel = new JLabel();
			kitDeliveryStationImageLabel.setIcon( kitDeliveryStationImage );
			redColorLabel = new JLabel();
			redColorLabel.setIcon( pictureConfirmationColors.get( 3 ) );
			yellowColorLabel = new JLabel();
			yellowColorLabel.setIcon( pictureConfirmationColors.get( 4 ) );
			greenColorLabel = new JLabel();
			greenColorLabel.setIcon( pictureConfirmationColors.get( 5 ) );
			redLightDescLabel = new JLabel();
			redLightDescLabel.setText( "Kit is incorrectly assembled" );
			yellowLightDescLabel = new JLabel();
			yellowLightDescLabel.setText( "Kit is incomplete" );
			greenLightDescLabel = new JLabel();
			greenLightDescLabel.setText( "Kit is correctly assembled" );
			lightKeyColors = new ArrayList<JLabel>();
			instructionsLabel = new JLabel();
			instructionsLabel.setText( "<html><body style=\"text-align:center;\">Select a source<br/>Then select a destination</body></html>" );
			for( int i = 0; i < 3; i++ ) {
				lightKeyColors.add( new JLabel() );
				lightKeyColors.get( i ).setIcon( pictureConfirmationColors.get( i ) );
			}
			
			//JButtons
			dropOffButton = new JButton();
			dropOffButton.setText( "Drop Off" );
			dropOffButton.setEnabled( false );
			dropOffButton.addActionListener( this );
			pickUpButton = new JButton();
			pickUpButton.setText( "Pick Up" );
			pickUpButton.addActionListener( this );
			takePictureButton = new JButton();
			takePictureButton.setPreferredSize( takePictureButtonSize );
			takePictureButton.setMaximumSize( takePictureButtonSize );
			takePictureButton.setMinimumSize( takePictureButtonSize );
			takePictureButton.setText( "<html><body style=\"text-align:center;\">Take<br/>Pic</body></html>" );
			takePictureButton.setMargin( new Insets( 1, 1, 1, 1 ) );
			takePictureButton.addActionListener( this );
			kitStandPositionButtons = new ArrayList<JButton>();
			for ( int i = 0; i < 3; i++ ) {
				kitStandPositionButtons.add( new JButton() );
				kitStandPositionButtons.get( i ).setText( "" + ( i + 1 ) );
				kitStandPositionButtons.get( i ).setPreferredSize( posButtonSize );
				kitStandPositionButtons.get( i ).setMaximumSize( posButtonSize );
				kitStandPositionButtons.get( i ).setMinimumSize( posButtonSize );
				kitStandPositionButtons.get( i ).setMargin( new Insets( 1, 1, 1, 1 ) );
				kitStandPositionButtons.get( i ).addActionListener( this );
			}
			
			//JRadioButtons
			kitRobotOnButton = new JRadioButton();
			kitRobotOnButton.setText( "ON" );
			kitRobotOnButton.addActionListener( this );
			kitRobotOffButton = new JRadioButton();
			kitRobotOffButton.setText( "OFF" );
			kitRobotOffButton.addActionListener( this );
			onOffButtonGroup = new ButtonGroup();
			onOffButtonGroup.add( kitRobotOnButton );
			onOffButtonGroup.add( kitRobotOffButton );
			
			//Timer
			cameraLightTimer = new Timer( 3000, this );
			cameraLightTimer.setRepeats( false );
			
			
			//Layout
			posButtonPanel.setLayout( new BoxLayout( posButtonPanel, BoxLayout.Y_AXIS ) );
			posButtonPanel.add( Box.createGlue() );
			for( JButton button : kitStandPositionButtons ) {
				posButtonPanel.add( button );
				posButtonPanel.add( Box.createGlue() );
			}
			
			kitRobotLabelPanel.setLayout( new BoxLayout( kitRobotLabelPanel, BoxLayout.X_AXIS ) );
			kitRobotLabelPanel.add( Box.createGlue() );
			kitRobotLabelPanel.add( kitRobotLabel );
			kitRobotLabelPanel.add( Box.createGlue() );
			
			kitRobotImageLabelPanel.add( kitRobotImageLabel );
			
			instructionPanel.setLayout( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
			instructionPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
			instructionPanel.setPreferredSize( instructionPanelSize );
			instructionPanel.setMaximumSize( instructionPanelSize );
			instructionPanel.setMinimumSize( instructionPanelSize );
			instructionPanel.add( instructionsLabel );
			
			redLightDescPanel.setLayout( new BoxLayout( redLightDescPanel, BoxLayout.X_AXIS ) );
			//redLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			redLightDescPanel.add( lightKeyColors.get( 0 ) );
			redLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			redLightDescPanel.add( redLightDescLabel );
			redLightDescPanel.add( Box.createGlue() );
			
			yellowLightDescPanel.setLayout( new BoxLayout( yellowLightDescPanel, BoxLayout.X_AXIS ) );
			//yellowLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			yellowLightDescPanel.add( lightKeyColors.get( 1 ) );
			yellowLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			yellowLightDescPanel.add( yellowLightDescLabel );
			yellowLightDescPanel.add( Box.createGlue() );
			
			greenLightDescPanel.setLayout( new BoxLayout( greenLightDescPanel, BoxLayout.X_AXIS ) );
			//greenLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			greenLightDescPanel.add( lightKeyColors.get( 2 ) );
			greenLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			greenLightDescPanel.add( greenLightDescLabel );
			greenLightDescPanel.add( Box.createGlue() );
			
			lightKeyPanel.setLayout( new BoxLayout( lightKeyPanel, BoxLayout.Y_AXIS ) );
			lightKeyPanel.add( Box.createVerticalStrut( 15 ) );
			lightKeyPanel.add( redLightDescPanel );
			lightKeyPanel.add( yellowLightDescPanel );
			lightKeyPanel.add( greenLightDescPanel );
			lightKeyPanel.add( Box.createVerticalStrut( 15 ) );
			
			pictureConfirmationPanel.setLayout( new BoxLayout( pictureConfirmationPanel, BoxLayout.Y_AXIS ) );
			pictureConfirmationPanel.add( redColorLabel );
			pictureConfirmationPanel.add( yellowColorLabel );
			pictureConfirmationPanel.add( greenColorLabel );
			
			cameraPanel.setLayout( new BoxLayout( cameraPanel, BoxLayout.X_AXIS ) );
			cameraPanel.add( takePictureButton );
			cameraPanel.add( pictureConfirmationPanel );
			
			blankPanel1.setPreferredSize( blankPanel1Size );
			blankPanel1.setMaximumSize( blankPanel1Size );
			blankPanel1.setMinimumSize( blankPanel1Size );
			
			blankPanel2.setPreferredSize( blankPanel2Size );
			blankPanel2.setMaximumSize( blankPanel2Size );
			blankPanel2.setMinimumSize( blankPanel2Size );
			
			robotOnOffButtonPanel.setLayout( new BoxLayout( robotOnOffButtonPanel, BoxLayout.X_AXIS ) );
			robotOnOffButtonPanel.add( Box.createGlue() );
			robotOnOffButtonPanel.add( kitRobotOnButton );
			robotOnOffButtonPanel.add(Box.createHorizontalStrut( 20 ) );
			robotOnOffButtonPanel.add( kitRobotOffButton );
			robotOnOffButtonPanel.add( Box.createGlue() );
			
			dropOffPickUpButtonPanel.setLayout( new BoxLayout( dropOffPickUpButtonPanel, BoxLayout.X_AXIS ) );
			dropOffPickUpButtonPanel.add( Box.createGlue() );
			dropOffPickUpButtonPanel.add( dropOffButton );
			dropOffPickUpButtonPanel.add(Box.createHorizontalStrut( 55 ) );
			dropOffPickUpButtonPanel.add( pickUpButton );
			dropOffPickUpButtonPanel.add( Box.createGlue() );
				
			setBorder( BorderFactory.createLineBorder( Color.black ) );
			setLayout( new GridBagLayout() );
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = c.gridy = 0;
			c.gridwidth = 12;
			c.gridheight = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			add( kitRobotLabelPanel, c );
			c.gridy = 1;
			c.gridheight = 3;
			add( lightKeyPanel, c );
			c.gridy = 4;
			c.gridwidth = 4;
			c.gridheight = 1;
			c.fill = GridBagConstraints.NONE;
			add( robotOnOffButtonPanel, c );
			c.gridy = 5;
			add( instructionPanel, c );
			c.gridy = 7;
			c.gridwidth = 3;
			c.gridheight = 6;
			add( kitStandImageLabel, c );
			c.gridx = 2;
			c.gridy = 13;
			c.gridwidth = 1;
			c.gridheight = 2;
			add( cameraPanel, c );
			c.gridx = 3;
			c.gridy = 7;
			c.gridwidth = 1;
			c.gridheight = 6;
			c.fill = GridBagConstraints.VERTICAL;
			add( posButtonPanel, c );
			c.gridx = 4;
			c.gridy = 4;
			c.gridheight = 13;
			add( blankPanel1, c );
			c.gridx = 5;
			c.gridwidth = 7;
			c.gridheight = 3;
			c.fill = GridBagConstraints.NONE;
			add( kitDeliveryStationImageLabel, c );
			c.gridy = 7;
			c.gridheight = 2;
			c.fill = GridBagConstraints.HORIZONTAL;
			add( dropOffPickUpButtonPanel, c );
			c.gridy = 8;
			c.gridwidth = 2;
			c.gridheight = 9;
			add( blankPanel2, c );
			c.gridx = 7;
			c.gridy = 9;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.NONE;
			add( kitRobotImageLabelPanel, c );
			
			//Initialize Kit Robot on/off
			GUIKitRobot kitRobot = fcm.server.getKitRobot();
			if( kitRobot.kitRobot.state == KitRobot.KRState.OFF )
				setKitRobotOn( false );
			else
				setKitRobotOn( true );
		}
		
		/**
		 * Returns true if the kit robot is on
		 * 
		 * @return boolean variable if the kit robot is on
		 */
		public boolean getKitRobotOn() { return kitRobotOnButton.isSelected(); }
		
		/**
		 * Turns the kit robot on and off
		 * 
		 * @param on boolean variable if kit robot is on or off
		 */
		public void setKitRobotOn ( boolean on ) {
			kitRobotOnButton.setSelected( on );
			kitRobotOffButton.setSelected( !on );
			if ( on )
				resetMoveButtons();
			else
				disableMoveButtons();
		}
		
		/**
		 * Return if a button has already been selected in order to tell if
		 * the button pressed is the source or the destination of the kit robot
		 * 
		 * @return boolean variable if the first button has already been selected or not
		 */
		public boolean getFirstButtonSelected() { return firstButtonSelected; }
		
		/**
		 * Sets the firstButtonSelected variable. Used after source of movement is selected
		 * 
		 * @param selected boolean variable storing if one button has already been selected
		 */
		public void setFirstButtonSelected( boolean selected ) { firstButtonSelected = selected; }
		
		/**
		 * Sets the pickUpButton to enabled/disabled
		 * 
		 * @param enabled boolean variable if the pickUpButton should be enabled or not
		 */
		public void setPickUpButtonEnabled( boolean enabled ) { pickUpButton.setEnabled( enabled ); }
		
		/**
		 * Sets the dropOffButton to enabled/disabled
		 * 
		 * @param enabled boolean variable if the dropOffButton should be enabled or not
		 */
		public void setDropOffButtonEnabled( boolean enabled ) { dropOffButton.setEnabled( enabled ); }
		
		/**
		 * Enables or disables the first two kit stand position buttons. These are the
		 * kit positions where the kits are assembled, not inspected.
		 * 
		 * @param enabled boolean variable if the buttons are to be enabled/disabled
		 */
		public void setKitStandAssemblyPositionButtonsEnabled( boolean enabled ) {
			kitStandPositionButtons.get( 0 ).setEnabled( enabled );
			kitStandPositionButtons.get( 1 ).setEnabled( enabled );
		}
		
		/**
		 * Enables or disables the last kit stand position button. This is the location
		 * where the kit is inspected, not assembled.
		 * 
		 * @param enabled boolean variable if the button is enabled/disabled
		 */
		public void setInspectionPositionEnabled( boolean enabled ) { kitStandPositionButtons.get( 2 ).setEnabled( enabled ); }
		
		/**
		 * This method calls other button enabling methods to disable all movement buttons
		 * while the robot is off or still completing a task
		 * 
		 */
		public void disableMoveButtons() {
			setPickUpButtonEnabled( false );
			setDropOffButtonEnabled( false );
			setKitStandAssemblyPositionButtonsEnabled( false );
			setInspectionPositionEnabled( false );
		}
		
		/**
		 * This method resets the enabled/disabled state of all the buttons for the user
		 * to begin inputting a new task for the robot
		 */
		public void resetMoveButtons() {
			if ( getKitRobotOn() ) {
				setPickUpButtonEnabled( true );
				setDropOffButtonEnabled( false );
				setKitStandAssemblyPositionButtonsEnabled( true );
				setInspectionPositionEnabled( true );
				firstButtonSelected = false;
			}
		}
		
		/**
		 * Turns the red "light" on which would signify an improperly assembled kit.
		 * This method also starts a timer after turning the red "light" on so that it turns off
		 * after 3 seconds.
		 * 
		 * @param on boolean variable if the red "light" should be turned on/off
		 */
		public void redLightOn( boolean on ) {
			if ( on == true ) {
				redColorLabel.setIcon( pictureConfirmationColors.get( 0 ) );
				cameraLightTimer.start();
			}
			else {
				redColorLabel.setIcon( pictureConfirmationColors.get( 3 ) );
			}
		}
		
		/**
		 * Turns the yellow "light" on which would signify an incomplete kit.
		 * This method also starts a timer after turning the yellow "light" on so that it turns off
		 * after 3 seconds.
		 * 
		 * @param on boolean variable if the yellow "light" should be turned on/off
		 */
		public void yellowLightOn( boolean on ) {
			if ( on == true ) {
				yellowColorLabel.setIcon( pictureConfirmationColors.get( 1 ) );
				cameraLightTimer.start();
			}
			else {
				yellowColorLabel.setIcon( pictureConfirmationColors.get( 4 ) );
			}
		}
		
		/**
		 * Turns the green "light" on which would signify an properly assembled kit.
		 * This method also starts a timer after turning the green "light" on so that it turns off
		 * after 3 seconds.
		 * 
		 * @param on boolean variable if the green "light" should be turned on/off
		 */
		public void greenLightOn( boolean on ) {
			if ( on == true ) {
				greenColorLabel.setIcon( pictureConfirmationColors.get( 2 ) );
				cameraLightTimer.start();
			}
			else {
				greenColorLabel.setIcon( pictureConfirmationColors.get( 5 ) );
			}
		}
		
		/**
		 * Gives functionality to all the JButtons, JRadioButtons, and Timers in the
		 * KitRobotControlPanel
		 * 
		 */
		public void actionPerformed( ActionEvent ae ) {
			// get entry corresponding to kit robot
			int krKey = fcm.server.kitRobotID;
			GUIKitRobot kitRobot = fcm.server.getKitRobot();

			// ignore command if kit robot is broken
			if (kitRobot.kitRobot.state == KitRobot.KRState.BROKEN) {
				fcm.printBroken("kit robot");
				return;
			}
			
			//Once the pickup button is pressed, user can only select one of the first two
			//kit stand positions
			if ( ae.getSource() == pickUpButton ) {
				setInspectionPositionEnabled( false );
				setFirstButtonSelected( true );
				setPickUpButtonEnabled( false );
				// prepare factory update message
				FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
				kitRobot.kitRobot.state = KitRobot.KRState.PICK_UP;
				Point2D.Double target = fcm.server.getKitDeliv().inConveyor.getItemLocation(0, update.timeElapsed);
				target.x += 60;
				target.y += 40;
				target = kitRobot.fixTarget(target);
				update.itemMoves.put(krKey, kitRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIKitRobot.SPEED));
				fcm.server.applyUpdate(update); // apply and broadcast update message
			}
			
			// TODO: the code to move to the different kit stands is identical except for the y offset, so the code below can be shortened considerably
			//If this is the first button selected, the user can only select the inspection position as destination
			//If this is the second button selected, all buttons are disabled until the robot completes the task
			else if ( ae.getSource() == kitStandPositionButtons.get( 0 ) ) {
				setPickUpButtonEnabled( false );
				setKitStandAssemblyPositionButtonsEnabled( false );
				if ( getFirstButtonSelected() ) {
					disableMoveButtons();
				}
				setFirstButtonSelected( true );
				// get entry corresponding to this kit stand
				int kitStandKey = fcm.server.kitStandID;
				GUIKitStand kitStand = fcm.server.getKitStand();
				// prepare factory update message
				FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
				kitRobot.kitRobot.state = KitRobot.KRState.KIT_STAND;
				kitRobot.kitRobot.targetID = 0;
				Point2D.Double target = kitRobot.fixTarget(new Point2D.Double(kitStand.movement.getStartPos().x, kitStand.movement.getStartPos().y - 90));
				update.itemMoves.put(krKey, kitRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIKitRobot.SPEED));
				fcm.server.applyUpdate(update); // apply and broadcast update message
				return; // no need to check if other buttons selected
			}
			
			//If this is the first button selected, the user can only select the inspection position as destination
			//If this is the second button selected, all buttons are disabled until the robot completes the task
			else if ( ae.getSource() == kitStandPositionButtons.get( 1 ) ) {
				setPickUpButtonEnabled( false );
				setKitStandAssemblyPositionButtonsEnabled( false );
				if ( getFirstButtonSelected() ) {
					disableMoveButtons();
				}
				setFirstButtonSelected( true );
				// get entry corresponding to this kit stand
				int kitStandKey = fcm.server.kitStandID;
				GUIKitStand kitStand = fcm.server.getKitStand();
				// prepare factory update message
				FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
				kitRobot.kitRobot.state = KitRobot.KRState.KIT_STAND;
				kitRobot.kitRobot.targetID = 1;
				Point2D.Double target = kitRobot.fixTarget(kitStand.movement.getStartPos());
				update.itemMoves.put(krKey, kitRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIKitRobot.SPEED));
				fcm.server.applyUpdate(update); // apply and broadcast update message
				return; // no need to check if other buttons selected
			}
			
			//If this is the first button selected, the user can only drop the kit off at the kit delivery station
			//If this is the second button selected, all buttons are disabled until the robot complete the task
			else if ( ae.getSource() == kitStandPositionButtons.get( 2 ) ) {
				setPickUpButtonEnabled( false );
				setKitStandAssemblyPositionButtonsEnabled( false );
				setInspectionPositionEnabled( false );
				if ( !getFirstButtonSelected() ) {
					setDropOffButtonEnabled( true );
					setFirstButtonSelected( true );
				}

				// get entry corresponding to this kit stand
				int kitStandKey = fcm.server.kitStandID;
				GUIKitStand kitStand = fcm.server.getKitStand();
				// prepare factory update message
				FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
				kitRobot.kitRobot.state = KitRobot.KRState.KIT_STAND;
				kitRobot.kitRobot.targetID = 2;
				Point2D.Double target = kitRobot.fixTarget(new Point2D.Double(kitStand.movement.getStartPos().x, kitStand.movement.getStartPos().y + 90));
				update.itemMoves.put(krKey, kitRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIKitRobot.SPEED));
				fcm.server.applyUpdate(update); // apply and broadcast update message
				return; // no need to check if other buttons selected
			}
			
			//This will always be the second button selected so all buttons will be disabled until the robot finished its task
			else if ( ae.getSource() == dropOffButton ) {
				disableMoveButtons();
				// prepare factory update message
				FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
				kitRobot.kitRobot.state = KitRobot.KRState.DROP_OFF;
				Point2D.Double target = kitRobot.fixTarget(fcm.server.getKitDeliv().getOutConveyorLocation());
				update.itemMoves.put(krKey, kitRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIKitRobot.SPEED));
				fcm.server.applyUpdate(update); // apply and broadcast update message
			}
			
			//This will send a request to the server to check if the kit is properly assembled
			else if ( ae.getSource() == takePictureButton ) {
				//request from server
				// get kit in kit stand
				GUIKit guiKit1 = fcm.server.getKitStand().getKit(2);
				if (guiKit1 == null) {
					// no kit in kit stand, set yellow light
					yellowLightOn(true);
					return;
				}
				Kit kit1 = guiKit1.kit;
				// get kit type in production
				Kit kit2 = null;
				int i;
				for (i = 0; i < fcm.server.getStatus().cmds.size(); i++) {
					if (fcm.server.getStatus().status.get(i) == ProduceStatusMsg.KitStatus.PRODUCTION) {
						kit2 = fcm.server.getKitByNumber(fcm.server.getStatus().cmds.get(i).kitNumber);
						break;
					}
				}
				if (kit2 == null) {
					// no kit in production, set yellow light
					yellowLightOn(true);
					return;
				}
				boolean incomplete = false;
				for (i = 0; i < Kit.MAX_PARTS; i++) {
					if (kit2.getParts().get(i) != null && kit1.getParts().get(i) == null) {
						incomplete = true; // kit in production has a part where kit in kit stand doesn't
					}
					else if (kit1.getParts().get(i) != null && (kit2.getParts().get(i) == null || kit1.getParts().get(i).getNumber() != kit2.getParts().get(i).getNumber())) {
						// parts in kit are different, set red light
						// note that this only checks that the part numbers are different,
						// so it is possible to get false negatives if a part type is deleted and replaced with another with the same part number
						redLightOn(true);
						return;
					}
				}
				// set yellow light if incomplete, otherwise set red light
				if (incomplete) {
					yellowLightOn(true);
				}
				else {
					greenLightOn(true);
				}
			}
			
			//This will turn the camera confirmation lights off when triggered
			else if ( ae.getSource() == cameraLightTimer ) {
				redLightOn( false );
				yellowLightOn( false );
				greenLightOn( false );
			}
			
			//This will turn the Kit Robot on
			else if ( ae.getSource() == kitRobotOnButton ) {
				resetMoveButtons();
				if ( kitRobot.kitRobot.state == KitRobot.KRState.OFF ) {
					kitRobot.kitRobot.state = KitRobot.KRState.IDLE;
					// prepare factory update message
					FactoryUpdateMsg update = new FactoryUpdateMsg( fcm.server.getState() );
					update.putItems.put( krKey, kitRobot ); // put updated kit robot in update message
					fcm.server.applyUpdate( update ); // apply and broadcast update message
				}
			}
			
			//This will turn the Kit Robot off
			else if ( ae.getSource() == kitRobotOffButton ) {
				disableMoveButtons();
				if ( kitRobot.kitRobot.state != KitRobot.KRState.OFF ) {
					kitRobot.kitRobot.state = KitRobot.KRState.OFF;
					// prepare factory update message
					FactoryUpdateMsg update = new FactoryUpdateMsg( fcm.server.getState() );
					update.putItems.put( krKey, kitRobot ); // put updated kit robot in update message
					fcm.server.applyUpdate( update ); // apply and broadcast update message
				}
			}
		}
	}
