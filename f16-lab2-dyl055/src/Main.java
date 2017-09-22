import java.io.File;
import java.io.IOException;

public class Main {
    
    /**
     * Generate some poems.
     * Requires vocabulary in <code>./vocabulary.txt</code>.
     * Requires WordNet database in <code>./dict</code>.
     */
    public static void main(String[] args) throws IOException {
        UBCPoet poet = new UBCPoet(new File("vocabulary.txt"), new File("dict"));
        for (String input : new String[] { "meter" }) {
            System.out.println("== A poem about: " + input + " ==");
            System.out.println(poet.verse(input));
            System.out.println();
        }
    }
}