import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class KitRobotDemo extends JPanel
{
	GUIKitDeliveryStation guiKitDeliveryStation;
	GUIKitRobot guiKitRobot;
	GUIKitStand guiKitStand;
	
	int paintCount = 0;
	
	private enum Status {
		GETKITFROMINCONVEYOR, PUTKITONSTAND, GETKITFROMSTAND, TAKINGPICTURE, PUTKITONOUTCONVEYOR, IDLE
	}
	
	private Status status = Status.IDLE;
	
	public KitRobotDemo()
	{
		this.setPreferredSize(new Dimension(800,600));
		
		Painter.loadImages();
		
		guiKitDeliveryStation = new GUIKitDeliveryStation(new KitDeliveryStation(), new GUILane(new Lane(), false, 8, 300,10), new GUILane(new Lane(), false, 3, 300-180, 10), 10, 10);
		guiKitDeliveryStation.turnOn(System.currentTimeMillis());
		guiKitRobot = new GUIKitRobot(new KitRobot(), new Point2D.Double(350, 250));
		guiKitStand = new GUIKitStand();
				
		guiKitDeliveryStation.inConveyor.addEmptyPallet(System.currentTimeMillis());
	}
	
	public void paint(Graphics gfx)
	{
		long currentTime = System.currentTimeMillis();

		checkStatus(currentTime);
		
		Graphics2D g = (Graphics2D)gfx;
		
		guiKitDeliveryStation.draw(g, currentTime);
		guiKitStand.draw(g, currentTime);
		guiKitRobot.draw(g, currentTime);
		
		paintCount++;
	}
	
	private void checkStatus(long currentTime)
	{
		guiKitDeliveryStation.checkStatus(currentTime);

		if (status == Status.IDLE && guiKitDeliveryStation.inConveyor.hasFullPalletAtEnd(currentTime))
		{
			guiKitRobot.movement = guiKitRobot.movement.moveToAtSpeed(currentTime, guiKitDeliveryStation.inConveyor.getItemLocation(0, currentTime), 0, 200);
			status = Status.GETKITFROMINCONVEYOR;
			return;
		}
		
		if (status == Status.GETKITFROMINCONVEYOR && guiKitRobot.arrived(currentTime))
		{
			guiKitRobot.kitRobot.setKit(guiKitDeliveryStation.inConveyor.removeEndPalletKit());
			guiKitRobot.movement = guiKitRobot.movement.moveToAtSpeed(currentTime, guiKitStand.getCameraStationLocation(), 0, 200);
			status = Status.PUTKITONSTAND;
			return;
		}
		
		if (status == Status.PUTKITONSTAND && guiKitRobot.arrived(currentTime))
		{
			guiKitStand.addKit(new GUIKit(guiKitRobot.kitRobot.removeKit(), 0, 0), 2);
			guiKitRobot.park(currentTime);
			status = Status.TAKINGPICTURE;
			return;
		}
		
		if (status == Status.TAKINGPICTURE && guiKitRobot.arrived(currentTime))
		{
			guiKitRobot.movement = guiKitRobot.movement.moveToAtSpeed(currentTime, guiKitStand.getCameraStationLocation(), 0, 200);
			status = Status.GETKITFROMSTAND;
			return;
		}
		
		if (status == Status.GETKITFROMSTAND && guiKitRobot.arrived(currentTime))
		{
			guiKitRobot.kitRobot.setKit(guiKitStand.removeKit(2).kit);
			guiKitRobot.movement = guiKitRobot.movement.moveToAtSpeed(currentTime, guiKitDeliveryStation.getOutConveyorLocation(), 0, 200);
			status = Status.PUTKITONOUTCONVEYOR;
			return;
		}
		
		if (status == Status.PUTKITONOUTCONVEYOR && guiKitRobot.arrived(currentTime))
		{
			guiKitDeliveryStation.outConveyor.addItem(new GUIPallet(new Pallet(guiKitRobot.kitRobot.removeKit()), 0, 0),
				new Point2D.Double(guiKitDeliveryStation.outConveyor.getPos().x-50+guiKitDeliveryStation.outConveyor.getLength(), 0), currentTime);
			guiKitRobot.park(currentTime);
			status = Status.IDLE;
			return;
		}
		
		
		if (guiKitDeliveryStation.outConveyor.hasFullPalletAtEnd(currentTime))
		{
			guiKitDeliveryStation.outConveyor.removeItem(guiKitDeliveryStation.outConveyor.endItem(0), currentTime);
			guiKitDeliveryStation.inConveyor.addEmptyPallet(currentTime);
		}
	}
	
}














