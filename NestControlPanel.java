import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;


/**
 * This class is the control panel inside FactoryControlManager
 * that controls all the Nest devices
 *
 */
@SuppressWarnings("serial")
public class NestControlPanel extends JPanel implements ActionListener {
		FactoryControlManager fcm;
		ImageIcon nestImage;
		JPanel nestsTitleLabelPanel;
		JLabel nestsTitleLabel;
		ArrayList<JPanel> radioButtonPairPanel, nestPanels, radioButtonPairAndNestPanels;
		ArrayList<JRadioButton> upRadioButtons, downRadioButtons;
		ArrayList<ButtonGroup> radioButtonGroups;
		ArrayList<JLabel> nestImageLabels;
		int nestNumber;
		
		/**
		 * Constructor; sets layout for panel
		 * 
		 * @param fcm pointer to FactoryControlManager object
		 */
		public NestControlPanel( FactoryControlManager fcm ) {
			this.fcm = fcm;
			
			Dimension panelSize = new Dimension ( 150, 532 );
			
			//ImageIcons
			nestImage = new ImageIcon( "images/guiserver_thumbs/nest_thumb_large.png" );
			
			//JPanels
			nestsTitleLabelPanel = new JPanel();
			radioButtonPairPanel = new ArrayList<JPanel>();
			nestPanels = new ArrayList<JPanel>();
			radioButtonPairAndNestPanels = new ArrayList<JPanel>();
			
			//JLabels
			nestsTitleLabel = new JLabel();
			nestsTitleLabel.setText( "Nests" );
			nestsTitleLabel.setFont( new Font( "Serif", Font.BOLD, 24 ) );
			nestImageLabels = new ArrayList<JLabel>();
			for( int i = 0; i < 8; i++ ) {
				nestImageLabels.add( new JLabel() );
				nestImageLabels.get( i ).setIcon( nestImage );
			}
			
			//JRadioButtons
			upRadioButtons = new ArrayList<JRadioButton>();
			downRadioButtons = new ArrayList<JRadioButton>();
			for( int i = 0; i < 8; i++ ) {
				upRadioButtons.add( new JRadioButton() );
				upRadioButtons.get( i ).setText( "Up" );
				upRadioButtons.get( i ).addActionListener( this );
				upRadioButtons.get( i ).setActionCommand( "up_button" );
				downRadioButtons.add( new JRadioButton() );
				downRadioButtons.get( i ).setText( "Down" );
				downRadioButtons.get( i ).addActionListener( this );
				downRadioButtons.get( i ).setActionCommand( "down_button" );
			}
			
			//ButtonGroups
			radioButtonGroups = new ArrayList<ButtonGroup>();
			for( int i = 0; i < 8; i++ ) {
				radioButtonGroups.add( new ButtonGroup() );
				radioButtonGroups.get( i ).add( upRadioButtons.get( i ) );
				radioButtonGroups.get( i ).add( downRadioButtons.get( i ) );
			}
			
			//Layout
			
			nestsTitleLabelPanel.setLayout( new BoxLayout( nestsTitleLabelPanel, BoxLayout.X_AXIS ) );
			nestsTitleLabelPanel.add( Box.createGlue() );
			nestsTitleLabelPanel.add( nestsTitleLabel );
			nestsTitleLabelPanel.add( Box.createGlue() );
			
			for( int i = 0; i < 8; i++ ) {
				radioButtonPairPanel.add( new JPanel() );
				radioButtonPairPanel.get( i ).setLayout( new BoxLayout( radioButtonPairPanel.get( i ), BoxLayout.Y_AXIS ) );
				radioButtonPairPanel.get( i ).add( Box.createGlue() );
				radioButtonPairPanel.get( i ).add( upRadioButtons.get( i ) );
				radioButtonPairPanel.get( i ).add( downRadioButtons.get( i ) );
				radioButtonPairPanel.get( i ).add( Box.createGlue() );
				
				nestPanels.add( new JPanel() );
				nestPanels.get( i ).add( nestImageLabels.get( i ) );
				
				radioButtonPairAndNestPanels.add( new JPanel() );
				radioButtonPairAndNestPanels.get( i ).setLayout( new BoxLayout( radioButtonPairAndNestPanels.get( i ), BoxLayout.X_AXIS ) );
				radioButtonPairAndNestPanels.get( i ).add( Box.createGlue() );
				radioButtonPairAndNestPanels.get( i ).add( radioButtonPairPanel.get( i ) );
				radioButtonPairAndNestPanels.get( i ).add( nestPanels.get( i ) );
				radioButtonPairAndNestPanels.get( i ).add( Box.createGlue() );
			}
			
			setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
			setBorder( BorderFactory.createLineBorder( Color.black ) );
			setPreferredSize( panelSize );
			setMaximumSize( panelSize );
			setMinimumSize( panelSize );
			add( nestsTitleLabelPanel );
			add( Box.createVerticalStrut( 20 ) );
			int i = 0;
			for( JPanel panel : radioButtonPairAndNestPanels ) {
				add( panel );
				i++;
				if ( i % 2 == 0 ) {
					add( Box.createGlue() );
				}
			}
			
			//Initializing factory states
			for( int nestNumber = 0; nestNumber < 8; nestNumber++ ) {
				int key = fcm.server.nestIDs.get(nestNumber);
				Object stateObj = fcm.server.getState().items.get(key);
				if ( stateObj instanceof GUINest ) {
					GUINest nest = (GUINest)stateObj;
					setNestUpButton( nest.getNestIsUp(), nestNumber );
				}
			}
		}
		
