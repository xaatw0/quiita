package guice.generics;

import com.google.inject.Inject;

public class Console<T> {

	@Inject
	private final IStringOutput<T> out = null;

	public void print(T t) {
		System.out.println(out.converter(t));
	}
}