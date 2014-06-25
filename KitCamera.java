import java.io.Serializable;


@SuppressWarnings("serial")
public class KitCamera implements Serializable {
	/** true if kit camera is taking picture */
	boolean takingPicture;
	/** initialize variable */
	public KitCamera () {
		takingPicture = false;
	}
	/** set takingPicture to true */
	public void takePicture() {
		takingPicture = true;
	}
}
