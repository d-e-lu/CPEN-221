package tournament;

public class Main {

    public static void main(String[] args) {
        /*
         * Constructing "Round 0" - a set of Byes that represent
         * the player's entry into the tournaments.
         */
        Tournament b1 = new Bye(new Player("Alice", 98));
        Tournament b2 = new Bye(new Player("Bob", 76));
        Tournament b3 = new Bye(new Player("Eve", 87));
        Tournament b4 = new Bye(new Player("Yolanda", 43));
        Tournament b5 = new Bye(new Player("Zach", 54));

        /*
         * Constructing the tournament bracket - this is the same
         * bracket that appears in the R07 handout.
         */
        Tournament m1 = new Game(b1, b2);
        Tournament m2 = new Game(b3, m1);
        Tournament m3 = new Game(b4, b5);
        Tournament m4 = new Game(m2, m3);

        // Using the R07 bracket, Alice should be the winner.
        System.out.println(m4.winner());
    }
}
