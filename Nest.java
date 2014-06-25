import java.io.*;
import java.util.ArrayList;

/** class constructs basic functionality of nest */
public class Nest implements Serializable {
	/** max number of parts per nest */
	public final int limit = 8; 
	/** parts are in the nest */
	public ArrayList<Part> nestedItems;
	/** true if nest is full */
	private boolean nestFull;
	/** true if nest is up */
	private boolean nestUp;

	/** Initialization */
	public Nest(){
		nestedItems = new ArrayList<Part>();
		nestFull = false;
		nestUp = true;
	}

	/** returns whether nest is full */
	public boolean isNestFull(){
		return nestFull;
	}

	/** returns whether nest is empty */
	public boolean isNestEmpty(){
		return nestedItems.isEmpty();
	}

	/** load part into nest, returns whether successful */
	public boolean addPart( Part p ){
		if (!nestUp) return true; // automatically dump part if nest is down
		if(nestedItems.size() < limit) {
			nestedItems.add(p);
			
			if(nestedItems.size() == limit){
				nestFull = true;
			}
			
			return true;
		} else { //nest full
			return false;
		}
	}

	/** remove part from nest */
	public Part removePart(){
		if( nestedItems.size() > 0 ){
			nestFull = false;
			return nestedItems.remove( 0 );
		} else {
			return null;
		}
	}

	/** dump nest out */
	public void dumpNest(){
		nestedItems = new ArrayList<Part>();
		nestFull = false;
	}

	/** setter for whether nest is up */
	public void setNestUp(boolean newNestUp){
		nestUp = newNestUp;
		if (!nestUp) dumpNest();
	}
	
	/** returns if nest is up */
	public boolean isNestUp(){
		return nestUp;
	}
}
