import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class FactoryProductionManager extends JPanel implements ActionListener {
	
	/** cardlayout to switch between connection panel and part manager */
	private CardLayout cardlayout = new CardLayout();
	/** FactoryProductionSchedulePanel variable */
	public FactoryProductionSchedulePanel fpsp;
	/** FactoryProductionButtonPanel variable */
	private FactoryProductionButtonPanel fpbp;
	/** FactoryProductionViewPanel variable */
	private FactoryProductionViewPanel fpvp;
	/** mainpanel that contains all three panels above */
	private JPanel mainpanel;

	/** initialize variables */
	public FactoryProductionManager(){
		this.setPreferredSize(FactoryPainter.getAreaSize(FactoryPainter.FactoryArea.ENTIRE_FACTORY));
		this.setSize(FactoryPainter.getAreaSize(FactoryPainter.FactoryArea.ENTIRE_FACTORY));
		
		setLayout(new BorderLayout());
		mainpanel = new JPanel();
		mainpanel.setLayout(cardlayout);
		fpsp = new FactoryProductionSchedulePanel();
		fpbp = new FactoryProductionButtonPanel();
		fpvp = new FactoryProductionViewPanel();
		mainpanel.add(fpsp,"fpsp");
		mainpanel.add(fpvp,"fpvbp");
		fpbp.btnSwitchSchedule.addActionListener(this);
		fpbp.btnSwitchView.addActionListener(this);
		add(mainpanel,BorderLayout.CENTER);
		add(fpbp,BorderLayout.SOUTH);
		
	}
	

	@Override
	/** handle user input when he switches between two panels */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == fpbp.btnSwitchSchedule){
			cardlayout.first(mainpanel);
			
		}
		
		if(e.getSource() == fpbp.btnSwitchView){
			validate();
			repaint();
			cardlayout.last(mainpanel);
		}
	}
	/** return FactoryProductionViewPanel variable */
	public FactoryProductionViewPanel getViewPanel() {
		return fpvp;
	}
}
