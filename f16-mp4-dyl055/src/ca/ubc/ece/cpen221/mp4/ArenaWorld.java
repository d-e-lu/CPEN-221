package ca.ubc.ece.cpen221.mp4;

import java.util.Set;

import ca.ubc.ece.cpen221.mp4.items.Item;
import ca.ubc.ece.cpen221.mp4.items.animals.ArenaAnimal;

/**
 * Subset of the world visible to {@link ArenaAnimal}s.
 */
public interface ArenaWorld {

	/**
	 * Returns items within the view range of <code>animal</code>, which is
	 * defined by {@link ArenaAnimal#getViewRange()}.
	 *
	 * @see World#searchSurroundings(Location, int)
	 *
	 * @param animal
	 *            the ArenaAnimal whose location is used
	 * @return a set of items visible to <code>animal</code>
	 */
	Set<Item> searchSurroundings(ArenaAnimal animal);

	/**
	 * @return an int representing the number of columns this World has
	 */
	int getWidth();

	/**
	 * @return an int representing the number of rows this World has
	 */
	int getHeight();

}
