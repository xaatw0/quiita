package guice.generics;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

/**
 * (元ネタ)
 *  Injecting generics with Guice
 *  http://stackoverflow.com/questions/2581137/injecting-generics-with-guice?lq=1
 */
public class MyModule extends AbstractModule {

	@Override
	protected void configure() {
		// bind(StringOutput.class);
		bind(new TypeLiteral<StringOutput<Integer>>() {});

		// bind(Console.class);
		bind(new TypeLiteral<Console<Double>>() {});
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MyModule());

		//StringOutput<Integer> out = injector.getInstance(StringOutput.class);
		StringOutput<Integer> out =
				injector.getInstance(Key.get(new TypeLiteral<StringOutput<Integer>>() {}));
		System.out.println(out.converter(12));

		// Console<Double> cons = injector.getInstance(Console.class);
		Console<Double> cons =
				injector.getInstance(Key.get(new TypeLiteral<Console<Double>>() {}));
		cons.print(123.0);
	}

}
