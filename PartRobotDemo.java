import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.*;

public class PartRobotDemo extends JPanel implements ActionListener {
	ArrayList<GUINest> nests;
	GUIKitStand guiKitStand;
	GUIPartRobot guiPartRobot;
	int paintCount = 0, timerFireCount = 0;
	Timer moveTimer, cameraTimer;
	GUIFlash camFlash;
	
	public PartRobotDemo() {

		this.setPreferredSize(new Dimension(800,600));
		
		Painter.loadImages();
		
		nests = new ArrayList<GUINest> ();
		guiKitStand = new GUIKitStand();
		guiPartRobot = new GUIPartRobot( new PartRobot(), new Point2D.Double(350, 340) );
		moveTimer = new Timer( 5000, this );
		cameraTimer = new Timer( 500, this );
		
		guiKitStand.addKit( new GUIKit( new Kit(), guiKitStand.getCameraStationLocation().x, guiKitStand.getCameraStationLocation().y ), 2 );
		
		nests.add( new GUINest( new Nest(), 580, 285 ) );
		nests.get(0).addPart( new GUIPart( new Part(), nests.get(0).movement.getStartPos().x + 25, nests.get(0).movement.getStartPos().y + 25, Math.PI/-2 ) );
		
		camFlash = null;
		
		moveTimer.start();	
	}
	
	public void paint(Graphics gfx)
	{
		long currentTime = System.currentTimeMillis();
		
		Graphics2D g = (Graphics2D)gfx;
		
		guiKitStand.draw(g, currentTime);
		nests.get(0).draw( g, currentTime );
		guiPartRobot.draw(g, currentTime);
		
		if( camFlash != null ){
			camFlash.draw(g, currentTime);
		}
		
		paintCount++;
	}
	
	public void actionPerformed( ActionEvent ae ) {
		if ( ae.getSource() == moveTimer ) {
			long currentTime = System.currentTimeMillis();
			if ( timerFireCount % 5 == 0 ) {
				// move part robot towards nest
				guiPartRobot.movement = guiPartRobot.movement.moveToAtSpeed(currentTime, new Point2D.Double( nests.get(0).movement.getStartPos().x - 30, nests.get(0).movement.getStartPos().y + 50 ), 0, 200 );
			}
			
			else if ( timerFireCount % 5 == 1 ) {
				// pick up part and move towards kit stand
				guiPartRobot.addPartToGripper( 2, nests.get(0).removePart( 0 ) );
				guiPartRobot.movement = guiPartRobot.movement.moveToAtSpeed(currentTime, new Point2D.Double( guiKitStand.getCameraStationLocation().x - 50, guiKitStand.getCameraStationLocation().y - 50 ), 0, 200);
			}
			
			else if ( timerFireCount % 5 == 2 ) {
				// drop off part and move back to start position
				guiKitStand.getKit( 2 ).kit.addPart( 3, guiPartRobot.removePartFromGripper( 2 ).part );
				guiPartRobot.movement = guiPartRobot.movement.moveToAtSpeed(currentTime, new Point2D.Double( guiPartRobot.getBasePos().x, guiPartRobot.getBasePos().y + 180), 0, 200 );
			}
			
			else if ( timerFireCount % 5 == 3 ) {
				camFlash = new GUIFlash( -10, 280); //make flash appear
				cameraTimer.start();
			}
			
			else if ( timerFireCount % 5 == 4 ) {
				// reset part to nest
				guiKitStand.getKit(2 ).kit.removePart( 3 );
				nests.get(0).addPart( new GUIPart( new Part(), nests.get(0).movement.getStartPos().x + 25, nests.get(0).movement.getStartPos().y + 25, Math.PI/-2 ) );
			}
			
			timerFireCount++;
		}
		
		if ( ae.getSource() == cameraTimer ) {
			cameraTimer.stop();
			camFlash = null;
		}
		
	}
}
