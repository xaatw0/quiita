import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.Test;

public class EqualsTest {

	private String name;
	private int age;
	private boolean isMan;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + (isMan ? 1231 : 1237);
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
		if (isMan != other.isMan)
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
		result = result && (isMan == other.isMan);
		result = result && (age == other.age);
		result = result && (name == null ? other.name == null : name.equals(other.name));

		return result;
	}


	@Test
	public void equalsTest(){

		// null, 異なるオブジェクト、自分自身との比較
		EqualsTest target1 = new EqualsTest();
		assertThat(target1.equals(null), is(false));
		assertThat(target1.equals("test"), is(false));
		assertThat(target1.equals(target1), is(true));

		// 内容が同じものと比較
		// [name:target1=null target2=null  age: target1=0 target2=0]
		EqualsTest target2 = new EqualsTest();
		assertThat(target1.equals(target2), is(true));

		// nameが異なる
		// [name:target1="name" target2=null  age: target1=0 target2=0]
		target1.name = "name";
		assertThat(target1.equals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1="name" target2="name"  age: target1=0 target2=0]
		target2.name = "name";
		assertThat(target1.equals(target2), is(true));

		// nameが異なる
		// [name:target1="name2" target2="name"  age: target1=0 target2=0]
		target1.name = "name2";
		assertThat(target1.equals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1="name2" target2="name2"  age: target1=0 target2=0]
		target2.name = "name2";
		assertThat(target1.equals(target2), is(true));

		// ageが異なる
		// [name:target1="name2" target2="name2"  age: target1=15 target2=0]
		target1.age = 15;
		assertThat(target1.equals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1="name2" target2="name2"  age: target1=15 target2=15]
		target2.age = 15;
		assertThat(target1.equals(target2), is(true));

		// nameが異なる
		// [name:target1=null target2="name2"  age: target1=15 target2=15]
		target1.name = null;
		assertThat(target1.equals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1=null target2=null  age: target1=15 target2=15]
		target2.name = null;
		assertThat(target1.equals(target2), is(true));

		// isManが異なる
		target1.isMan = true;
		assertThat(target1.equals(target2), is(false));

		// 内容が同じものと比較
		target2.isMan = true;
		assertThat(target1.equals(target2), is(true));
	}


	@Test
	public void myEqualsTest(){

		// null, 異なるオブジェクト、自分自身との比較
		EqualsTest target1 = new EqualsTest();
		assertThat(target1.myEquals(null), is(false));
		assertThat(target1.myEquals("test"), is(false));
		assertThat(target1.myEquals(target1), is(true));

		// 内容が同じものと比較
		// [name:target1=null target2=null  age: target1=0 target2=0]
		EqualsTest target2 = new EqualsTest();
		assertThat(target1.myEquals(target2), is(true));

		// nameが異なる
		// [name:target1="name" target2=null  age: target1=0 target2=0]
		target1.name = "name";
		assertThat(target1.myEquals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1="name" target2="name"  age: target1=0 target2=0]
		target2.name = "name";
		assertThat(target1.myEquals(target2), is(true));

		// nameが異なる
		// [name:target1="name2" target2="name"  age: target1=0 target2=0]
		target1.name = "name2";
		assertThat(target1.myEquals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1="name2" target2="name2"  age: target1=0 target2=0]
		target2.name = "name2";
		assertThat(target1.myEquals(target2), is(true));

		// ageが異なる
		// [name:target1="name2" target2="name2"  age: target1=15 target2=0]
		target1.age = 15;
		assertThat(target1.myEquals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1="name2" target2="name2"  age: target1=15 target2=15]
		target2.age = 15;
		assertThat(target1.myEquals(target2), is(true));

		// nameが異なる
		// [name:target1=null target2="name2"  age: target1=15 target2=15]
		target1.name = null;
		assertThat(target1.myEquals(target2), is(false));

		// 内容が同じものと比較
		// [name:target1=null target2=null  age: target1=15 target2=15]
		target2.name = null;
		assertThat(target1.myEquals(target2), is(true));

		// isManが異なる
		target1.isMan = true;
		assertThat(target1.myEquals(target2), is(false));

		// 内容が同じものと比較
		target2.isMan = true;
		assertThat(target1.myEquals(target2), is(true));
	}

	@Test
	public void child(){
		EqualsTest target1 = new EqualsTest();

		// 子クラス
		EqualsTest target2 = new ChildEqualsTest();

		// 匿名クラス
		EqualsTest target3 = new EqualsTest(){
			@Override
			public boolean equals(Object obj){
				return super.equals(obj);
			}
		};

		assertThat(target1.equals(target2), is(false));
		assertThat(target1.myEquals(target2), is(true));

		assertThat(target1.equals(target3), is(false));
		assertThat(target1.myEquals(target3), is(true));

		assertThat(target2.equals(target1), is(false));
		assertThat(target2.myEquals(target1), is(true));

		assertThat(target2.equals(target3), is(false));
		assertThat(target2.myEquals(target3), is(true));

		assertThat(target3.equals(target1), is(false));
		assertThat(target3.myEquals(target1), is(true));

		assertThat(target3.equals(target2), is(false));
		assertThat(target3.myEquals(target2), is(true));
	}

	public class ChildEqualsTest extends EqualsTest{
	}

}
