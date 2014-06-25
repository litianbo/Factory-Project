import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class FactoryProductionButtonPanel extends JPanel{

	JButton btnSwitchSchedule, btnSwitchView; 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public FactoryProductionButtonPanel(){
		//initialize buttons in button panel
		btnSwitchSchedule = new JButton("Schedule");
		btnSwitchView = new JButton("Factory View");
		add(btnSwitchSchedule);
		add(btnSwitchView);
	}
}
