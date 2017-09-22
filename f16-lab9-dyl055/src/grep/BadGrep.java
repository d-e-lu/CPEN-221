package grep;

import java.util.ArrayList;
import java.util.List;

public class BadGrep {
    private static int numMatches = 0;
    
    public static void main(String[] args) throws InterruptedException {
        String substring = "do";
        
        List<String> lines = new ArrayList<String>();
        for (int i = 0; i < 10000; ++i) {
            lines.add("don't do this");
        }
        
        Thread[] searchers = new Thread[20]; // TODO use multiple consumers
        
        for (int ii = 0; ii < searchers.length; ii++) { // start Searchers
            Thread searcher = searchers[ii] = new Thread(new Searcher(lines, substring));
            searcher.start();
        }

        for (Thread searcher : searchers) { // wait for Searchers to stop
            searcher.join();
        }
        
        System.out.println(numMatches);
    }
    
    static class Searcher implements Runnable {
        private List<String> lines;
        private String substring;
        
        public Searcher(List<String> lines, String substring) {
            this.lines = lines;
            this.substring = substring;
        }

        public void run() {
            while (lines.size() > 0) {
                int i = (int) (Math.random() * lines.size());
                String line = lines.get(i);
                lines.remove(i);
                if (line.contains(substring)) {
                    System.out.println(line);
                    ++numMatches;
                }
            }
        }
        
        
    }

    
}
