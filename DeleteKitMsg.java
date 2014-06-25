import java.io.*;

/** networking message indicating to delete a kit */
public class DeleteKitMsg implements Serializable {
	/** kit number of kit to delete */
	public int number;

	/** constructor to set up DeleteKitMsg with the number of the kit that should be deleted */
	public DeleteKitMsg(int delNumber) {
		number = delNumber;
	}
}
