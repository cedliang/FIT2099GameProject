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
		
		List<String> compoundmap = Arrays.asList(
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
		GameMap compound = new GameMap(groundFactory, compoundmap );
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
		
		
		// place a simple weapon
		compound.at(74, 20).addItem(new Plank());
		// FIXME: Add more zombies!
		compound.at(30, 20).addActor(new Zombie("Groan"));
		compound.at(30,  18).addActor(new Zombie("Boo"));
		compound.at(10,  4).addActor(new Zombie("Uuuurgh"));
		compound.at(50, 18).addActor(new Zombie("Mortalis"));
		compound.at(1, 10).addActor(new Zombie("Gaaaah"));
		compound.at(62, 12).addActor(new Zombie("Aaargh"));	
		
		compound.at(0, 0).addActor(new MamboMarie("Mambo Marie"));
		
		
		
		//create town
		List<String> townmap = Arrays.asList(
				"................................................................................",
				"................................................................................",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............######.######.......######.######.......######.######...............",
				"...........+.............+.....+.............+.....+.............+..............",
				"................................................................................",
				"................................................................................",
				"................................................................................",
				"...........+.............+.....+.............+.....+.............+..............",
				"............######.######.......######.######.......######.######...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"............#############.......#############.......#############...............",
				"................................................................................",
				".............................................................+++................",
				".............................++++............................+++++++............",
				"................................................................++..+...........",
				".............++++......................++++.....................................",
				"..............++................................................................",
				".............+...............++.................................................",
				"................................................................................"
				);
		GameMap town = new GameMap(groundFactory, townmap );
		world.addGameMap(town);
		
		//portal to town
		compound.at(44, 17).addItem(new Vehicle(town.at(1, 12)));
		//portal from town
		town.at(1, 12).addItem(new Vehicle(compound.at(44, 17)));
		
		town.at(18, 9).addItem(new SniperRifle());

		//houses
		//doors are house1(18,6), house2(18,12), house3(38,6), house4(38,12), house5(58,6), house6(58,12)
		List<String> houseup= Arrays.asList(
				"######.######",
				"#...........#",
				"#...........#",
				"#...........#",
				"#############"
				);
		List<String> housedown= Arrays.asList(
				"#############",
				"#...........#",
				"#...........#",
				"#...........#",
				"######.######"
				);
		GameMap house1 = new GameMap(groundFactory, housedown );
		GameMap house2 = new GameMap(groundFactory, houseup );
		GameMap house3 = new GameMap(groundFactory, housedown );
		GameMap house4 = new GameMap(groundFactory, houseup );
		GameMap house5 = new GameMap(groundFactory, housedown );
		GameMap house6 = new GameMap(groundFactory, houseup );
		world.addGameMap(house1);
		world.addGameMap(house2);
		world.addGameMap(house3);
		world.addGameMap(house4);
		world.addGameMap(house5);
		world.addGameMap(house6);
		

		town.at(18, 6).addItem(new Door(house1.at(6, 4)));
		house1.at(6, 4).addItem(new Door(town.at(18, 6)));
		
		town.at(18, 12).addItem(new Door(house2.at(6, 0)));
		house2.at(6, 0).addItem(new Door(town.at(18, 12)));

		town.at(38, 6).addItem(new Door(house3.at(6, 4)));
		house3.at(6, 4).addItem(new Door(town.at(38, 6)));
		
		town.at(38, 12).addItem(new Door(house4.at(6, 0)));
		house4.at(6, 0).addItem(new Door(town.at(38, 12)));
		
		town.at(58, 6).addItem(new Door(house5.at(6, 4)));
		house5.at(6, 4).addItem(new Door(town.at(58, 6)));
		
		town.at(58, 12).addItem(new Door(house6.at(6, 0)));
		house6.at(6, 0).addItem(new Door(town.at(58, 12)));
		
		
		
		
		
		
		
		
		
		world.run();
	}
}
