package tournament;

public class Bye implements Tournament {
    
    // The player 
    private final Player p;
    
    /*
     * Byes only have a single player and serve as a base-case.
     * 
     * @param Player p the player to be entered in this Bye.
     */
    public Bye(Player p) {
        this.p = p;
    }

    /*
     * @return Player the player in this Bye.
     */
    @Override
    public Player winner() {
        return p;
    }

}
