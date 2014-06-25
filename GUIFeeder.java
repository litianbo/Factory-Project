import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
/** Contains data and methods for drawing and animating a feeder */
public class GUIFeeder implements GUIItem, Serializable {
	/** used to access feeder data */
	public Feeder feeder;
	/** used to access movement data */
	public Movement movement;

	/** Initialization */
	public GUIFeeder(Feeder feeder, double x, double y)
	{
		this.feeder = feeder;
		movement = new Movement(new Point2D.Double(x,y), 0);
	}
	/** draws the feeder */
	public void draw( Graphics2D g, long currentTime ){
		Painter.draw(g, Painter.ImageEnum.FEEDER, currentTime, movement, true);
		// purge bins too easily confused with the feeders themselves, so commenting this out
		/*if(feeder.getPurgeBin() != null){
			new GUIBin(feeder.getPurgeBin(), movement.offset(new Point2D.Double(140, 0), 0)).draw( g, currentTime );
		}*/
	}

	/** setter for movement */
	public void setMove(Movement movement)
	{
		this.movement = movement;
	}

	/** getter for movement */
	public Movement getMove() {
		return movement;
	}
	
	/** turns feeder on if x is true else it turns it off */
	public void setFeederOn( boolean x ){
		if( x ){
			feeder.turnOn();
		} else {
			feeder.turnOff();
		}
	}
	
	/** raises gate if x is true else it lowers the gate */
	public void setGateRaised( boolean x ){
		if( x ){
			feeder.raiseGate();
		} else {
			feeder.lowerGate();
		}
	}
	
	/** starts feeding if x is true else it stops feeding */
	public void setFeeding( boolean x ){
		if( x ){
			feeder.startFeeding();
		} else {
			feeder.stopFeeding();
		}
	}
	
	/** changes diverter to alternate position */
	public void setDiverter( int newDiverter ) {
		feeder.setDiverter( newDiverter );
	}

	/** returns lane number that parts are fed to (-1 = bottom, 1 = top)*/
	public int getDiverter(){
		return feeder.getDiverter();
	}

	/** load parts into feeder */
	public void loadParts( ArrayList<Part> load ){
		feeder.loadParts(load);
	}

	/** load bin into feeder */
	public void loadBin(Bin load) {
		feeder.loadBin(load);
	}
	
	/** empties the feeder into purge bin */
	public void purge( Bin purged ){
		feeder.purge( purged );
	}

	/** return part fed */
	public Part feedPart(long currentTime){
		return feeder.feedPart(currentTime);
	}
	
	/** turn on feeder */
	public void turnOn(){
		feeder.turnOn();
	}
	
	/** turn off feeder */
	public void turnOff(){
		feeder.turnOff();
	}
	
	/** returns if the feeder is on */
	public boolean isOn(){
		return feeder.isOn();
	}
	
	/** return if the feeder is feeding */
	public boolean isFeeding(){
		return feeder.isFeeding();
	}
	
	/** return if feeder gate is lowered */
	public boolean isGateRaised(){
		return feeder.isGateRaised();
	}
	
	/** returns number of parts fed */
	public int partsFed(){
		return feeder.partsFed();
	}
	
	/** resets fedCount to 0 */
	public void resetCount(){
		feeder.resetCount();
	}
	
	public boolean getPartsLow() {
		return feeder.checkIfLow();
	}
	
	public ArrayList<Part> getParts() {
		return feeder.getParts();
	}
}
