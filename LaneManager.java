import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class LaneManager extends JPanel {
	/** LanesClient variable for interacting with LaneClient */
	private LanesClient myClient;
	/** panel for laying out graphics panel and break panel */
	private JPanel panelLayout;
	/** cardlayout for switching between lane and break panels */
	private CardLayout cLayout;
	/** graphics panel */
	private LaneGraphics graphics;
	/** break panel*/
	private BreakLanePanel breaker;
	/** Initialize */
	public LaneManager( LanesClient lc ) {
		myClient = lc;
		graphics = new LaneGraphics();
		breaker = new BreakLanePanel( lc );
		breaker.setBorder( BorderFactory.createLineBorder( Color.black ) );
		
		panelLayout = new JPanel();
		cLayout = new CardLayout();
		panelLayout.setLayout( new BorderLayout() );
		
		//layout graphics and break panel in center
		panelLayout.add( graphics, BorderLayout.CENTER );
		panelLayout.add( breaker, BorderLayout.EAST );
		
		//layout panels
		setLayout( new BorderLayout() );
		add( panelLayout, BorderLayout.CENTER );
	}
	
	/** gets NetComm for LaneClient */
	public NetComm getCom(){
		return myClient.getCom();
	}
	/** sets factory state in graphics panel */
	public void setFactoryState(FactoryStateMsg factoryState)
	{
		graphics.setFactoryState(factoryState);
	}
	/** updates factory state in graphics panel */
	public void update(FactoryUpdateMsg updateMsg)
	{
		graphics.update(updateMsg);
	}
}
