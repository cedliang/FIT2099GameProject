package game;

public class Shotgun extends RangeWeaponItem {
	public Shotgun() {
		super("Shotgun", 'i',10,"bludgeons",25,"blasts");
		allowableActions.add(new ShootShotgunAction(this, "N"));
	}
	
}
