package guice.generics;

import com.google.inject.Inject;

public class Console<T> {

	private final StringOutput<T> out;

	@Inject
	public Console(StringOutput<T> out) {
		this.out = out;
	}

	public void print(T t) {
		System.out.println(out.converter(t));
	}
}