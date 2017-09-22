package lambda;
@FunctionalInterface
interface Foo<T,R>{
	public R foo(T t);
}