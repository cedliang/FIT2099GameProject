package game;

import java.util.Arrays;
import java.util.List;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.FancyGroundFactory;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.World;

/**
 * The main class for the zombie apocalypse game.
 *
 */
public class Application {

	public static void main(String[] args) {
		WorldExtended world = new WorldExtended(new Display());

		FancyGroundFactory groundFactory = new FancyGroundFactory(new Dirt(), new Fence(), new Tree());
		
		List<String> map = Arrays.asList(
		"................................................................................",
		"................................................................................",
		"....................................##########..................................",
		"..........................###########........#####..............................",
		"............++...........##......................########.......................",
		"..............++++.......#..............................##......................",
		".............+++...+++...#...............................#......................",
		".........................##..............................##.....................",
		"..........................#...............................#.....................",
		".........................##...............................##....................",
		".........................#...............................##.....................",
		".........................###..............................##....................",
		"...........................####....................########.....................",
		"..............................#########.........####............................",
		"............+++.......................#.........#...............................",
		".............+++++....................#.........#...............................",
		"...............++........................................+++++..................",
		".............+++....................................++++++++....................",
		"............+++.......................................+++.......................",
		"................................................................................",
		".........................................................................++.....",
		"........................................................................++.++...",
		".........................................................................++++...",
		"..........................................................................++....",
		"................................................................................");
		GameMap compound = new GameMap(groundFactory, map );
		world.addGameMap(compound);
		
		Actor player = new Player("Player", '@', 100);
		world.addPlayer(player, compound.at(42, 15));
		
	    // Place some random humans
		String[] humans = {"Carlton", "May", "Vicente", "Andrea", "Wendy",
				"Elina", "Winter", "Clem", "Jacob", "Jaquelyn"};
		int x, y;
		for (String name : humans) {
			do {
				x = (int) Math.floor(Math.random() * 20.0 + 30.0);
				y = (int) Math.floor(Math.random() * 7.0 + 5.0);
			} 
			while (compound.at(x, y).containsAnActor());
			//20% chance that a human is a farmer
			if (Math.random()>0.8) {
				compound.at(x,  y).addActor(new Farmer(name));	
			}
			else {
			compound.at(x,  y).addActor(new Human(name));
			}
		}
		
		//test portal
//		Vehicle portal=new Vehicle();
//		portal.setPortalDestination(compound.at(42, 15));
//		compound.at(44, 17).addItem(portal);
		
		// place a simple weapon
		compound.at(74, 20).addItem(new Plank());
		compound.at(43, 15).addItem(new SniperRifle());
		compound.at(50, 18).addItem(new SniperRifle());
		// FIXME: Add more zombies!
		compound.at(30, 20).addActor(new Zombie("Groan"));
		compound.at(30,  18).addActor(new Zombie("Boo"));
		compound.at(10,  4).addActor(new Zombie("Uuuurgh"));
		compound.at(50, 18).addActor(new Zombie("Mortalis"));
		compound.at(1, 10).addActor(new Zombie("Gaaaah"));
		compound.at(62, 12).addActor(new Zombie("Aaargh"));	
		
		compound.at(0, 0).addActor(new MamboMarie("Mambo Marie"));
		
		world.run();
	}
}
