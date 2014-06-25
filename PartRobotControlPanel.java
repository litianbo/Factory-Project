import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.Timer;

/**
 * This class is the control panel inside FactoryControlManager
 * that controls the Part Robot device
 *
 */
@SuppressWarnings("serial")
public class PartRobotControlPanel extends JPanel implements ActionListener {
		FactoryControlManager fcm;
		ImageIcon partRobotImage, nestImage;
		JPanel robotOnOffButtonPanel, partRobotGripperButtonPanel, partRobotTitleLabelPanel, kit1Panel, kit2Panel, nestPanel, takePicPanel;
		JPanel blankPanel1, blankPanel2, blankPanel3, blankPanel4, blankPanel5, lightKeyPanel, redLightDescPanel, yellowLightDescPanel, greenLightDescPanel, kitPanel, instructionPanel;
		JLabel partRobotTitleLabel, partRobotImageLabel, redLightDescLabel, yellowLightDescLabel, greenLightDescLabel, instructionsLabel;
		JRadioButton partRobotOnButton, partRobotOffButton;
		ButtonGroup onOffButtonGroup, partRobotGripperButtonGroup;
		Dimension textFieldSize, kitButtonSize, nestButtonSize, nestPanelSize, takePicPanelSize, blankPanelSize, blankPanelSize2, pictureConfirmationPanelSize;
		Dimension takePicButtonSize, kitPanelSize, instructionPanelSize;
		ArrayList<JButton> kit1PositionButtons, kit2PositionButtons, nestButtons, takePictureButtons;
		ArrayList<JRadioButton> partRobotGripperButtons;
		ArrayList<ImageIcon> kitPosImages, pictureConfirmationColors;
		ArrayList<JTextField> nestPartContentsTextFields;
		ArrayList<JLabel> colorLabels, lightKeyColors;
		ArrayList<JPanel> pictureConfirmationPanels, nests, cameras;
		Timer cameraLightTimer;
		int cameraNumber, gripperNumber, nestNumber, kit1Pos, kit2Pos;
		