		/**
		 * Sets a nest up( keep parts in nest ) or down( drop parts on the floor )
		 * 
		 * @param up boolean variable if the nest should be up
		 * @param nestNumber the nest to be controlled
		 */
		public void setNestUpButton( boolean up, int nestNumber ) {
			upRadioButtons.get( nestNumber ).setSelected( up );
			downRadioButtons.get( nestNumber ).setSelected( !up );
		}
		
		/**
		 * Gives functionality to all the JRadioButtons in NestControlPanel
		 */
		public void actionPerformed( ActionEvent ae ) {
			
			/*
			 * Finds which nest number the command originated from
			 */
			if( ae.getActionCommand().equals( "up_button" ) ) {
				for ( int i = 0; i < upRadioButtons.size(); i++ ) {
					if ( ae.getSource() == upRadioButtons.get( i ) ) {
						nestNumber = i;
						// get entry corresponding to this nest
						int key = fcm.server.nestIDs.get(nestNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUINest) {
							GUINest nest = (GUINest)stateObj;
							if ( !nest.getNestIsUp() ) { // only put nest up if it is down
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								nest.nest.setNestUp(true); // raise nest
								update.putItems.put(key, nest); // put updated nest in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: nest index variable does not point to a nest");
						}
						return; // no need to check if other buttons selected
					}
				}
			}
			
			/*
			 * Finds which nest number the command originated from
			 */
			else if( ae.getActionCommand().equals( "down_button" ) ) {
				for ( int i = 0; i < downRadioButtons.size(); i++ ) {
					if ( ae.getSource() == downRadioButtons.get( i ) ) {
						nestNumber = i;
						// get entry corresponding to this nest
						int key = fcm.server.nestIDs.get(nestNumber);
						Object stateObj = fcm.server.getState().items.get(key);
						if (stateObj instanceof GUINest) {
							GUINest nest = (GUINest)stateObj;
							if ( nest.getNestIsUp() ) { // only lower nest if it is up
								// prepare factory update message
								FactoryUpdateMsg update = new FactoryUpdateMsg();
								update.setTime(fcm.server.getState()); // set time in update message
								nest.nest.setNestUp(false); // dump nest
								update.putItems.put(key, nest); // put updated nest in update message
								fcm.server.applyUpdate(update); // apply and broadcast update message
							}
						}
						else {
							System.out.println("Error: nest index variable does not point to a nest");
						}
						return; // no need to check if other buttons selected
					}
				}
			}
			
		}
		
	}
