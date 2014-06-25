import java.io.*;

/** networking message specifying kits to produce in factory */
public class ProduceKitsMsg implements Serializable {
	/** kit number corresponding to type of kit to produce */
	public int kitNumber;
	/** how many new kits to produce */
	public int howMany;

	/** constructor to set up ProduceKitsMsg with the number of the kit and how many kits should be produced */
	public ProduceKitsMsg(int newKitNumber, int newHowMany) {
		kitNumber = newKitNumber;
		howMany = newHowMany;
	}
}