		/**
		 * Constructor; sets layout for panel
		 * 
		 * @param fcm pointer to FactoryControlManager object
		 */
		public PartRobotControlPanel( FactoryControlManager fcm ) {
			this.fcm = fcm;
			
			//ImageIcons
			partRobotImage = new ImageIcon( "images/guiserver_thumbs/partRobot_thumb.png" );
			nestImage = new ImageIcon( "images/guiserver_thumbs/nest_thumb_large.png" );
			kitPosImages = new ArrayList<ImageIcon>();
			for ( int i = 0; i < 8; i++ ) {
				kitPosImages.add( new ImageIcon( "images/guiserver_thumbs/kit_pos/pos" + i + ".png" ) );
			}
			pictureConfirmationColors = new ArrayList<ImageIcon>();
			pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/red.png" ) );
			pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/yellow.png" ) );
			pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/green.png" ) );
			pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/dark_red.png" ) );
			pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/dark_yellow.png" ) );
			pictureConfirmationColors.add( new ImageIcon( "images/guiserver_thumbs/colors/dark_green.png" ) );
			
			//Dimensions
			textFieldSize = new Dimension( 70, 15 );
			kitButtonSize = new Dimension( 28, 35 );
			nestButtonSize = new Dimension( 75, 47 );
			nestPanelSize = new Dimension( 75, 500 );
			blankPanelSize = new Dimension( 80, 400 );
			blankPanelSize2 = new Dimension( 635, 30 );
			takePicPanelSize = new Dimension( 40, 500 );
			pictureConfirmationPanelSize = new Dimension( 40, 20 );
			takePicButtonSize = new Dimension( 40, 40 );
			kitPanelSize = new Dimension( 125, 300 );
			instructionPanelSize = new Dimension( 250, 35 );
			
			//Timers
			cameraLightTimer = new Timer( 3000, this );
			cameraLightTimer.setRepeats( false );
			
			//JPanels
			robotOnOffButtonPanel = new JPanel();
			partRobotGripperButtonPanel = new JPanel();
			partRobotTitleLabelPanel = new JPanel();
			kit1Panel = new JPanel();
			kit2Panel = new JPanel();
			nestPanel = new JPanel();
			takePicPanel = new JPanel();
			blankPanel1 = new JPanel();
			blankPanel2 = new JPanel();
			blankPanel3 = new JPanel();
			blankPanel4 = new JPanel();
			blankPanel5 = new JPanel();
			lightKeyPanel = new JPanel();
			redLightDescPanel = new JPanel();
			yellowLightDescPanel = new JPanel();
			greenLightDescPanel = new JPanel();
			kitPanel = new JPanel();
			instructionPanel = new JPanel();
			pictureConfirmationPanels = new ArrayList<JPanel>();
			nests = new ArrayList<JPanel>();
			cameras = new ArrayList<JPanel>();
			
			//JLabels
			partRobotTitleLabel = new JLabel();
			partRobotTitleLabel.setText( "Part Robot" );
			partRobotTitleLabel.setFont( new Font( "Serif", Font.BOLD, 24 ) );
			partRobotImageLabel = new JLabel();
			partRobotImageLabel.setIcon( partRobotImage );
			redLightDescLabel = new JLabel();
			redLightDescLabel.setText( "Nest pair contains incorrect part" );
			yellowLightDescLabel = new JLabel();
			yellowLightDescLabel.setText( "Nest pair is not full or unsettled" );
			greenLightDescLabel = new JLabel();
			greenLightDescLabel.setText( "Nest pair is full and contains proper parts" );
			instructionsLabel = new JLabel();
			instructionsLabel.setText( "<html><body style=\"text-align:center;\">Select a source<br/>Then select a destination</body></html>" );
			colorLabels = new ArrayList<JLabel>();
			for( int i = 0; i < 12; i++ ) {
				colorLabels.add( new JLabel() );
				if ( i % 3 == 0 ) {
					colorLabels.get( i ).setIcon( pictureConfirmationColors.get( 3 ) );
				}
				else if ( i % 3 == 1 ) {
					colorLabels.get( i ).setIcon( pictureConfirmationColors.get( 4 ) );
				}
				else
					colorLabels.get( i ).setIcon( pictureConfirmationColors.get( 5 ) );
			}
			lightKeyColors = new ArrayList<JLabel>();
			for( int i = 0; i < 3; i++ ) {
				lightKeyColors.add( new JLabel() );
				lightKeyColors.get( i ).setIcon( pictureConfirmationColors.get( i ) );
			}
			
			//JButtons
			kit1PositionButtons = new ArrayList<JButton>();
			kit2PositionButtons = new ArrayList<JButton>();
			nestButtons = new ArrayList<JButton>();
			takePictureButtons = new ArrayList<JButton>();
			
			for ( int i = 0; i < 8; i++ ) {
				//Buttons for first kit on kit stand
				kit1PositionButtons.add( new JButton() );
				kit1PositionButtons.get( i ).setIcon( kitPosImages.get( i ) );
				kit1PositionButtons.get( i ).setMargin( new Insets( 0, 0, 0, 0 ) );
				kit1PositionButtons.get( i ).setActionCommand( "kit_pos_1" );
				kit1PositionButtons.get( i ).addActionListener( this );
				kit1PositionButtons.get( i ).setPreferredSize( kitButtonSize );
				kit1PositionButtons.get( i ).setMaximumSize( kitButtonSize );
				kit1PositionButtons.get( i ).setMinimumSize( kitButtonSize );
				
				//Buttons for second kit on kit stand
				kit2PositionButtons.add( new JButton() );
				kit2PositionButtons.get( i ).setIcon( kitPosImages.get( i ) );
				kit2PositionButtons.get( i ).setMargin( new Insets( 0, 0, 0, 0 ) );
				kit2PositionButtons.get( i ).setActionCommand( "kit_pos_2" );
				kit2PositionButtons.get( i ).addActionListener( this );
				kit2PositionButtons.get( i ).setPreferredSize( kitButtonSize );
				kit2PositionButtons.get( i ).setMaximumSize( kitButtonSize );
				kit2PositionButtons.get( i ).setMinimumSize( kitButtonSize );
				
				//Buttons for each of the 8 nests
				nestButtons.add( new JButton() );
				nestButtons.get( i ).setIcon( nestImage );
				nestButtons.get( i ).setMargin( new Insets( 0, 0, 0, 0 ) );
				nestButtons.get( i ).setActionCommand( "nest" );
				nestButtons.get( i ).addActionListener( this );
				nestButtons.get( i ).setPreferredSize( nestButtonSize );
				nestButtons.get( i ).setMaximumSize( nestButtonSize );
				nestButtons.get( i ).setMinimumSize( nestButtonSize );
				
			}
			
			for ( int i = 0; i < 4; i++ ) {
				//Buttons to take pictures of nest pairs
				takePictureButtons.add( new JButton() );
				takePictureButtons.get( i ).setPreferredSize( takePicButtonSize );
				takePictureButtons.get( i ).setMaximumSize( takePicButtonSize );
				takePictureButtons.get( i ).setMinimumSize( takePicButtonSize );
				takePictureButtons.get( i ).setText( "<html><body style=\"text-align:center;\">Take<br/>Pic</body></html>" );
				takePictureButtons.get( i ).setMargin( new Insets( 1, 1, 1, 1 ) );
				takePictureButtons.get( i ).setActionCommand( "take_picture" );
				takePictureButtons.get( i ).addActionListener( this );
			}
			
			
			//JRadioButtons
			partRobotOnButton = new JRadioButton();
			partRobotOnButton.setText( "ON" );
			partRobotOnButton.addActionListener( this );
			partRobotOffButton = new JRadioButton();
			partRobotOffButton.setText( "OFF" );
			partRobotOffButton.addActionListener( this );
			onOffButtonGroup = new ButtonGroup();
			onOffButtonGroup.add( partRobotOnButton );
			onOffButtonGroup.add( partRobotOffButton );
			partRobotGripperButtons = new ArrayList<JRadioButton>();
			partRobotGripperButtonGroup = new ButtonGroup();
			for ( int i = 0; i < 4; i++ ) {
				partRobotGripperButtons.add( new JRadioButton() );
				partRobotGripperButtons.get( i ).setActionCommand( "gripper_button" );
				partRobotGripperButtons.get( i ).addActionListener( this );
				partRobotGripperButtons.get( i ).setMargin( new Insets( 0, 0, 0, 0 ) );
				partRobotGripperButtonGroup.add( partRobotGripperButtons.get( i ) );
			}
			partRobotGripperButtons.get( 0 ).setSelected( true );
			
			//JTextFields
			nestPartContentsTextFields = new ArrayList<JTextField>();
			for ( int i = 0; i < 8; i++ ) {
				nestPartContentsTextFields.add( new JTextField() );
				nestPartContentsTextFields.get( i ).setEditable( false );
				nestPartContentsTextFields.get( i ).setPreferredSize( textFieldSize );
				nestPartContentsTextFields.get( i ).setMaximumSize( textFieldSize );
				nestPartContentsTextFields.get( i ).setMinimumSize( textFieldSize );
				nestPartContentsTextFields.get( i ).setFont( new Font( "Sans-Serif", Font.PLAIN, 10 ) );
				nestPartContentsTextFields.get( i ).setHorizontalAlignment( JTextField.CENTER );
			}
			
			
			//Layout
			
			blankPanel1.setPreferredSize( blankPanelSize );
			blankPanel1.setMaximumSize( blankPanelSize );
			blankPanel1.setMinimumSize( blankPanelSize );
			
			blankPanel2.setPreferredSize( blankPanelSize );
			blankPanel2.setMaximumSize( blankPanelSize );
			blankPanel2.setMinimumSize( blankPanelSize );
			
			blankPanel3.setPreferredSize( blankPanelSize2 );
			blankPanel3.setMaximumSize( blankPanelSize2 );
			blankPanel3.setMinimumSize( blankPanelSize2 );
			
			blankPanel4.setPreferredSize( blankPanelSize );
			blankPanel4.setMaximumSize( blankPanelSize );
			blankPanel4.setMinimumSize( blankPanelSize );
			
			blankPanel5.setPreferredSize( blankPanelSize );
			blankPanel5.setMaximumSize( blankPanelSize );
			blankPanel5.setMinimumSize( blankPanelSize );
			
			partRobotTitleLabelPanel.setLayout( new BoxLayout( partRobotTitleLabelPanel, BoxLayout.X_AXIS ) );
			partRobotTitleLabelPanel.add( Box.createGlue() );
			partRobotTitleLabelPanel.add( partRobotTitleLabel );
			partRobotTitleLabelPanel.add( Box.createGlue() );
			
			instructionPanel.setLayout( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
			instructionPanel.setBorder( BorderFactory.createLineBorder( Color.black ) );
			instructionPanel.setPreferredSize( instructionPanelSize );
			instructionPanel.setMaximumSize( instructionPanelSize );
			instructionPanel.setMinimumSize( instructionPanelSize );
			instructionPanel.add( instructionsLabel );
			
			redLightDescPanel.setLayout( new BoxLayout( redLightDescPanel, BoxLayout.X_AXIS ) );
			redLightDescPanel.add( Box.createHorizontalStrut( 30 ) );
			redLightDescPanel.add( lightKeyColors.get( 0 ) );
			redLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			redLightDescPanel.add( redLightDescLabel );
			redLightDescPanel.add( Box.createGlue() );
			
			yellowLightDescPanel.setLayout( new BoxLayout( yellowLightDescPanel, BoxLayout.X_AXIS ) );
			yellowLightDescPanel.add( Box.createHorizontalStrut( 30 ) );
			yellowLightDescPanel.add( lightKeyColors.get( 1 ) );
			yellowLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			yellowLightDescPanel.add( yellowLightDescLabel );
			yellowLightDescPanel.add( Box.createGlue() );
			
			greenLightDescPanel.setLayout( new BoxLayout( greenLightDescPanel, BoxLayout.X_AXIS ) );
			greenLightDescPanel.add( Box.createHorizontalStrut( 30 ) );
			greenLightDescPanel.add( lightKeyColors.get( 2 ) );
			greenLightDescPanel.add( Box.createHorizontalStrut( 10 ) );
			greenLightDescPanel.add( greenLightDescLabel );
			greenLightDescPanel.add( Box.createGlue() );
			
			lightKeyPanel.setLayout( new BoxLayout( lightKeyPanel, BoxLayout.Y_AXIS ) );
			lightKeyPanel.add( Box.createGlue() );
			lightKeyPanel.add( redLightDescPanel );
			lightKeyPanel.add( yellowLightDescPanel );
			lightKeyPanel.add( greenLightDescPanel );
			lightKeyPanel.add( Box.createGlue() );
			
			kit1Panel.setBorder( new TitledBorder ( BorderFactory.createEtchedBorder( EtchedBorder.RAISED ), "Kit Position 1" ) );
			kit1Panel.setLayout( new GridLayout( 2, 4, 0, 0 ) );
			for ( JButton button : kit1PositionButtons ) {
				kit1Panel.add( button );
			}
			
			kit2Panel.setBorder( new TitledBorder ( BorderFactory.createEtchedBorder( EtchedBorder.RAISED ), "Kit Position 2" ) );
			kit2Panel.setLayout( new GridLayout( 2, 4, 0, 0 ) );
			for ( JButton button : kit2PositionButtons ) {
				kit2Panel.add( button );
			}
			
			kitPanel.setLayout( new BoxLayout( kitPanel, BoxLayout.Y_AXIS ) );
			kitPanel.setPreferredSize( kitPanelSize );
			kitPanel.setMaximumSize( kitPanelSize );
			kitPanel.setMinimumSize( kitPanelSize );
			kitPanel.add( Box.createVerticalStrut( 80 ) );
			kitPanel.add( kit1Panel );
			kitPanel.add( Box.createGlue() );
			kitPanel.add( kit2Panel );
			kitPanel.add( Box.createGlue() );
			
			takePicPanel.setLayout( new BoxLayout( takePicPanel, BoxLayout.Y_AXIS ) ); 
			takePicPanel.setPreferredSize( takePicPanelSize );
			takePicPanel.setMaximumSize( takePicPanelSize );
			takePicPanel.setMinimumSize( takePicPanelSize );
			takePicPanel.add( Box.createVerticalStrut( 10 ) );
			for ( int i = 0; i < 4; i++ ){
				pictureConfirmationPanels.add( new JPanel() );
				pictureConfirmationPanels.get( i ).setLayout( new BoxLayout( pictureConfirmationPanels.get( i ), BoxLayout.X_AXIS ) );
				pictureConfirmationPanels.get( i ).setPreferredSize( pictureConfirmationPanelSize );
				pictureConfirmationPanels.get( i ).setMaximumSize( pictureConfirmationPanelSize );
				pictureConfirmationPanels.get( i ).setMinimumSize( pictureConfirmationPanelSize );
				pictureConfirmationPanels.get( i ).add( colorLabels.get( i * 3 ) );
				pictureConfirmationPanels.get( i ).add( colorLabels.get( i * 3 + 1 ) );
				pictureConfirmationPanels.get( i ).add( colorLabels.get( i * 3 + 2 ) );
				cameras.add( new JPanel() );
				cameras.get( i ).setLayout( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
				cameras.get( i ).add( takePictureButtons.get( i ) );
				cameras.get( i ).add( pictureConfirmationPanels.get( i ) );
				takePicPanel.add( cameras.get( i ) );
				if ( i < 3 )
					takePicPanel.add( Box.createGlue() );
			}
			takePicPanel.add( Box.createVerticalStrut( 10 ) );
			
			robotOnOffButtonPanel.setLayout( new BoxLayout( robotOnOffButtonPanel, BoxLayout.X_AXIS ) );
			robotOnOffButtonPanel.add( Box.createGlue() );
			robotOnOffButtonPanel.add( partRobotOnButton );
			robotOnOffButtonPanel.add(Box.createHorizontalStrut( 40 ) );
			robotOnOffButtonPanel.add( partRobotOffButton );
			robotOnOffButtonPanel.add( Box.createGlue() );	
			
			nestPanel.setLayout( new BoxLayout( nestPanel, BoxLayout.Y_AXIS ) );
			nestPanel.setPreferredSize( nestPanelSize );
			nestPanel.setMaximumSize( nestPanelSize );
			nestPanel.setMinimumSize( nestPanelSize );
			for( int i = 0; i < 8; i++ ) {
				nests.add( new JPanel() );
				nests.get( i ).setLayout( new FlowLayout( FlowLayout.CENTER, 0, 0 ) );
				nests.get( i ).add( nestButtons.get( i ) );
				nests.get( i ).add( nestPartContentsTextFields.get( i ) );
				nestPanel.add( nests.get( i ) );
			}
			
			partRobotGripperButtonPanel.setLayout( new BoxLayout( partRobotGripperButtonPanel, BoxLayout.X_AXIS ) );
			partRobotGripperButtonPanel.add( Box.createHorizontalStrut( 7 ) );
			for( int i = 0; i < 3; i++ ) {
				partRobotGripperButtonPanel.add( partRobotGripperButtons.get( i ) );
				partRobotGripperButtonPanel.add( Box.createGlue() );
			}
			partRobotGripperButtonPanel.add( partRobotGripperButtons.get( 3 ) );
			partRobotGripperButtonPanel.add( Box.createHorizontalStrut( 7 ) );
				
			setBorder( BorderFactory.createLineBorder( Color.black ) );
			setLayout( new GridBagLayout() );
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = c.gridy = 0;
			c.gridwidth = 13;
			c.gridheight = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			add( partRobotTitleLabelPanel, c );
			c.gridy = 1;
			c.gridwidth = 9;
			c.gridheight = 3;
			add( lightKeyPanel, c );
			c.gridy = 4;
			c.gridheight = 1;
			add( blankPanel3, c );
			c.gridy = 5;
			c.gridwidth = 1;
			c.gridheight = 11;
			c.fill = GridBagConstraints.VERTICAL;
			add( blankPanel4, c );
			c.gridx = 1;
			c.gridy = 5;
			c.gridwidth = 3;
			c.gridheight = 2;
			c.fill = GridBagConstraints.NONE;
			add( robotOnOffButtonPanel, c );
			c.gridy = 7;
			add( instructionPanel, c );
			c.gridy = 8;
			c.gridheight = 5;
			add( kitPanel, c );
			c.gridx = 4;
			c.gridy = 5;
			c.gridwidth = 1;
			c.gridheight = 10;
			c.fill = GridBagConstraints.VERTICAL;
			add( blankPanel1, c );
			c.gridx = 5;
			c.gridy = 7;
			c.gridwidth = 3;
			c.gridheight = 1;
			c.fill = GridBagConstraints.HORIZONTAL;
			add( partRobotGripperButtonPanel, c );
			c.gridy = 6;
			c.gridheight = 8;
			c.fill = GridBagConstraints.NONE;
			add( partRobotImageLabel, c );
			c.gridx = 8;
			c.gridy = 5;
			c.gridwidth = 1;
			c.gridheight = 10;
			c.fill = GridBagConstraints.VERTICAL;
			add( blankPanel2, c );
			c.gridx = 9;
			c.gridy = 1;
			c.gridwidth = 2;
			c.gridheight = 15;
			add( nestPanel, c );
			c.gridx = 11;
			c.gridwidth = 1;
			add( takePicPanel, c );
			c.gridx = 12;
			c.fill = GridBagConstraints.VERTICAL;
			add( blankPanel5, c );
			
			//Initialize Part Robot on/off
			GUIPartRobot partRobot = fcm.server.getPartRobot();
			if( partRobot.partRobot.state == PartRobot.PRState.OFF )
				setPartRobotOn( false );
			else
				setPartRobotOn( true );
			
			//Initialize Nest contents
			for( int nestNumber = 0; nestNumber < 8; nestNumber++ ) {
				int key = fcm.server.nestIDs.get(nestNumber);
				Object stateObj = fcm.server.getState().items.get(key);
				if( stateObj instanceof GUINest ) {
					GUINest nest = (GUINest)stateObj;
					if ( !nest.nest.nestedItems.isEmpty() )
						setPartContent( nest.nest.nestedItems.get( 0 ).getName(), nestNumber );
					else
						setPartContent( "Empty", nestNumber );
				}
			}
		}
		
		/**
		 * Returns true if the part robot is on
		 * 
		 * @return boolean variable that is true if the part robot is on
		 */
				
		public boolean getPartRobotOn () { return partRobotOnButton.isSelected(); }
		
		/**
		 * Turns the part robot on and off
		 * 
		 * @param on boolean variable if part robot is on or off
		 */
		public void setPartRobotOn ( boolean on ) {
			partRobotOnButton.setSelected( on );
			partRobotOffButton.setSelected( !on );
			if ( on ) {
				resetMoveButtons();
			}
			else {
				disableMoveButtons();
			}
		}
		

		/*public void setNestButtonsEnabled( boolean enabled ) {
			for ( JButton nest : nestButtons ) {
				nest.setEnabled( enabled );
			}
		}
		
		/**
		 * Sets all the kit position buttons to enabled or disabled
		 * 
		 * @param enabled boolean variable signifying if the buttons should be enabled/disabled
		 
		public void setKitButtonsEnabled( boolean enabled ) {
			for( JButton kitPos : kit1PositionButtons ) {
				kitPos.setEnabled( enabled );
			}
			for( JButton kitPos : kit2PositionButtons ) {
				kitPos.setEnabled( enabled );
			}
		}*/
		
		/**
		 * This method resets the enabled/disabled state of all the buttons for the user
		 * to begin inputting a new task for the robot
		 */
		public void resetMoveButtons() {
			/*if ( getPartRobotOn() ) {
				setKitButtonsEnabled( false );
				setNestButtonsEnabled( true );
			}*/
		}
		
		public void disableMoveButtons() {
			//setKitButtonsEnabled( false );
			//setNestButtonsEnabled( false );
		}
		
		/**
		 * Sets all the takePictureButtons to enabled/disabled
		 * This is used to prevent the user from taking more than one picture at a time
		 * 
		 * @param enabled boolean variable signifying if the buttons should be enabled/disabled
		 */
		public void setTakePictureButtonsEnabled( boolean enabled ) {
			for( JButton button : takePictureButtons ) {
				button.setEnabled( enabled );
			}
		}
		
		/**
		 * Sets the text in the JTextField below each nest which states the contents of that nest
		 * 
		 * @param partName the name of the part that is in the nest
		 * @param nestNumber which nest JTextField is to be updated with partName
		 */
		public void setPartContent( String partName, int nestNumber ) {
			nestPartContentsTextFields.get( nestNumber ).setText( partName );
		}
		
		/**
		 * Turns the red "light" on which would signify a nest pair containing 1 or more parts that it should not
		 * This method also starts a timer after turning the red "light" on so that it turns off
		 * after 3 seconds.
		 * 
		 * @param on boolean variable if the red "light" should be turned on/off
		 */
		public void redLightOn( boolean on ) {
			if ( on == true ) {
				colorLabels.get( cameraNumber * 3 ).setIcon( pictureConfirmationColors.get( 0 ) );
				cameraLightTimer.start();
			}
			else {
				colorLabels.get( cameraNumber * 3 ).setIcon( pictureConfirmationColors.get( 3 ) );
			}
		}
		
		/**
		 * Turns the yellow "light" on which would signify a nest pair that is not full or has not completely settled
		 * This method also starts a timer after turning the yellow "light" on so that it turns off
		 * after 3 seconds.
		 * 
		 * @param on boolean variable if the yellow "light" should be turned on/off
		 */
		public void yellowLightOn( boolean on ) {
			if ( on == true ) {
				colorLabels.get( cameraNumber * 3 + 1 ).setIcon( pictureConfirmationColors.get( 1 ) );
				cameraLightTimer.start();
			}
			else {
				colorLabels.get( cameraNumber * 3 + 1 ).setIcon( pictureConfirmationColors.get( 4 ) );
			}
		}
		
		/**
		 * Turns the green "light" on which would signify a nest pair that is full and contains the correct parts
		 * This method also starts a timer after turning the green "light" on so that it turns off
		 * after 3 seconds.
		 * 
		 * @param on boolean variable if the green "light" should be turned on/off
		 */
		public void greenLightOn( boolean on ) {
			if ( on == true ) {
				colorLabels.get( cameraNumber * 3 + 2 ).setIcon( pictureConfirmationColors.get( 2 ) );
				cameraLightTimer.start();
			}
			else {
				colorLabels.get( cameraNumber * 3 + 2 ).setIcon( pictureConfirmationColors.get( 5 ) );
			}
		}
		
		/**
		 * Gives functionality to all the JButtons, JRadioButtons, and Timers in the
		 * PartRobotControlPanel
		 * 
		 */
		public void actionPerformed( ActionEvent ae ) {
			
			String cmd = "";
			if ( ae.getActionCommand() != null) 
				cmd = ae.getActionCommand();

			// get entry corresponding to part robot
			int prKey = fcm.server.partRobotID;
			GUIPartRobot partRobot = fcm.server.getPartRobot();
			
			// ignore command if part robot is broken
			if (partRobot.partRobot.state == PartRobot.PRState.BROKEN) {
				fcm.printBroken("part robot");
				return;
			}
			
			/*
			 *If the actionCommand originates from a nest, the kitButtons are enabled so the
			 *user can select a location to put the part. The for loop finds which nest the
			 *command originated from
			 */
			if ( cmd.equals( "nest" ) ) {
				//setNestButtonsEnabled( false );
				//setKitButtonsEnabled( true );
				for( int i = 0; i < nestButtons.size(); i++ ) {
					if( ae.getSource() == nestButtons.get( i ) ) {
						nestNumber = i;
						// get entry corresponding to this nest
						int nestKey = fcm.server.nestIDs.get(nestNumber);
						GUINest nest = fcm.server.getNest(nestNumber);
						// prepare factory update message
						FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
						Point2D.Double target = partRobot.fixTarget(new Point2D.Double(nest.movement.getStartPos().x, nest.movement.getStartPos().y + 25));
						partRobot.partRobot.state = PartRobot.PRState.NEST;
						partRobot.partRobot.targetID = nestNumber;
						partRobot.movement = partRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIPartRobot.SPEED);
						update.putItems.put(prKey, partRobot);
						fcm.server.applyUpdate(update); // apply and broadcast update message
						return; // no need to check if other buttons selected
					}
				}
			}
			
			/*
			 * If the actionCommand originates from a kit1 position button then all other movement
			 * buttons are disabled until the robot finishes its task. The for loop finds which
			 * kit1 position the command originated from
			 */
			else if ( cmd.equals( "kit_pos_1" ) ) {
				//setKitButtonsEnabled( false );
				for( int i = 0; i < kit1PositionButtons.size(); i++ ) {
					if( ae.getSource() == kit1PositionButtons.get( i ) ) {
						kit1Pos = i;
						// get entry corresponding to this kit stand
						int kitStandKey = fcm.server.kitStandID;
						GUIKitStand kitStand = fcm.server.getKitStand();
						// prepare factory update message
						FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
						Point2D.Double target = partRobot.fixTarget(new Point2D.Double(kitStand.movement.getStartPos().x, kitStand.movement.getStartPos().y - 90));
						partRobot.partRobot.state = PartRobot.PRState.KIT_STAND;
						partRobot.partRobot.targetID = 0;
						partRobot.partRobot.kitPosID = kit1Pos;
						partRobot.movement = partRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIPartRobot.SPEED);
						update.putItems.put(prKey, partRobot);
						fcm.server.applyUpdate(update); // apply and broadcast update message
						return; // no need to check if other buttons selected
					}
				}
			}
			
			/*
			 * If the actionCommand originates from a kit2 position button then all other movement
			 * buttons are disabled until the robot finishes its task. The for loop finds which
			 * kit2 position the command originated from
			 */
			else if ( cmd.equals( "kit_pos_2" ) ) {
				//setKitButtonsEnabled( false );
				for( int i = 0; i < kit2PositionButtons.size(); i++ ) {
					if( ae.getSource() == kit2PositionButtons.get( i ) ) {
						kit2Pos = i;
						// get entry corresponding to this kit stand
						int kitStandKey = fcm.server.kitStandID;
						GUIKitStand kitStand = fcm.server.getKitStand();
						// prepare factory update message
						FactoryUpdateMsg update = new FactoryUpdateMsg(fcm.server.getState());
						Point2D.Double target = partRobot.fixTarget(kitStand.movement.getStartPos());
						partRobot.partRobot.state = PartRobot.PRState.KIT_STAND;
						partRobot.partRobot.targetID = 1;
						partRobot.partRobot.kitPosID = kit2Pos;
						partRobot.movement = partRobot.movement.moveToAtSpeed(update.timeElapsed, target, 0, GUIPartRobot.SPEED);
						update.putItems.put(prKey, partRobot);
						fcm.server.applyUpdate(update); // apply and broadcast update message
						return; // no need to check if other buttons selected
					}
				}
			}
			
			//Finds which part robot gripper is selected
			else if ( cmd.equals( "gripper_button" ) ) {
				for ( int i = 0; i < partRobotGripperButtons.size(); i++ ) {
					if ( ae.getSource() == partRobotGripperButtons.get( i ) ) {
						gripperNumber = i;
						partRobot.partRobot.gripperID = partRobotGripperButtons.size() - 1 - gripperNumber;
					}
				}
			}
			
			/*
			 * Once a take picture button is selected, all the other take picture buttons are disabled
			 * until the result is returned. The for loop finds which take picture button was activated
			 */
			else if ( cmd.equals( "take_picture" ) ) {
				setTakePictureButtonsEnabled( false );
				for ( int i = 0; i < takePictureButtons.size(); i++ ) {
					if ( ae.getSource() == takePictureButtons.get( i ) ) {
						cameraNumber = i;
						// get entry corresponding to these nests
						GUINest nest = fcm.server.getNest(cameraNumber * 2);
						GUINest nest2 = fcm.server.getNest(cameraNumber * 2 + 1);
						// picture is good if nests are full and all parts are same type
						for (int j = 1; j < nest.nest.nestedItems.size(); j++) {
							if (!nest.nest.nestedItems.get(j).equals(nest.nest.nestedItems.get(0))) {
								redLightOn(true);
								return;
							}
						}
						for (int j = 1; j < nest2.nest.nestedItems.size(); j++) {
							if (!nest2.nest.nestedItems.get(j).equals(nest2.nest.nestedItems.get(0))) {
								redLightOn(true);
								return;
							}
						}
						if (nest.nest.nestedItems.size() < nest.nest.limit || nest2.nest.nestedItems.size() < nest2.nest.limit) {
							yellowLightOn(true);
						}
						else {
							greenLightOn(true);
						}
						return;
					}
				}
			}
			
			//This will turn the camera confirmation lights off when triggered
			else if ( ae.getSource() == cameraLightTimer ) {
				redLightOn( false );
				yellowLightOn( false );
				greenLightOn( false );
				setTakePictureButtonsEnabled( true );
			}
			
			//This will turn the Part Robot on
			else if ( ae.getSource() == partRobotOnButton ) {
				//setNestButtonsEnabled( true );
				//setNestButtonsEnabled( true );
				if ( partRobot.partRobot.state == PartRobot.PRState.OFF ) {
					partRobot.partRobot.state = PartRobot.PRState.IDLE;
					// prepare factory update message
					FactoryUpdateMsg update = new FactoryUpdateMsg( fcm.server.getState() );
					update.putItems.put( prKey, partRobot ); // put updated part robot in update message
					fcm.server.applyUpdate( update ); // apply and broadcast update message
				}
			}
			
			//This will turn the Part Robot off
			else if ( ae.getSource() == partRobotOffButton ) {
				disableMoveButtons();
				if ( partRobot.partRobot.state != PartRobot.PRState.OFF ) {
					partRobot.partRobot.state = PartRobot.PRState.OFF;
					// prepare factory update message
					FactoryUpdateMsg update = new FactoryUpdateMsg( fcm.server.getState() );
					update.putItems.put( prKey, partRobot ); // put updated part robot in update message
					fcm.server.applyUpdate( update ); // apply and broadcast update message
				}
			}
		}
	}
