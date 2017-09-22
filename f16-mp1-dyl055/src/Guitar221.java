// skeleton version of the class

public class Guitar221 implements Guitar {
	private GuitarString[] strings = new GuitarString[KEYBOARD.length()];
	private int timesTicked = 0;
	
    public static final String KEYBOARD =
        "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
   
    
    /*
     * measures frequency of each string, creates a string and adds the string to an array called strings
     */
    public Guitar221(){
    	for(int i = 0; i < KEYBOARD.length(); i++){
    		double frequency =(double) 440 * Math.pow(2.0, (double)(i-24.0)/12.0);
    		strings[i] = new GuitarString(frequency);
    	}
    }
    
    
    /*
     * can change pitch here, so that you can play the notes in different scales
     * every 12 notes is an octave
     * @param: pitch - tells which note to pluck
     */
    
    public void playNote(int pitch){
    	int pitchPlus = 12;
    	if(pitch + pitchPlus > 0 && pitch + pitchPlus < strings.length){
    		strings[pitch + pitchPlus].pluck();
    	}
    	
    }
    /*
     * checks if KEYBOARD contains key
     * @param: key - key to check
     * @return value: true if key is in KEYBOARD
     * 				  false if key is not in KEYBOARD
     */
	public boolean hasString(char key) {
		for(char i : KEYBOARD.toCharArray()){
			if(key == i){
				return true;
			}
		}
		return false;
	}
	/*
	 * plucks the guitar string that corresponds to the key
	 * @param: key - the key to be plucked
	 * 
	*/
    public void pluck(char key){
    	if(!hasString(key)) throw new IllegalArgumentException();
    	
    	int index = KEYBOARD.indexOf(key);
    	strings[index].pluck();
    }
    /*
     * gets the total sample of all of the guitar strings
     * return: returns the sum of all guitar string samples
     */
    public double sample(){
    	double totalSample = 0;
    	for(GuitarString string : strings){
    		totalSample += string.sample();
    	}
    	return totalSample;
    }
    /*
     * tics each of the guitar strings on the guitar
     */
    public void tic(){
    	for(GuitarString string : strings){
    		string.tic();
    	}
    	timesTicked++;
    	
    }
    /*
     * returns the number of times the guitar was ticked
     */
    public int time(){
    	
    	return timesTicked;
    }
}
