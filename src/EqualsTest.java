import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

public class EqualsTest {

	private String name;
	private int age;

	public EqualsTest(){}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EqualsTest other = (EqualsTest) obj;
		if (age != other.age)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean myEquals(Object obj){

		if (this == obj) return true;
		if (obj == null || hashCode() != obj.hashCode() || ! (obj instanceof EqualsTest)) return false;

		EqualsTest other = (EqualsTest) obj;

		boolean result = true;
		result = result && (age == other.age);
		result = result && (name == null ? other.name == null : name.equals(other.name));

		return result;
	}


	@Test
	public void equalsTest(){

		EqualsTest target1 = new EqualsTest();
		assertThat(target1.equals(null), is(false));

		EqualsTest target2 = new EqualsTest();
		assertThat(target1.equals(target2), is(true));

		target1.name = "name";
		assertThat(target1.equals(target2), is(false));

		target2.name = "name";
		assertThat(target1.equals(target2), is(true));

		target1.name = "name2";
		assertThat(target1.equals(target2), is(false));

		target2.name = "name2";
		assertThat(target1.equals(target2), is(true));

		target1.age = 15;
		assertThat(target1.equals(target2), is(false));

		target2.age = 15;
		assertThat(target1.equals(target2), is(true));

		target1.name = null;
		assertThat(target1.equals(target2), is(false));

		target2.name = null;
		assertThat(target1.equals(target2), is(true));
	}


	@Test
	public void myEqualsTest(){

		EqualsTest target1 = new EqualsTest();
		assertThat(target1.myEquals(null), is(false));

		EqualsTest target2 = new EqualsTest();
		assertThat(target1.myEquals(target2), is(true));

		target1.name = "name";
		assertThat(target1.myEquals(target2), is(false));

		target2.name = "name";
		assertThat(target1.myEquals(target2), is(true));

		target1.name = "name2";
		assertThat(target1.myEquals(target2), is(false));

		target2.name = "name2";
		assertThat(target1.myEquals(target2), is(true));

		target1.age = 15;
		assertThat(target1.myEquals(target2), is(false));

		target2.age = 15;
		assertThat(target1.myEquals(target2), is(true));

		target1.name = null;
		assertThat(target1.myEquals(target2), is(false));

		target2.name = null;
		assertThat(target1.myEquals(target2), is(true));
	}

	@Test
	public void child(){
		EqualsTest target1 = new ChildEqualsTest();
		EqualsTest target2 = new EqualsTest();
		assertThat(target1.equals(target2), is(false));
		assertThat(target1.myEquals(target2), is(true));
	}

	public class ChildEqualsTest extends EqualsTest{
	}

}
