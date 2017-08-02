import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;


public class IntefaceClassMethod {

	interface Interface{
		Interface test();
	}

	class Clazz implements Interface{
		@Override
		public Clazz test() { return new Clazz();}
	}


	int counterInterface;
	int counterClazz;

	void test(Interface a){ counterInterface ++; }
	void test(Clazz a){ counterClazz ++;}



	@Test
	public void test(){

		IntefaceClassMethod target = new IntefaceClassMethod();

		Interface interface1 = ()->{ return new Clazz(); };
		Clazz clazz1 = new Clazz();
		Interface clazz0 = clazz1;

		target.test(interface1);
		target.test(clazz0);
		target.test(clazz1);

		assertThat(target.counterInterface, is(2));
		assertThat(target.counterClazz, is(1));


		target.test(interface1.test());
		target.test(clazz1.test());

		assertThat(target.counterInterface, is(3));
		assertThat(target.counterClazz, is(2));
	}

}
