package bots;

import elf_kingdom.*;
import elf_kingdom.Game;

class Functions {

	public static int abs(int a) {
		if (a < 0) {
			a *= -1;
		}
		return a;
	}

	public static int min(int a, int b) {
		if (a < b)
			return a;
		return b;
	}
	
	public static int howMany(Location location , GameObject[] objects, int range) {
		int counter = 0;
		for(int i = 0; i < objects.length; i++) {
			if(objects[i].distance(location) <= range) {
				counter++;
			}
		}
		
		return counter;
	}
	
	public static GameObject closestTo(GameObject object, GameObject[] objects) {
		if (objects.length > 0) {
			GameObject a = objects[0];
			for (int i = 0; i < objects.length; i++) {
				if (objects[i].distance(object) < a.distance(object)) {
					a = objects[i];
				}
			}
			return a;
		}
		return null;
	}

	public static Elf elfClosestTo(Location location, Elf[] elves) {
		if (elves.length > 0) {
			Elf a = elves[0];
			for (int i = 0; i < elves.length; i++) {
				if (a.distance(location) > elves[i].distance(location)) {
					a = elves[i];
				}
			}
			return a;
		}
		return null;
	}

	public static GameObject closestTo(Location location, GameObject[] objects) {
		if (objects.length > 0) {
			GameObject a = objects[0];
			for (int i = 0; i < objects.length; i++) {
				if (a.distance(location) > objects[i].distance(location)) {
					a = objects[i];
				}
			}
			return a;
		}
		return null;
	}

	public static void attackObject(Elf elf, GameObject object) {
		if (object != null) {
			if (elf.alreadyActed == false) {
				if (elf.inAttackRange(object)) {
					elf.attack(object);
				} else {
					elf.moveTo(object);
				}
			}
		}
	}

	public static void buildPortal(Elf elf, Location location, Game game) {
		if(elf != null) {
			System.out.println("location 1: "+location );
			location = locationChecker(game, location);
			System.out.println("location 2: "+location);
			if (elf.alreadyActed == false) {
				if (elf.getLocation().equals(location)) {
					if (elf.canBuildPortal()) {
						elf.buildPortal();
					}
				}
				else {
					elf.moveTo(location);
				}
			}
		}	
	}	
	
	public static void buildManaFountain(Elf elf, Location location, Game game) {
		if(elf != null) {
			if (elf.alreadyActed == false) {
				if (elf.getLocation().equals(location)) {
					if (elf.canBuildManaFountain()) {
						elf.buildManaFountain();
					}
				} 
				else {
					elf.moveTo(location);
				}
			}
		}
	}
	
	public static boolean enemyInRange(Game game, int range) {

		for (Portal temp : game.getEnemyPortals()) {
			if (temp.distance(game.getMyCastle()) < range) {
				return true;
			}
		}
		for (Elf temp : game.getEnemyLivingElves()) {
			if (temp.distance(game.getMyCastle()) < range) {
				return true;
			}
		}
		for (Creature temp : game.getEnemyCreatures()) {
			if (temp.distance(game.getMyCastle()) < range) {
				return true;
			}
		}
		return false;
	}

	public static boolean inRange(GameObject object, GameObject secondObject, int distance) {
		if (object.distance(secondObject) < distance) {
			return true;
		}
		return false;
	}

	public static boolean inRange(GameObject object, GameObject[] objects, int distance) {

		for (GameObject temp : objects) {
			if (temp.distance(object) < distance)
				return true;
		}
		return false;
	}

