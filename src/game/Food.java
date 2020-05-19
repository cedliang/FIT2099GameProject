package game;


public class Food extends PortableItem {

	
	//can be expanded to be an abstract class to support different food types with different effects and healFactors
	private int healFactor;
	public Food() {
		super("food",'f');
		healFactor = 30;
	}

	public int getHealFactor() {
		return healFactor;
	}
	
}


