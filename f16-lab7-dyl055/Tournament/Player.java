package tournament;

/**
 * A player in a tournament.
 */
public class Player {
    
    // TODO representation
    private final String name;
    private final int skill;
    
    /**
     * TODO
     */
    public Player(String name, int skill) {
        this.name = name;
        this.skill = skill;
    }
    
    /**
     * TODO
     */
    public int skill() {
        return skill;
    }
    
    /**
     * TODO
     */
    @Override
    public String toString() {
        return name + " with skill ranking: " + String.valueOf(skill);
    }
}
