package poly;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuffer;

public class Poly {

	private final List<Term> p;
	
	public Poly(List<Term> p) {
		// defensive cpy
		this.p = new ArrayList<Term>(p);
	}
	

	public double eval (double x) {
		double rez = 0;
		for (Term t : p) {
			rez = rez + t.eval(x);
		}
		return rez;
	}
	
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (Term t: p) {
			if (s.length() > 0) {
				s.append(" + ");
			}
			s.append(t.getCoeff() + " * X ^ " + t.getPow());
		}
		return s.toString();
	}
}
