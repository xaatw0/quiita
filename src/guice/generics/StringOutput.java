package guice.generics;

public class StringOutput<T> {
	public String converter(T t) {
		return t.toString();
	}
}
