package ca.ubc.ece.cpen221.mp4.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.commands.BreedCommand;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.EatCommand;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.Grass;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;
import ca.ubc.ece.cpen221.mp4.items.animals.Fox;
import ca.ubc.ece.cpen221.mp4.items.animals.Rabbit;

public class HiveMind {
	
	private static Map<Rabbit, Brain> rabbits = new HashMap<Rabbit, Brain>();
	private static Map<Fox, Brain> foxes = new HashMap<Fox, Brain>();
	
	
	private static List<Item> visibleItems;
	private static Location centralRabbitLocation;
	private static List<Location> safeLocations = new ArrayList<Location>();
	private static int direction = 0;
	private static int turns = 0;
	
	public static Direction[] getDirectionTowards(Location src, Location dest) {
		if (src == null || dest == null) {
			throw new NullPointerException("Location cannot be null.");
		}
		
		int dx = dest.getX() - src.getX();
		int dy = dest.getY() - src.getY();

		Direction[] directions = new Direction[2];
	
	
		if (dx > 0) {
			directions[0] = Direction.EAST;
		} else if ( dx < 0){
			directions[0] = Direction.WEST;
		}

		if (dy > 0) {
			directions[1] =  Direction.SOUTH;
		} else if (dy < 0){
			directions[1] = Direction.NORTH;
		}
		return directions;
	}
	
	public static List<Item> getVisibleItems(ArenaWorld world, ArenaAnimal animal){
		
		Set<Item> items = world.searchSurroundings(animal);
		List<Item> updateItems = new ArrayList<Item>();
		updateItems.addAll(items);
		
		for(Rabbit r : rabbits.keySet()){
			items = world.searchSurroundings(r);
			for(Item item : items){
				if(!updateItems.contains(item)){
					updateItems.add(item);
				}
			}
		}
		
		for(Fox f : foxes.keySet()){
			items = world.searchSurroundings(f);
			for(Item item : items){
				if(!updateItems.contains(item)){
					updateItems.add(item);
				}
			}
		}
		visibleItems = updateItems;
		return updateItems;
	}
	/*
	public static Rabbit getBestFoxFood(ArenaAnimal animal){
		Rabbit r;
		boolean foundAnotherRabbit = false;
		List<Rabbit> otherRabbits = new ArrayList<Rabbit>();
		for(Item i : visibleItems){
			if(i instanceof Rabbit){
				if(!rabbits.containsKey(i)){
					otherRabbits.add((Rabbit) i);
					foundAnotherRabbit = true;
				}
			}
		}
		
		if(foundAnotherRabbit){
			r = otherRabbits.get(0);
			int distance = getDistance(animal.getLocation(), r.getLocation());
			for(Rabbit rabbit : otherRabbits){
				rabbit.getLocation();
				int dis = getDistance(animal.getLocation(),rabbit.getLocation());
				if(dis < distance){
					r = rabbit;
					distance = dis;
				}
			}
		}
		else{
			r = null;
			int distance = getDistance(animal.getLocation(), r.getLocation());
		
			for(Rabbit rabbit : rabbits.keySet()){
				rabbit.getLocation();
				int dis = getDistance(animal.getLocation(),rabbit.getLocation());
				if(dis < distance){
					r = rabbit;
					distance = dis;
				}
			}
		}
		return r;
	}*/
	
	public static int getDistance(Location l1, Location l2){
		return l1.getDistance(l2);
	}
	
