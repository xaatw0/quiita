package guice.generics;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.util.Types;

/**
 * (元ネタ)
 *  Injecting generics with Guice
 *  http://stackoverflow.com/questions/2581137/injecting-generics-with-guice?lq=1
 *
 *  Guice generics - how can I make it less ugly?
 *  http://stackoverflow.com/questions/3777428/guice-generics-how-can-i-make-it-less-ugly?rq=1
 */
public class MyModule extends AbstractModule {

	static <T> Key<IStringOutput<T>> producerOf(Class<T> type) {
		  return (Key<IStringOutput<T>>)Key.get(Types.newParameterizedType(IStringOutput.class,type));
	}

	@Override
	protected void configure() {
		bind(producerOf(Integer.class)).to(StringOutputInteger.class);
		bind(producerOf(Double.class)).to(StringOutputDouble.class);
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new MyModule());

		IStringOutput<Integer> out =
				injector.getInstance(Key.get(new TypeLiteral<StringOutput<Integer>>() {}));
		System.out.println(out.converter(12));


		Console<Integer> conInteger =
				injector.getInstance(Key.get(new TypeLiteral<Console<Integer>>() {}));
		conInteger.print(123);

		Console<Double> conDouble =
				injector.getInstance(Key.get(new TypeLiteral<Console<Double>>() {}));
		conDouble.print(123.5);
	}
}
