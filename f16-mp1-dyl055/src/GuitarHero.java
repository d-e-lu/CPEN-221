
import java.io.*;
import java.util.*;
import javax.swing.*;


/**
 * 
 * This program constructs a Guitar object that it allows the user to play.
 * @author Sathish Gopalakrishnan
 *
 */

public class GuitarHero {/*
    public static void main(String[] args) {
        Guitar g = new Guitar221();
        // this is an infinite loop--user must quit the application
        for (;;) {
            // check if the user has typed a key; if so, process it   
            if (StdDraw.hasNextKeyTyped()) {
                char key = Character.toLowerCase(StdDraw.nextKeyTyped());
                if (g.hasString(key)) {
                    g.pluck(key);
                } else {
                    System.out.println("bad key: " + key);
                    System.out.println((int) key);
                }
            }
            // send the result to the sound card
            StdAudio.play(g.sample());
            g.tic();
        }
    }
    */
	
	 public static void main(String[] args) throws FileNotFoundException {
	        Guitar g = new Guitar221();
	        
	        JFileChooser chooser = new JFileChooser(new File("."));
	        int result = chooser.showOpenDialog(null);
	        if (result == JFileChooser.APPROVE_OPTION) {
	            Scanner input = new Scanner(chooser.getSelectedFile());
	            while (input.hasNextInt()) {
	                int pitch = input.nextInt();
	                double duration = input.nextDouble();
	                g.playNote(pitch);
	                advance(duration, g);	                	           
	            }
	        }
	 }
	
	 public static void advance(double duration, Guitar g) {
		 int tics = (int) Math.round(duration * StdAudio.SAMPLE_RATE);
		 for (int i = 0; i < tics; i++) {
			 StdAudio.play(g.sample());
			 g.tic();
		 }
	}
	    
}