	public static Set<Location> getEmptyLocations(Set<Item> surronding, ArenaAnimal animal, ArenaWorld world){
		Set<Location> emptyLocations = new HashSet<Location>();
		for( Direction direction : Direction.values()){
			Location temp = new Location( animal.getLocation(), direction );
			if(isLocationEmpty(surronding, animal, temp) && Util.isValidLocation(world, temp)){
				emptyLocations.add(temp);
			}
		}
		return emptyLocations;
	}
	public static boolean isLocationEmpty(Set<Item> possibleMoves, ArenaAnimal animal, Location location) { 
	
		Iterator<Item> it = possibleMoves.iterator();
		while (it.hasNext()) {
			Item item = it.next();
			if (item.getLocation().equals(location)) {
				return false;
			}
		}
		return true;
	}
	
	
	public static Location getCentralRabbitLocation(){
		turns++;
		if(turns % 3000 == 0){
			direction = (int) Math.random()*8 - 4;
		}
		int count = 0;
		int x = 0;
		int y = 0;
		for(Rabbit r : rabbits.keySet()){
			count++;
			x += r.getLocation().getX();
			y += r.getLocation().getY();
		}
		if(turns%12000 > 9000){
			return new Location(x/count + direction, y/count);
		}else if( turns%12000 > 6000){
			return new Location(x/count, y/count - direction);
		}else if(turns%12000 > 3000){
			return new Location(x/count - direction, y/count );
		}
		return new Location(x/count, y/count + direction);
	}
	
	
	public static Command getFoxCommand(ArenaWorld world, ArenaAnimal animal){
		Fox f = (Fox) animal;
		Brain b = null;
		if(!foxes.containsKey(f)){
			b = new Brain();
			foxes.put(f, b);
		}else{
			b = foxes.get(f);
		}
		int dangerFoxDistance = Integer.MAX_VALUE;
		Item dangerFox = null;
		Set<Item> surroundingItems = world.searchSurroundings(animal);
		Set<Location> emptyLocations = getEmptyLocations(surroundingItems , animal, world);
		for( Item i : surroundingItems){
			if( i instanceof Rabbit){
				
				if( !rabbits.containsKey(i) 
						&& !safeLocations.contains(i.getLocation()) 
						&& i.getLocation().getDistance(f.getLocation()) == 1){
					return new EatCommand(f, i);
				}else if( animal.getEnergy() < 15 && i.getLocation().getDistance(f.getLocation()) ==1){
					return new EatCommand(f,i);
				}
			}else if(i instanceof Fox){
				if( !foxes.containsKey(i) ){
					int d = i.getLocation().getDistance(centralRabbitLocation);
					if( d < dangerFoxDistance ){
						dangerFoxDistance = d;
						dangerFox = i;
					}
				}
			}
		}
		if( f.getEnergy() < 15){
			b.starving = true;
		}
		
		if(b.target != null && b.target.getLocation().getDistance(animal.getLocation())>1){
			System.out.println("going to eat rabbit");
			moveTowardsLocation(animal, b.target, b, emptyLocations);
			if(b.previousCommand != null)
				return b.previousCommand;
		}
		else if(b.target != null && b.target.getLocation().getDistance(animal.getLocation())==1){
			System.out.println("just ate");
			b.previousCommand = new EatCommand(animal, b.target);
			b.target = null;
			return b.previousCommand;
		}
		else if(f.getEnergy() < 50){
			System.out.println("Fox is starving");
			b.starving = true;
			
			/*
			if( b.target instanceof Rabbit){
				if( b.target.getLocation().getDistance( f.getLocation() ) == 1){
					b.target = null;
					return new EatCommand(f, b.target);
				}else{
					moveTowardsLocation(f, b.target, b, emptyLocations);
					if(b.previousCommand != null)
						return b.previousCommand;
				}
			}else{
				for(Item i : surroundingItems){
					if(i instanceof Rabbit && rabbits.containsKey(i)){
						if(i.getLocation().getDistance(animal.getLocation())==1){
							b.target = null;
							b.previousCommand = new EatCommand(animal, i);
							System.out.println("Fox ate rabbit");
							return b.previousCommand;
						}
						else{
							rabbits.get(i).feeding = true;
							moveTowardsLocation(animal, i.getLocation(), b, emptyLocations);
							b.target = i;
							if(b.previousCommand != null)
								return b.previousCommand;
						}
					}
				}
				
			}
			*/
		}
		else if( f.getLocation().getDistance( centralRabbitLocation ) > 10){
			moveTowardsLocation(animal, centralRabbitLocation, b, emptyLocations);
			if(b.previousCommand != null)
				return b.previousCommand;
		}
		else if( dangerFox != null){
			Location dLoc = dangerFox.getLocation();
			Location central = centralRabbitLocation;
			int dx = dLoc.getX() - central.getX();
			int dy = dLoc.getY() - central.getY();
			moveTowardsLocation(animal,new Location(central.getX() + dx, central.getY() + dy) , b, emptyLocations);
		}
		else if(foxes.size() < 0.2* rabbits.size()){
			if( !emptyLocations.isEmpty()){
				for( Location l : emptyLocations){
					return new BreedCommand(animal, l);
				}
			}
		}
		
		
		return new WaitCommand();
	}
	
	
	
