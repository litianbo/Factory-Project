import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class KitManager extends JPanel
{
	/** KitsClient variable which will sent Msg classes to the server when a button is pressed */
	private KitsClient myClient;
	/** print text in a label "kit name:" */
	private JLabel lblName;
	/** print text in a label "kit number:" */
	private JLabel lblNumber;
	/** print text in a label "kit info:" */
	private JLabel lblInfo;
	/** print text in a label "Number of kit to be changed/deleted" */
	private JLabel lblEdit;
	/** print text in a label "Kit will be changed to new kit above" */
	private JLabel lblEdit2;
	/** textfield for prompting kit name */
	private JTextField txtName;
	/** textfield for prompting kit number */
	private JTextField txtNumber;
	/** textfield for prompting kit description */
	private JTextField txtInfo;
	/** textfield for prompting the number of kit he wants to change */
	private JTextField txtEdit;
	/** create button to create a kit */
	private JButton btnCreate;
	/** change button to change a kit */
	private JButton btnChange;
	/** delete button to delete a kit */
	private JButton btnDelete;
	/** scroll pane that stores kits */
	private JScrollPane scroll;
	/** panel that contains all of the available kits */
	private JPanel pnlKits;
	/** panel that contains all part selection options */
	private JPanel pnlPartSelection;
	/** error message prompt */
	private JLabel lblMsg;
	/** label for selecting an existing kit */
	private JLabel lblSelectKit;
	/** drop down menu for part 1 */
	private JComboBox dropDown1;
	/** drop down menu for part 2 */
	private JComboBox dropDown2;
	/** drop down menu for part 3 */
	private JComboBox dropDown3;
	/** drop down menu for part 4 */
	private JComboBox dropDown4;
	/** drop down menu for part 5 */
	private JComboBox dropDown5;
	/** drop down menu for part 6 */
	private JComboBox dropDown6;
	/** drop down menu for part 7 */
	private JComboBox dropDown7;
	/** drop down menu for part 8 */
	private JComboBox dropDown8;
	/** drop down menu for selecting an existing kit */
	private JComboBox dropDownKits;
	/** ArrayList of comboBoxes used to check for validation */
	private ArrayList<JComboBox> comboBoxes = new ArrayList<JComboBox>();
	/** TreeMap of Strings used to map to a particular part */
	private TreeMap<String, Part> comboMap = new TreeMap<String, Part>();
	/** TreeMap of Strings used to map to a particular kit */
	private TreeMap<String, Kit> kitMap = new TreeMap<String, Kit>();
	/** simple array of Strings of parts used to generate each part's drop down menu */
	String partList[];
	/** simple array of Strings of kits used to generate dropDownKit's options */
	String kitList[];
	/** GridBagConstraints for this content pane */
	GridBagConstraints c;
	/** GridBagConstraints for pnlPartSelection panel */
	GridBagConstraints c2;
	/** initialization*/
	public KitManager ( KitsClient kc )
	{
		myClient = kc;		
		lblName = new JLabel("Kit Name: ");
		lblNumber = new JLabel("Kit Number: ");
		lblInfo = new JLabel("Kit Info: ");
		lblEdit = new JLabel("Number of kit to be changed/deleted");
		lblEdit2 = new JLabel("Kit will be changed to new kit above");
		lblSelectKit = new JLabel("Available kits:");
		txtName = new JTextField(10);
		txtNumber = new JTextField(10);
		txtInfo = new JTextField(10);
		txtEdit = new JTextField(10);
		btnCreate = new JButton("Create");
		btnChange = new JButton("Change");
		btnDelete = new JButton("Delete");
		lblMsg = new JLabel("");
		
		//jscrollpane for list of kits
		pnlKits = new JPanel();
		pnlKits.setLayout( new BoxLayout( pnlKits, BoxLayout.Y_AXIS ) );
		scroll = new JScrollPane(pnlKits);
		
		//Instantiate partsSelection panel
		pnlPartSelection = new JPanel();
		pnlPartSelection.setBorder(new TitledBorder("Part Selection"));
		pnlPartSelection.setLayout(new GridBagLayout());
		c2 = new GridBagConstraints();
		
		//layout GUI
		setLayout( new GridBagLayout() );
		c = new GridBagConstraints();		
		
		//parts scroll pane
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 2;
		c.gridheight = 10;
		add( scroll, c );
		
		
		//panel part Selection
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(10, 10, 0, 0);
		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 5;
		c.gridheight = 3;
		add(pnlPartSelection, c);
			
		//adding kits
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(10,10,0,0);
		c.gridx = 2;
		c.gridy = 0;
		c.weightx = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		add( lblName, c );
		
		c.gridx = 2;
		c.gridy = 1;
		add( lblNumber, c );
		
		c.gridx = 2;
		c.gridy = 2;
		add( lblInfo, c );
		
		c.gridx = 3;
		c.gridy = 0;
		add( txtName, c );
		
		c.gridx = 3;
		c.gridy = 1;
		add( txtNumber, c );
		
		c.gridx = 3;
		c.gridy = 2;
		add( txtInfo, c );
		
		c.gridx = 4;
		c.gridy = 1;
		add( btnCreate, c );
		
		
		//changing/deleting parts
		c.gridx = 2;
		c.gridy = 4;
		add( lblEdit, c );
		
		c.gridx = 3;
		c.gridy = 3;
		add( lblEdit2, c );
		
		c.gridx = 3;
		c.gridy = 4;
		add( txtEdit, c );
		
		c.gridheight = 1;
		c.gridx = 4;
		c.gridy = 3;
		add( btnChange, c );
		
		c.gridx = 4;
		c.gridy = 4;
		add( btnDelete, c );
		
		c.gridx = 2;
		c.gridy = 5;
		add( lblSelectKit, c );
		
		//action listeners for buttons
		btnCreate.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e )
			{
				int numOfEmptyComboBoxes = 0;
				boolean comboBoxesValid = true;
				comboBoxes.clear();
				comboBoxes.add(dropDown1);
				comboBoxes.add(dropDown2);
				comboBoxes.add(dropDown3);
				comboBoxes.add(dropDown4);
				comboBoxes.add(dropDown5);
				comboBoxes.add(dropDown6);
				comboBoxes.add(dropDown7);
				comboBoxes.add(dropDown8);
				for(int i=0; i<comboBoxes.size(); i++)
				{
					JComboBox comboBox = comboBoxes.get(i);
					
					if(comboBox.getSelectedItem().equals(""))
						numOfEmptyComboBoxes++;
					
				}
				if(numOfEmptyComboBoxes > 4)
					comboBoxesValid = false;
				
				if( !txtName.getText().equals("") && !txtInfo.getText().equals("") && !txtNumber.getText().equals("") && comboBoxesValid) 
				{
					try
					{
						//add kit to server
						Kit newKit = new Kit(txtName.getText(), txtInfo.getText(), (int)Integer.parseInt( txtNumber.getText()));
						
						for(int i=0; i<comboBoxes.size(); i++)
						{
							JComboBox comboBox = comboBoxes.get(i);
							if(comboBox.getSelectedIndex() != 0)
								newKit.addPart(i, comboMap.get(comboBox.getSelectedItem()));	
						}
						myClient.getCom().write( new NewKitMsg(newKit));
						requestKits();
						generateKitList();
						lblMsg.setText("");
					} 
					catch (NumberFormatException nfe) 
					{
						lblMsg.setText( "Please enter a valid kit number" );
					}
				}
				else 
				{
					lblMsg.setText( "Enter all information and select at least 4 parts" );
				}
			}
		});
		
		btnChange.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e )
			{
				comboBoxes.clear();
				comboBoxes.add(dropDown1);
				comboBoxes.add(dropDown2);
				comboBoxes.add(dropDown3);
				comboBoxes.add(dropDown4);
				comboBoxes.add(dropDown5);
				comboBoxes.add(dropDown6);
				comboBoxes.add(dropDown7);
				comboBoxes.add(dropDown8);
				int numOfEmptyComboBoxes = 0;
				boolean comboBoxesValid = true;
				for(int i=0; i<comboBoxes.size(); i++)
				{
					JComboBox comboBox = comboBoxes.get(i);
					if(comboBox.getSelectedIndex() == 0)
						numOfEmptyComboBoxes++;
				}
				if(numOfEmptyComboBoxes > 4)
					comboBoxesValid = false;
				
				if( !txtName.getText().equals("") && !txtInfo.getText().equals("") && !txtNumber.getText().equals("") && !txtEdit.getText().equals("") && comboBoxesValid) {
					try{
						//replace kit number X with new kit
						
						Kit editedKit = new Kit(txtName.getText(), txtInfo.getText(), (int)Integer.parseInt( txtNumber.getText()));
						for(int i=0; i<comboBoxes.size(); i++)
						{
							JComboBox comboBox = comboBoxes.get(i);
							if(comboBox.getSelectedIndex() != 0)
								editedKit.addPart(i, comboMap.get(comboBox.getSelectedItem()));	
						}
						myClient.getCom().write( new ChangeKitMsg( (int)Integer.parseInt( txtEdit.getText() ), editedKit ) );
						lblMsg.setText("");
						requestKits();
						generateKitList();
					} catch (NumberFormatException nfe) {
						lblMsg.setText( "Please enter a kit number to be changed" );
					}
				}
				else if( txtEdit.getText().equals("") ) {
					lblMsg.setText( "Please enter kit number to be changed." );
				}
				else {
					lblMsg.setText( "Enter all information and select at least 4 parts" );
				}
			}
		});
		
		btnDelete.addActionListener( new ActionListener() 
		{
			public void actionPerformed( ActionEvent e )
			{
				comboBoxes.clear();
				comboBoxes.add(dropDown1);
				comboBoxes.add(dropDown2);
				comboBoxes.add(dropDown3);
				comboBoxes.add(dropDown4);
				comboBoxes.add(dropDown5);
				comboBoxes.add(dropDown6);
				comboBoxes.add(dropDown7);
				comboBoxes.add(dropDown8);
				if( !txtEdit.getText().equals("") ) {
					try {
						//delete the kit on the server
						myClient.getCom().write( new DeleteKitMsg( Integer.parseInt( txtEdit.getText() ) ) );
						requestKits();
						generateKitList();
						lblMsg.setText("");
					} catch (NumberFormatException nfe) {
						lblMsg.setText( "Please enter kit number to be deleted" );
					}
				}
				else {
					lblMsg.setText( "Please enter kit number to be deleted." );
				}
			}
		});
	}
	/** tells the client to request a new ArrayList of parts from the server */
	public void requestParts()
	{
		//get updated parts list
		myClient.getCom().write( new PartListMsg() );
		generatePartList();
	}
	/** tells the client to request a new ArrayList of kits from the server */
	public void requestKits()
	{
		//get updated kits list
		myClient.getCom().write(new KitListMsg());
		generateKitList();
	}
	/** generates the list of parts to be available in each drop down menu of parts */
	public void generatePartList()
	{
		//get updated parts list
		comboMap.clear();
		for(int i=0; i<myClient.getParts().size(); i++)
		{
			Part p = myClient.getParts().get(i);
			comboMap.put(p.getName(), p);
		}
		
		partList = new String [comboMap.size()+1];
		partList[0] = ""; //want first option to be blank
		
		for(int i=0; i<myClient.getParts().size(); i++)
			partList[i+1] = myClient.getParts().get(i).getName(); //element placed in i+1 to offset blank entry at index 0
		
		pnlPartSelection.removeAll();
		setupJComboBoxes();
		validate();
		repaint();
	}
	/** generates the list of kits to be available in dropDownKits */
	public void generateKitList()
	{
		//get updated kits list
		kitMap.clear();
		myClient.getCom().write( new KitListMsg());
		ArrayList<Kit> kitArr = myClient.getKits();
		for(int i=0; i<kitArr.size(); i++)
		{
			Kit k = kitArr.get(i);
			kitMap.put(k.getName(), k);
		}
		
		kitList = new String [kitMap.size()+1];
		kitList[0] = ""; //want first option to be blank
		
		for(int i=0; i<kitArr.size(); i++)
			kitList[i+1] = kitArr.get(i).getName(); //element placed in i+1 to offset blank entry at index 0

		setupJComboBoxes2();
		validate();
		repaint();
	}
	/** regenerate kits label(s) in parts panel */
	public void displayKits(){
		//remove current list from the panel
		pnlKits.removeAll();
				
		//add new list to panel
		ArrayList<Kit> temp = myClient.getKits();
				
		for( Kit k : temp )
			pnlKits.add( new JLabel( k.getNumber() + " - " + k.getName() + " - " + k.getDescription() ) );
		
		validate();
		repaint();
	}
	/** recreates each dropDownPart JComboBox to match the Strings contained in partList[] */
	public void setupJComboBoxes()
	{
		comboBoxes.clear();
		//generate labels in part selection panel
		JLabel lblDropDown1 = new JLabel ("Part1");
		c2.fill = c2.HORIZONTAL;
		c2.insets = new Insets(10, 20, 0, 20);
		c2.gridx = 0;
		c2.gridy = 0;
		c2.gridwidth = 1;
		c2.gridheight = 1;
		pnlPartSelection.add(lblDropDown1, c2);
		
		JLabel lblDropDown2 = new JLabel ("Part2");
		c2.gridx = 1;
		c2.gridy = 0;
		pnlPartSelection.add(lblDropDown2, c2);
		
		JLabel lblDropDown3 = new JLabel ("Part3");
		c2.gridx = 2;
		c2.gridy = 0;
		pnlPartSelection.add(lblDropDown3, c2);
		
		JLabel lblDropDown4 = new JLabel ("Part4");
		c2.gridx = 3;
		c2.gridy = 0;
		pnlPartSelection.add(lblDropDown4, c2);
		
		JLabel lblDropDown5 = new JLabel ("Part5");
		c2.gridx = 0;
		c2.gridy = 4;
		pnlPartSelection.add(lblDropDown5, c2);
		
		JLabel lblDropDown6 = new JLabel ("Part6");
		c2.gridx = 1;
		c2.gridy = 4;
		pnlPartSelection.add(lblDropDown6, c2);
		
		JLabel lblDropDown7 = new JLabel ("Part7");
		c2.gridx = 2;
		c2.gridy = 4;
		pnlPartSelection.add(lblDropDown7, c2);
		
		JLabel lblDropDown8 = new JLabel ("Part8");
		c2.gridx = 3;
		c2.gridy = 4;
		pnlPartSelection.add(lblDropDown8, c2);
		
		//generate JComboBox options and add each JComboBox to the comboBoxes ArrayList for future validation
		dropDown1 = new JComboBox(partList);
		dropDown1.setSelectedIndex(0);
				
		c2.gridx = 0;
		c2.gridy = 1;
		pnlPartSelection.add( dropDown1, c2 );
		
		dropDown2 = new JComboBox(partList);
		dropDown2.setSelectedIndex(0);
				
		c2.gridx = 1;
		c2.gridy = 1;
		pnlPartSelection.add( dropDown2, c2 );
		
		dropDown3 = new JComboBox(partList);
		dropDown3.setSelectedIndex(0);
				
		c2.gridx = 2;
		c2.gridy = 1;
		pnlPartSelection.add( dropDown3, c2 );
		
		dropDown4 = new JComboBox(partList);
		dropDown4.setSelectedIndex(0);
				
		c2.gridx = 3;
		c2.gridy = 1;
		pnlPartSelection.add( dropDown4, c2 );
		
		dropDown5 = new JComboBox(partList);
		dropDown5.setSelectedIndex(0);
		
		c2.gridx = 0;
		c2.gridy = 5;
		pnlPartSelection.add( dropDown5, c2 );
		
		dropDown6 = new JComboBox(partList);
		dropDown6.setSelectedIndex(0);
		
		c2.gridx = 1;
		c2.gridy = 5;
		pnlPartSelection.add( dropDown6, c2 );
		
		dropDown7 = new JComboBox(partList);
		dropDown7.setSelectedIndex(0);
				
		c2.gridx = 2;
		c2.gridy = 5;
		pnlPartSelection.add( dropDown7, c2 );

		dropDown8 = new JComboBox(partList);
		dropDown8.setSelectedIndex(0);
		
		c2.gridx = 3;
		c2.gridy = 5;
		pnlPartSelection.add( dropDown8, c2 );
		
		//messages
		c2.gridx = 5;
		c2.gridy = 7;
		c2.gridwidth = 4;
		c2.gridheight = 1;
		pnlPartSelection.add( lblMsg, c2 );
	}
	/** recreates dropDownKits JComboBox to match the Strings contained in kitList[] */
	public void setupJComboBoxes2()
	{
		//if dropDownKits combo box already exists, remove it before adding the updated one
		try
		{
			this.remove(dropDownKits);
		}
		catch(Exception e) {}
		//generate JComboBox options for kit  selection
		dropDownKits = new JComboBox(kitList);
				
		c.gridx = 3;
		c.gridy = 5;
		c.gridwidth = 1;
		add( dropDownKits, c );
		
		//generate JComboBox options for kit  selection
		dropDownKits.setSelectedIndex(0);
		dropDownKits.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ae) 
			{
				JComboBox cb = (JComboBox)ae.getSource();
				if(cb.getSelectedIndex() != 0)
				{
					String editKitName = (String)cb.getSelectedItem();
					Kit editKit = kitMap.get(editKitName);
					TreeMap<Integer, Part> tempMap = editKit.getParts();
					if(tempMap.containsKey(0))
						dropDown1.setSelectedItem(tempMap.get(0).getName());
					else
						dropDown1.setSelectedIndex(0);
					if(tempMap.containsKey(1))
						dropDown2.setSelectedItem(tempMap.get(1).getName());
					else
						dropDown2.setSelectedIndex(0);
					if(tempMap.containsKey(2))
						dropDown3.setSelectedItem(tempMap.get(2).getName());
					else
						dropDown3.setSelectedIndex(0);
					if(tempMap.containsKey(3))
						dropDown4.setSelectedItem(tempMap.get(3).getName());
					else
						dropDown4.setSelectedIndex(0);
					if(tempMap.containsKey(4))
						dropDown5.setSelectedItem(tempMap.get(4).getName());
					else
						dropDown5.setSelectedIndex(0);
					if(tempMap.containsKey(5))
						dropDown6.setSelectedItem(tempMap.get(5).getName());
					else
						dropDown6.setSelectedIndex(0);
					if(tempMap.containsKey(6))
						dropDown7.setSelectedItem(tempMap.get(6).getName());
					else
						dropDown7.setSelectedIndex(0);
					if(tempMap.containsKey(7))
						dropDown8.setSelectedItem(tempMap.get(7).getName());
					else
						dropDown8.setSelectedIndex(0);
					
					txtName.setText(editKitName);
					txtNumber.setText(editKit.getNumber() + "");
					txtInfo.setText(editKit.getDescription());
				}
			}
			
		});
	}
	/** enables interactivity of lblMsg by server through the client */
	public void setMsg( String s )
	{
		lblMsg.setText(s);
	}
}
