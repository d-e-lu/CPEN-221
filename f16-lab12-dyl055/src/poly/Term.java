package poly;

import java.lang.Math;

// immutable polynomial term
final public class Term {
	private final float coeff;
	private final int pow;

	public Term (float coeff, int pow) {
		this.coeff = coeff;
		this.pow = pow;
	}
	
	public int getPow () {
		return pow;
	}

	public float getCoeff () {
		return coeff;
	}

	public double eval (double x) {
		return coeff * Math.pow(x, pow);
	}
	
	@Override
	public String toString() {
		return "T[" + coeff + ", " + pow + "]";
	}
}
