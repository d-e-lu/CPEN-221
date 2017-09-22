package ca.ubc.ece.cpen221.mp4.commands;

import ca.ubc.ece.cpen221.mp4.World;
import ca.ubc.ece.cpen221.mp4.items.LivingItem;

/**
 * A WaitCommand is a {@link Command} which represents doing nothing
 */
public final class WaitCommand implements Command {

	@Override
	public void execute(World w) {
		// Do nothing.
	}

}
