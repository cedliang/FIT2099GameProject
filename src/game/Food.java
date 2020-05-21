package game;

/**
 * A special type of PortableItem that can be consumed by Actors to restore HP. It does this by 
 * possessing EatFoodAction instances in its allowableActions array. Also stores information about
 * how much HP it will heal.
 *
 *@author Cedric Liang, Nathan Vaughan
 */
public class Food extends PortableItem {

	
	//can be expanded to be an abstract class to support different food types with different effects and healFactors
	private int healFactor;
	public Food() {
		super("food",'+');
		healFactor = 30;
		this.allowableActions.add(new EatFoodAction(this));
	}

	/**
	 * Retrieves the amount of HP the Food will heal.
	 *
	 * @return the amount of HP the Food will heal
	 */
	public int getHealFactor() {
		return healFactor;
	}
	
}


