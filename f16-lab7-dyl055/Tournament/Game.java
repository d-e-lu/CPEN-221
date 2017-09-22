package tournament;

public class Game implements Tournament {
    
    /*
     *  Matches in this tournament. These matches are of
     *  type Tournament, so they can be either of the variants
     *  that we have designed: Game or Bye.
     */
    private final Tournament match1;
    private final Tournament match2;
    
    // Constructor taking two Tournaments.
    public Game(Tournament match1, Tournament match2) {
        this.match1 = match1;
        this.match2 = match2;
    }

    /*
     * We grab the winner of both Tournaments - in the case
     * of a Bye, this is just the player in the Bye. We then
     * compare the two winners and return the one with greater
     * skill, deciding ties arbitrarily.
     */
    @Override
    public Player winner() {
        Player winner1 = match1.winner();
        Player winner2 = match2.winner();
        if (winner1.skill() >= winner2.skill()) {
            return winner1;
        } else {
            return winner2;
        }
    }

}
