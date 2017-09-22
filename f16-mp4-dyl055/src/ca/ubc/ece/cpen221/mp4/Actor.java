package ca.ubc.ece.cpen221.mp4;

import ca.ubc.ece.cpen221.mp4.commands.Command;

/**
 * An Actor is an object in a {@link World} that can actively affect the state
 * of that World. It has a cooldown period which defines how often it performs
 * an action.
 */
public interface Actor {

	/**
	 * Returns the cooldown period between two consecutive actions. This
	 * represents the number of steps passed between two actions.
	 *
	 * @return the number of steps between actions
	 */
	int getCoolDownPeriod();

	/**
	 * Returns the next action to be taken, given the current state of the
	 * world.
	 *
	 * @param world
	 *            the current world
	 * @return the next action of this Actor as a {@link Command}
	 */
	Command getNextAction(World world);

}