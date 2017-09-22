package tournament;

/**
 * A single-elimination tournament.
 */
public interface Tournament {
    
    /**
     * Returns the winner of the tournament, where the winner is a player with
     * skill greater than or equal to the skill of every other player.
     * @return winning player
     */
    public Player winner();
}

/*
 * In their own ".java" files, define classes that implement this interface.
 * Those concrete implementation classes are obligated to implement winner().
 */
