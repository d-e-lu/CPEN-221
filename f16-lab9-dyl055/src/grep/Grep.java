package grep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/** Search web pages for lines matching a pattern. */
public class Grep {
    public static void main(String[] args) throws Exception {
        
        // substring to search for
        String substring = "Kaggle";
        
        // URLs to search
        String[] urls = new String[] {
        		"https://www.kaggle.com/datasets"
        		//"http://eece210.ece.ubc.ca/",
                //"http://github.com/EECE-210/lab2",
                //"http://github.com/EECE-210/mp1",
        };
        
        // list for accumulating matching lines
        List<Text> matches = Collections.synchronizedList(new ArrayList<Text>());
        
        // queue for sending lines from producers to consumers
        BlockingQueue<Line> queue = new LinkedBlockingQueue<Line>();
        
        Thread[] producers = new Thread[urls.length]; // one producer per URL
        Thread[] consumers = new Thread[8]; // TODO use multiple consumers
        
        for (int ii = 0; ii < consumers.length; ii++) { // start Consumers
            Thread consumer = consumers[ii] = new Thread(new Consumer(queue, matches, substring));
            consumer.start();
        }
        
        for (int ii = 0; ii < urls.length; ii++) { // start Producers
            Thread producer = producers[ii] = new Thread(new Producer(urls[ii], queue, consumers.length));
            producer.start();
        }
        
        for (Thread producer : producers) { // wait for Producers to stop
            producer.join();
        }
        
        // stop Consumers
        // ...
        // ...
        
        for (Thread consumer : consumers) { // wait for Consumers to stop
            consumer.join();
        }
        
        for (Text match : matches) {
            System.out.println(match);
        }
        System.out.println(matches.size() + " lines matched");
    }
}

class Producer implements Runnable {
    private String url;
    private BlockingQueue<Line> queue;
    private int consumerCount;
    Producer(String url, BlockingQueue<Line> queue, int consumerCount) {
    		this.url = url;
    		this.queue = queue;
    		this.consumerCount = consumerCount;
		}

    public void run() {
    	BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
		} catch (IOException e1) {

			e1.printStackTrace();
		}
    	String line;
    	try {
    		int lineNumber = 1;
			while ((line = in.readLine()) != null) {
					queue.put(new Text(url, lineNumber, line));
					++lineNumber;
				}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	Text poisonPill = new Text("", -1, "");
    	try {
    		for(int i = 0; i < consumerCount; ++i){
    			queue.put(poisonPill);
    		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}

class Consumer implements Runnable {
    BlockingQueue<Line> queue;
    List<Text> matches;
    String substring;
    Consumer(BlockingQueue<Line> queue, List<Text> matches, String substring) {
        this.queue = queue;
        this.matches = matches;
        this.substring = substring;
    }

    public void run() {
        while (true){
        	try {

				Line line = queue.take();
				if (line.lineNumber() == -1){
					break;
				}
				else{
					if(line.text().contains(substring)){
						matches.add((Text) line);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	
        }
    }

    
}

interface Line {
    /** @return the filename. */
    public String filename();
    /** @return the line number. */
    public int lineNumber();
    /** @return the text on the line. */
    public String text();
}

class Text implements Line {
    private final String filename;
    private final int lineNumber;
    private final String text;
    
    public Text(String filename, int lineNumber, String text) {
        this.filename = filename;
        this.lineNumber = lineNumber;
        this.text = text;
    }
    
    public String filename() {
        return filename;
    }
    
    public int lineNumber() {
        return lineNumber;
    }
    
    public String text() {
        return text;
    }
    
    @Override public String toString() {
        return filename + ":" + lineNumber + ":" + text;
    }
}
