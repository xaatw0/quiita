package guice;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestMain {

	public static void main(final String[] args) {

		// DIの注入を行うインスタンス
		Injector injector = Guice.createInjector(new AbstractModule() {

			@Override
			protected void configure() {

				if (0 < args.length && args[0].equals("TEST")){

					// LogicInterfaceには、LogicDummyを注入することを定義する
					bind(LogicInterface.class).to(LogicDummy.class);
				}
			}
		});

		Controller controller = new Controller();

		// コントロールの@Injectアノテーションのある変数に注入を行う
		injector.injectMembers(controller);

		System.out.println("3 + 5 = " + controller.add(3, 5));
		System.out.println("5 - 3 = " + controller.substract(5, 3));
	}
}