	private static Command moveTowardsLocation( ArenaAnimal animal, Item food, Brain b, Set<Location> emptyLocations){
		
		Command ret =  moveTowardsLocation(animal, food.getLocation(), b, emptyLocations);
		
		if(ret != null){
			b.target = food;
			
		}
		return ret;
	}
	
	
	
	private static Command moveTowardsLocation( ArenaAnimal animal, Location l, Brain b, Set<Location> emptyLocations){
		Command ret = null;
		b.previousCommand = null;
		for(Direction d : getDirectionTowards(animal.getLocation(), l)){
			if( d == null) continue;
			Location t = new Location( animal.getLocation(), d);
			if(emptyLocations.contains(t)){
				ret = new MoveCommand(animal,t);
				b.previousCommand = ret;
				b.previousLocation = animal.getLocation();
				
				break;
			}
		}
		return ret;
	}
	
	
	
	
	
	public static Command getRabbitCommand(ArenaWorld world,ArenaAnimal animal){
		
		Rabbit r = (Rabbit) animal;
		Brain b = null;
		
		if(!rabbits.containsKey(r)){
			if( safeLocations.contains(animal.getLocation())){
				safeLocations.remove(animal.getLocation());
			}
			b = new Brain();
			rabbits.put(r, b);
		}else{
			b = rabbits.get(r);
		}

		
		centralRabbitLocation = getCentralRabbitLocation();
		int nearestFoodDistance = Integer.MAX_VALUE;
		Item nearestFood = null;
		
		boolean danger = false;
		boolean hasTarget = false;
		if(b.target != null && !b.feeding){
			nearestFood = b.target;
			nearestFoodDistance = b.target.getLocation().getDistance(animal.getLocation());
			hasTarget = true;
		}
		Set<Item> surroundingItems = world.searchSurroundings(animal);
		Set<Location> emptyLocations = getEmptyLocations(surroundingItems , animal, world);
		
		for(Item item : surroundingItems){
			if(item instanceof Grass && !hasTarget){
				int distance = getDistance(animal.getLocation(), item.getLocation());
				if (distance < nearestFoodDistance){
					nearestFood = item;
					nearestFoodDistance = distance;
				}
			}else if( item instanceof Fox){
				if( !foxes.containsKey((Fox)item)){
					int distance = getDistance(animal.getLocation(), item.getLocation());
					if( distance == 1){
						danger = true;	
						
					}else if( distance == 2){
						Direction[] bad = getDirectionTowards(animal.getLocation(), item.getLocation());
						for( Direction d : bad){
							if(d != null){
								emptyLocations.remove( new Location( animal.getLocation(), d) );
							}
						}
					}
					
				}
			}
		}
		if(danger){
			if(emptyLocations.isEmpty()){
				return new WaitCommand();
			}
			else{
				for( Direction d : Direction.values()){
					Location t = new Location( r.getLocation(), d);
					if(emptyLocations.contains(t)){
						b.previousCommand = new MoveCommand(animal, t);	
						return b.previousCommand;
					}
				}
				b.previousLocation = new Location(animal.getLocation());
				b.running = true;
				return 	b.previousCommand ;
			}
		}
		else if( b.target instanceof Fox && b.feeding){
			System.out.println("going to feed the fox");
			moveTowardsLocation(animal, b.target, b, emptyLocations);
			if(b.previousCommand != null){
				return b.previousCommand;
			}else if(nearestFoodDistance == 1){
				return new EatCommand(animal, nearestFood);
			}
		}else if(animal.getEnergy() < 40){
			if(b.target != null){
				if(b.target.getLocation().getDistance(animal.getLocation()) ==1){
					b.previousCommand = new EatCommand(animal, b.target);
					return b.previousCommand;
				}
				else{
					b.previousCommand = moveTowardsLocation(animal, b.target, b, emptyLocations);
				}
			}
			if( nearestFoodDistance == 1){
				b.previousCommand = new EatCommand(animal, nearestFood);
				return b.previousCommand;
			}
			else{
				if(hasTarget){
					moveTowardsLocation(animal, b.target, b, emptyLocations);
				}
				else if(nearestFoodDistance != Integer.MAX_VALUE){
					moveTowardsLocation(animal, nearestFood, b, emptyLocations);
				}
				else{
					getVisibleItems(world, animal);
					for(Item item : visibleItems){
						if(item instanceof Grass){
							int distance = getDistance(animal.getLocation(), item.getLocation());
							if (distance < nearestFoodDistance){
								nearestFood = item;
								nearestFoodDistance = distance;
							}
						}
					}
					b.target = nearestFood;
					moveTowardsLocation(animal, nearestFood, b, emptyLocations);
				}
			}
			b.running = false;
			b.movingTowardsCentral = false;
			if(b.previousCommand != null){
				return b.previousCommand;
			}
			
			// do something here?
		}
		else if(centralRabbitLocation.getDistance(animal.getLocation()) > 15){ //change this value
			b.target = null;
			b.running = false;
			b.movingTowardsCentral = true;
			moveTowardsLocation(animal, centralRabbitLocation, b, emptyLocations);
			
			if(b.previousCommand != null){
				return b.previousCommand;
			}
			// do something here;
		}
		/*
		else{
			for(Direction direction: getDirectionTowards(animal.getLocation(), centralRabbitLocation )){
				 if(direction != null){
					 Location temp = new Location(animal.getLocation(), direction);
				 	if( emptyLocations.contains(temp) ){
				 		b.previousCommand =  new MoveCommand(animal, temp);
				 		return b.previousCommand;
				 	}
				 	else{
				 		b.previousCommand = null;
				 	}
				 }
			}
		}
		*/
		
		for( Entry<Fox, Brain> v : foxes.entrySet()){
			if( v.getValue().starving ){
				v.getValue().starving = false;
				v.getValue().target = animal;
				b.feeding = true;
				b.target = v.getKey();
				for(Location l : emptyLocations ){
					safeLocations.add(l);
					return new BreedCommand( animal, l);
				}
			}
		}
		
		
		
		if( animal.getEnergy() > (animal.getMinimumBreedingEnergy() + 30) && !emptyLocations.isEmpty()){
			for(Location l : emptyLocations ){
				safeLocations.add(l);
				return new BreedCommand( animal, l);
			}
		}
		
		/*else if( nearestFoodDistance == 1 ){
			
			return new EatCommand(animal, nearestFood);
		}else{
			if(nearestFoodDistance != Integer.MAX_VALUE){
				for( Direction direction : getDirectionTowards(animal.getLocation(), nearestFood.getLocation()) ){
					if(direction != null){
						Location temp = new Location(animal.getLocation(), direction);
						if( emptyLocations.contains(temp) ){
							return new MoveCommand(animal, temp);
						}	
					}
				}
			}
		}
		
		for(Direction direction: getDirectionTowards(animal.getLocation(), centralRabbitLocation )){
			if(direction != null){
				Location temp = new Location(animal.getLocation(), direction);
				if( emptyLocations.contains(temp) ){
					return new MoveCommand(animal, temp);
				}	
			}
		}
	
		Direction d = Util.getRandomDirection();
		*/
		/*
		
		if(nearestFoodDistance == Integer.MAX_VALUE){
			visibleItems = getVisibleItems(world, animal);
			for( Item i : visibleItems){
				if( i instanceof Grass){
					int distance = i.getLocation().getDistance( animal.getLocation() );
					if( distance < nearestFoodDistance){
						nearestFoodDistance = distance;
						nearestFood = i;
					}
				}
			}
			if( nearestFoodDistance != Integer.MAX_VALUE ){
				moveTowardsLocation(animal, nearestFood, b, emptyLocations);
				if( b.previousCommand != null) return b.previousCommand;
			}
		}else if( nearestFoodDistance == 1){
			
			return new EatCommand(animal, nearestFood);
		}else{
			moveTowardsLocation(animal, nearestFood, b, emptyLocations);
			if( b.previousCommand != null) return b.previousCommand;
		}
		*/
		int size = emptyLocations.size();
		if(size > 0){
			int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
			int x = 0;
			for(Location loc : emptyLocations)
			{
				if (x == item)
					return new MoveCommand(animal, loc);
				x = x + 1;
			}
		}
		
		//System.out.println("waiting");
		return new WaitCommand();
	}

}
