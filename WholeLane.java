import java.util.ArrayList;

/** Control the lane implementation
    (THIS CLASS IS DEPRECATED AND ONLY USED IN V0) */
public class WholeLane {
	/** initialize variables */
	private Feeder myFeeder;
	/** initialize variables */
	private Nest myTopNest;
	/** initialize variables */
	private Nest myBotNest;
	/** lane instance */
	private Lane myLane;
	
	/** constructor for WholeLane class */
	public WholeLane(){
		myFeeder = new Feeder();
		myLane = new Lane();
		myTopNest = new Nest();
		myBotNest = new Nest();
	}

	/** change the lane */
	public void divert() {
		myFeeder.changeLane();
	}

	/** getter for lane */
	public Lane getLane(){
		return myLane;
	}
	/** return Feeder variable  */
	public Feeder getFeeder() {
		return myFeeder;
	}
	/** fill the feeder by parts  */
	public void fillFeeder(ArrayList<Part> load) {
		myFeeder.loadParts(load);
	}
	
	/** return the which lane the parts are going  */
	public int getActiveLane(){
		return (myFeeder.getDiverter() < 0) ? 1 : 2;
	}
	/** return the speed  */
	public double getSpeed() {
		return myLane.getSpeed();
	}
}
