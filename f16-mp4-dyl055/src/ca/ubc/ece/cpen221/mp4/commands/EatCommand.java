package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.Food;
import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

/**
 * An EatCommand is a {@link Command} which represents a {@link LivingItem}
 * eating a {@link Food}.
 */
public final class EatCommand implements Command {

	private final LivingItem item;
	private final Item food;

	/**
	 * Construct a {@link EatCommand}, where <code> item </code> is the eater
	 * and <code> food </code> is the food. Remember that the food must be
	 * adjacent to the eater, and the eater must have greater strength than the
	 * food.
	 *
	 * @param item
	 *            the eater
	 * @param food
	 *            : the food
	 */
	public EatCommand(LivingItem item, Item food) {
		this.item = item;
		this.food = food;
	}

	@Override
	public void execute(World w) throws InvalidCommandException {
		if (item.getStrength() <= food.getStrength())
			throw new InvalidCommandException("Invalid EatCommand: Food possesses too much strength");
		if (food.getLocation().getDistance(item.getLocation()) != 1)
			throw new InvalidCommandException("Invalid EatCommand: Food is not adjacent");

		item.eat(food);
		food.loseEnergy(Integer.MAX_VALUE);
	}

}
