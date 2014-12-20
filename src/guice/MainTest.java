package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class MainTest {

	public static void main(String[] args) {

		Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {
				bind(LogicInterface.class).to(LogicDummy.class);
			}
		});

		Controller controller = new Controller();
		injector.injectMembers(controller);

		System.out.println("3 + 5 = " + controller.add(3, 5));
		System.out.println("5 - 3 = " + controller.substract(5, 3));

	}
}
