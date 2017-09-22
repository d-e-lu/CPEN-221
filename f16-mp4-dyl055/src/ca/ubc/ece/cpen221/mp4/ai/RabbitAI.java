package ca.ubc.ece.cpen221.mp4.ai;

import ca.ubc.ece.cpen221.mp4.ArenaWorld;
import ca.ubc.ece.cpen221.mp4.commands.Command;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

/**
 * Your Rabbit AI.
 */
public class RabbitAI extends AbstractAI {
	@Override
	public Command getNextAction(ArenaWorld world, ArenaAnimal animal) {
		Command c = HiveMind.getRabbitCommand(world, animal);
		return c;
		
	}
}
