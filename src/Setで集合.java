import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;


public class Setで集合 {

	private Set<String> set1;
	private Set<String> set2;

	@Before
	public void tearUp(){
		set1 = new HashSet<>();
		set1.add("a");
		set1.add("b");
		set1.add("c");

		set2 = new HashSet<>();
		set2.add("c");
		set2.add("d");
		set2.add("e");
	}

	@Test
	public void 和集合(){
		set1.addAll(set2);
		assertThat(set1.size(), is(5));
	}

	@Test
	public void 差集合(){
		set1.removeAll(set2);
		assertThat(set1.size(), is(2));
	}

	@Test
	public void 積集合(){
		set1.retainAll(set2);
		assertThat(set1.size(), is(1));
	}

}
