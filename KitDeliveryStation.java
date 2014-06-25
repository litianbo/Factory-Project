import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
/** This class contains all the information about the state of the kit delivery station. */
public class KitDeliveryStation implements Serializable {
	/** pallets contains empty kits */
	ArrayList<Pallet> pallets;
	/** Contructor */
	public KitDeliveryStation() {
		pallets = new ArrayList<Pallet>();
	}
}
