import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * class contains the panel which have graphic view of kit assembly manager and
 * button panel which switch between grahpic view and break panel
 */
public class KitAssemblyManager extends JPanel implements ActionListener {
	/** button for switching to KitAssembly graphic panel */
	private JButton btnKA;
	/** button for switching to break panel */
	private JButton btnBreak;
	/** button panel that contains the above two buttons  */
	private JPanel buttonPanel;
	/** main panel that contains graphic panel and break panel */
	private JPanel mainPanel;
	/** cardlayout */
	private CardLayout cardlayout;
	/** panel for graphic view of kit assembly manager */
	private KitAssemblyGraphics pnlkag;
	
	private KitAssemblyBreak pnlkab;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/** Initialization */
	public KitAssemblyManager(KitAssemblyClient kac) {
		setLayout(new BorderLayout());
		pnlkab = new KitAssemblyBreak(kac);
		cardlayout = new CardLayout();
		mainPanel = new JPanel();
		mainPanel.setLayout(cardlayout);
		btnKA = new JButton("Kit Assembly View");
		// nothing to do now...
		btnBreak = new JButton("Break Panel");
		btnKA.addActionListener(this);
		btnBreak.addActionListener(this);
		pnlkag = new KitAssemblyGraphics();
		buttonPanel = new JPanel();
		buttonPanel.add(btnKA);
		buttonPanel.add(btnBreak);
		mainPanel.add(pnlkag, "kag");
		mainPanel.add(pnlkab,"kab");
		add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnKA) {
			cardlayout.first(mainPanel);

		}
		if (e.getSource() == btnBreak) {
			cardlayout.last(mainPanel);

		}
	}
	/** return the instance of KitAssemblyGraphics */
	public KitAssemblyGraphics getKitAssemblyGraphics() {

		return pnlkag;
	}

}
