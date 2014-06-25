import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * this class calls FactoryPainter to draw everything in the Kit Assembly
 * Manager
 */
@SuppressWarnings("serial")
public class KitAssemblyGraphics extends JPanel {
	/** variable of FactoryPainter that draws graphic stuffs */
	private FactoryPainter painter;

	/**
	 * @param args
	 */
	/** Initialization */
	public KitAssemblyGraphics() {
		painter = new FactoryPainter();
		
	}
	/** call drawKitAssembly in painter to draw */
	public void paint(Graphics gfx) {
		Graphics2D g = (Graphics2D) gfx;

		BufferedImage factoryImg = painter.drawFactoryArea(FactoryPainter.FactoryArea.KIT_MANAGER);
		g.drawImage(factoryImg, 0, 0, null);

	}

	public void setFactoryState(FactoryStateMsg factoryState) {
		painter.setFactoryState(factoryState);
	}
	/** update the updateMsg */
	public void update(FactoryUpdateMsg updateMsg) {
		painter.update(updateMsg);
	}
}