	public static Location locationChecker(Game game, Location location) {
		
		Building[] buildings = game.getAllBuildings(); 
		for(int i = 0 ; i < buildings.length; i++) {
			if(location.distance(buildings[i]) < 2*buildings[i].size) {
				location = new Location(buildings[i].location.row, buildings[i].location.col-2*buildings[i].size-100);
				if(buildings[i].location.col < 2500) {
					location = new Location(buildings[i].location.row, buildings[i].location.col+2*buildings[i].size+100);
				}
			}
		}
		
		return location;
	}
	public static void defensiveMode(Game game, int range) {
		
		for (int i = 0; i < game.getMyLivingElves().length; i++) {
			
			int a = howMany(game.getMyLivingElves()[i].getLocation(), game.getEnemyLivingElves(), game.getMyLivingElves()[i].attackRange);//num of enemyElves close To my elf
			int b = howMany(game.getMyLivingElves()[i].getLocation(), game.getMyLivingElves(), game.getMyLivingElves()[i].attackRange);//num of My elves close to my elf
		
			if (i % 2 == 0) {
				
				if (game.getEnemyLivingElves().length > 0) {
					if (closestTo(game.getMyCastle(), game.getEnemyLivingElves())
							.distance(game.getMyCastle()) < range) {
						if(howMany(game.getMyLivingElves()[i].location, game.getEnemyLivingElves(), 3000)>1) {
						
							attackObject(game.getMyLivingElves()[i],
								closestTo(game.getMyCastle(), game.getEnemyLivingElves()));
						}
					}
				}
				if (game.getEnemyCreatures().length > 0) {
					if (closestTo(game.getMyCastle(), game.getEnemyCreatures()).distance(game.getMyCastle()) < range) {
						attackObject(game.getMyLivingElves()[i],
								closestTo(game.getMyCastle(), game.getEnemyCreatures()));
					}
				}
				if (game.getEnemyPortals().length > 0) {
					if (closestTo(game.getMyCastle(), game.getEnemyPortals()).distance(game.getMyCastle()) < 10000) {
						attackObject(game.getMyLivingElves()[i],
								closestTo(game.getMyLivingElves()[i], game.getEnemyPortals()));
					}
				}

			}

			else {
				
				if (game.getEnemyPortals().length > 0) {
					if (closestTo(game.getMyCastle(), game.getEnemyPortals()).distance(game.getMyCastle()) < 10000) {
						attackObject(game.getMyLivingElves()[i],
								closestTo(game.getMyLivingElves()[i], game.getEnemyPortals()));
					}
				}
				if (game.getEnemyCreatures().length > 0) {
					if (closestTo(game.getMyCastle(), game.getEnemyCreatures()).distance(game.getMyCastle()) < range) {
						attackObject(game.getMyLivingElves()[i],
								closestTo(game.getMyCastle(), game.getEnemyCreatures()));
					}
				}
				if (game.getEnemyLivingElves().length > 0) {
					if (closestTo(game.getMyCastle(), game.getEnemyLivingElves())
							.distance(game.getMyCastle()) < range) {
						attackObject(game.getMyLivingElves()[i],
								closestTo(game.getMyCastle(), game.getEnemyLivingElves()));
					}
				}

			}
		}
	}

	public static void offenceMode(Game game) { 
		
	}

	public static void ManapreparationMode(Game game) { 
		
		if (game.getEnemyManaFountains().length != 0) {
			int i = game.getEnemyManaFountains().length - game.getMyManaFountains().length - 1;
			if(i >=0 && game.getMyLivingElves().length>0) {
				Location manaclose = game.getEnemyManaFountains()[i].location;

				int xEnemyMana = manaclose.getLocation().col;
				int yEnemyMana = manaclose.getLocation().row;
				
				int xEnemyCastle = game.getEnemyCastle().getLocation().col;
				int yEnemyCastle = game.getEnemyCastle().getLocation().row;
				
				int xMyCastle = game.getMyCastle().getLocation().col;
				int yMyCastle = game.getMyCastle().getLocation().row;
				
				int d = xEnemyCastle-xEnemyMana;
				int x = xMyCastle+d;
				int y;
				if(d != 0) {
					int m = (yEnemyMana-yEnemyCastle)/(xEnemyMana-xEnemyCastle);
					y = m*d+yMyCastle;
				}
				else {
					y = yMyCastle+yEnemyCastle-yEnemyMana;
				}
				
				Location myMana = new Location(y,x);
				Elf elf = elfClosestTo(myMana, game.getMyLivingElves());
				buildManaFountain(elf, myMana, game);
			}
		}
		else {
			
		}
	}

	public static void PortalPreparation (Game game) {
		
		if(game.getEnemyPortals().length>0) {
			Location enemyPortal = game.getEnemyPortals()[0].getLocation();
			for(int i = 0; i < game.getEnemyPortals().length ; i++) {
				if(game.getMyCastle().distance(game.getEnemyPortals()[i])<2500) {
					enemyPortal = game.getEnemyPortals()[i].getLocation();
				}
					
			}
			int x1 = game.getMyCastle().location.col;
			int y1 = game.getMyCastle().location.row;
			
			int x2 = enemyPortal.col;
			int y2 = enemyPortal.row;
			
			int d = enemyPortal.distance(game.getMyCastle());
			
			int a = d/4;
			
			int Xp = (x1*3+x2)/4;
			int Yp = (y1*3+y2)/4;
		
			Location myPortal = new Location(Yp,Xp);
			Elf elf = elfClosestTo(myPortal, game.getMyLivingElves());
			buildPortal(elf, myPortal, game);
		}
		
	}
}
