import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GuitarString {
	
	private Queue<Double> ringBuffer = new LinkedList<Double>();
	private int N;
	private double decayFactor = 0.996;
	
	
	/*
	 * @param: frequency - the fundamental frequency of the string
	 * sets the size of the ring buffer to N full of zeros
	 * frequency must be positive
	 * N must be greater than 1
	 */
	public GuitarString(double frequency){
		if(frequency <= 0) throw new IllegalArgumentException();
		
		N = (int)Math.round(StdAudio.SAMPLE_RATE/frequency);

		if(N < 2) throw new IllegalArgumentException();
		
		for(int i = 0; i < N; i++){
			ringBuffer.add(0.0);
		}
	}
	
	/*
	 * @param: init - sets the ring buffer manually
	 * init must have more than one element
	 */
	public GuitarString(double[] init){
		if (init.length < 2) throw new IllegalArgumentException();
		N = init.length;
		for(int i = 0; i < init.length; i++){
			ringBuffer.add(init[i]);
		}

	}
	
	/*
	 * sets all of the ring buffer values to a random value
	 */
	public void pluck(){
		Random r = new Random();
		for(int i = 0; i < N; i++){
			ringBuffer.remove();
			ringBuffer.add( -0.5 + r.nextDouble());
		}
	}
	
	/*removes the first element of the ring buffer and adds
	* the average of the first two elements multiplied by a decay
	* as the last element
	*/
	public void tic(){
		double firstDisplacement = ringBuffer.peek();
		ringBuffer.remove();
		double secondDisplacement = ringBuffer.peek();
		ringBuffer.add(decayFactor * 0.5 * (firstDisplacement + secondDisplacement));
	}
	
	//@return: shows the first element of ring buffer
	public double sample(){
		return ringBuffer.peek();
	}
}
