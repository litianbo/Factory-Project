import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class LaneGraphics extends JPanel {
	/** value of update rate of LaneGraphics */
	public static final int UPDATE_RATE = 50;
	/** FactoryPainter for drawing factory */
	private FactoryPainter painter;
	/** Initialize */
	public LaneGraphics() {
		this.setPreferredSize(FactoryPainter.getAreaSize(FactoryPainter.FactoryArea.LANE_MANAGER));
		painter = new FactoryPainter();
	}
	/** Sets factory state in FactoryPainter */
	public void setFactoryState(FactoryStateMsg factoryState)
	{
		painter.setFactoryState(factoryState);
	}
	/** Updates factory state in FactoryPainter */
	public void update(FactoryUpdateMsg updateMsg)
	{
		painter.update(updateMsg);
	}
	/** Draws the lane are of the factory */
	public void paint(Graphics gfx)
	{
		Graphics2D g = (Graphics2D)gfx;
		
		BufferedImage factoryImg = painter.drawFactoryArea(FactoryPainter.FactoryArea.LANE_MANAGER);
		g.drawImage(factoryImg, 0, 0, null);
	}
}
