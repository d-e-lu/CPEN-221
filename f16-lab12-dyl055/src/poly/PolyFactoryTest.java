package poly;

public class PolyFactoryTest {
	
	public static void main (String args[]) {
		Poly p = PolyFactory.parse (" X^10 - X ^ 2 + 10 * X ^ 2 + 100 * X + 1");
		System.out.println("RETURNED POLY: " + p);
		System.out.println("EVAL P(2):" + p.eval(2));
	}

}
