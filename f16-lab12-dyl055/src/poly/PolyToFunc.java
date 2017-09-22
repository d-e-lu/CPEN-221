package poly;
@FunctionalInterface
public interface PolyToFunc {
	public Function<Double> polyToFunc(Poly p);
}
