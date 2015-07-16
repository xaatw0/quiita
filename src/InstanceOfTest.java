

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;
public class InstanceOfTest {

	@Test
	public void instanceOf(){

		ClassBase base = new ClassBase();
		ClassBase sub = new ClassSub();
		ClassBase inter = new ClassInterface();

		assertThat(base instanceof Interface, is(false));
		assertThat(sub instanceof Interface, is(false));
		assertThat(inter instanceof Interface, is(true));

		Class classTest = sub.getClass();
		assertThat(classTest.isInstance(base), is(false));
		assertThat(classTest.isInstance(sub), is(true));
		assertThat(classTest.isInstance(inter), is(false));

		assertThat(sub.getClass() == ClassSub.class, is(true));
		assertThat(sub.getClass() == ClassBase.class, is(false));
		assertThat(sub instanceof ClassBase, is(true));
		assertThat(sub instanceof ClassSub, is(true));
	}


	private interface Interface{
	}

	private class ClassBase{
	}

	private class ClassSub extends ClassBase{
	}

	private class ClassInterface extends ClassBase implements Interface{
	}
}
