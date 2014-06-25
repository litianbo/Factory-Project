import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class FactoryProductionViewPanel extends JPanel implements MouseMotionListener
{
	/** interval between timer ticks in milliseconds
            (is lower than Server.UPDATE_RATE because this is draw rate, not movement update rate) */
	public static final int UPDATE_RATE = 50;
	
	//Merge with Clayton's FactoryManagerView 
	
	/**
	 * Member Data:
	 * FactoryPainter painter
	 * 
	 */
	
	private FactoryPainter painter;
	
	private int mouseX = 0, mouseY = 0;

	//the following is Clayton's code for full-view factory 
	public FactoryProductionViewPanel()
	{
		this.setPreferredSize(FactoryPainter.getAreaSize(FactoryPainter.FactoryArea.ENTIRE_FACTORY));
		this.addMouseMotionListener(this);
		
		painter = new FactoryPainter();
	}

	public void setFactoryState(FactoryStateMsg factoryState)
	{
		painter.setFactoryState(factoryState);
	}

	public void update(FactoryUpdateMsg updateMsg)
	{
		painter.update(updateMsg);
	}
	
	public void paint(Graphics gfx)
	{
		Graphics2D g = (Graphics2D)gfx;
		
		BufferedImage factoryImg = painter.drawEntireFactory();
		g.drawImage(factoryImg, 0, 0, null);
		
		g.drawString("x: " + mouseX + " Y: " + mouseY, 1500, 790);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		mouseX = arg0.getX();
		mouseY = arg0.getY();
	}
	
	
	

}
