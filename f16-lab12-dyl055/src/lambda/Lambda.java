package lambda;


public class Lambda<T,R,X,U,V,W> {
	
	public static<T,R> void main(String[] args){
		//CALLING T -> R
		
		Foo <T,R>z = new Foo<T, R>(){
			@Override
			public R foo(T t) {
				//or something like that
				return (R) t;
			}
		};
		
		
		//CALLING X ADDER
		Xadder x = harder(3);
		//Returns 7
		System.out.println(x.adder(4));
		
		//CALLING COMPOSITION
		Foo f = new Foo(){
			@Override
			public Object foo(Object t) {		
				return (int)t*2;
			}
		};
		Foo g = new Foo(){
			@Override
			public Object foo(Object t){
				return (int) t + 5;
			}
		};
		Foo myComposition = evenHarder(f, g);
		//returns 4 * 2 + 5 = 13
		//f(4) = 4*2 = 8
		//g(8) = 8+5 = 13
		//g(f(4)) = 13
		System.out.println(myComposition.foo(4));
		
	}
	
	//FIRST TASK
	public R easy(Foo<T,R> f, T x){	
		return f.foo(x);
	}
	//SECOND TASK
	public static Xadder harder(int added){
		return new Xadder(){
			@Override
			public int adder(int X) {
				return X + added;
			}
		};
	}
	//THIRD TASK
	public static<U,V,W> Foo evenHarder(Foo<U,V> f, Foo<V,W> g){
		return new Foo(){
			@Override
			public Object foo(Object t) {
				return g.foo((V) f.foo((U) t));
			}
			
		};
	}
	
	
}
