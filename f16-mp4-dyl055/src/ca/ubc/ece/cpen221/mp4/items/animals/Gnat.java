package ca.ubc.ece.cpen221.mp4.items.animals;

import javax.swing.ImageIcon;

import ca.ubc.ece.cpen221.mp4.Direction;
import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.Location;
import ca.ubc.ece.cpen221.mp4.Util;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.commands.MoveCommand;
import ca.ubc.ece.cpen221.mp4.commands.WaitCommand;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

/**
 * This is a simple implementation of a Gnat. It never loses energy and moves in
 * random directions.
 */
public class Gnat implements LivingItem {
	private static final ImageIcon gnatImage = Util.loadImage("gnat.gif");

	private static final int MEAT_CALORIES = 100;
	private static final int STRENGTH = 10;

	private Location location;
	private boolean isDead;

	/**
	 * Create a new Gnat at <code>initialLocation</code>. The
	 * <code>initialLocation</code> must be valid and empty.
	 *
	 * @param initialLocation
	 *            the location where the Gnat will be created
	 */
	public Gnat(Location initialLocation) {
		this.location = initialLocation;
		this.isDead = false;
	}

	@Override
	public ImageIcon getImage() {
		return gnatImage;
	}

	@Override
	public String getName() {
		return "Gnat";
	}

	@Override
	public Location getLocation() {
		return location;
	}

	@Override
	public int getPlantCalories() {
		return 0;
	}

	@Override
	public int getMeatCalories() {
		return MEAT_CALORIES;
	}

	@Override
	public void loseEnergy(int energy) {
		isDead = true; // Dies if it loses energy.
	}

	@Override
	public boolean isDead() {
		return isDead;
	}

	@Override
	public void moveTo(Location targetLocation) {
		location = targetLocation;
	}

	@Override
	public int getCoolDownPeriod() {
		// Each Gnat acts every 1-10 steps randomly.
		return Util.RAND.nextInt(10) + 1;
	}

	@Override
	public Command getNextAction(World world) {
		// The Gnat selects a random direction and check if the next location at
		// the direction is valid and empty. If yes, then it moves to the
		// location, otherwise it waits.
		Direction dir = Util.getRandomDirection();
		Location targetLocation = new Location(this.getLocation(), dir);
		if (Util.isValidLocation(world, targetLocation) && Util.isLocationEmpty(world, targetLocation)) {
			return new MoveCommand(this, targetLocation);
		}

		return new WaitCommand();
	}

	@Override
	public int getStrength() {
		return STRENGTH;
	}

	@Override
	public int getEnergy() {
		// doesn't every die, except when run over by a Vehicle
		return 100;
	}

	@Override
	public LivingItem breed() {
		return null; // Never eats.
	}

	@Override
	public void eat(Food food) {
		// Never eats.
	}

	@Override
	public int getMovingRange() {
		return 1; // Can only move to adjacent locations.
	}
}
