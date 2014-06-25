import java.io.Serializable;
import java.util.TreeMap;


/** defines a kit stand and its attributes upon which kits will be assembled */
@SuppressWarnings("serial")
public class KitStand implements Serializable
{
	/** 0-1 are positions for incomplete kits, 2 is the inspection position */
	TreeMap<Integer,Kit> kits;
	/** used to access gui kit camera data */
	GUIKitCamera guiKitCamera;
	/** Constructor */
	public KitStand()
	{
		kits = new TreeMap<Integer,Kit>();
		//guiKitCamera = new GUIKitCamera( new KitCamera() );  //commented out for v.0
	}
}
