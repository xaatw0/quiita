import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;


public class 隣り合う数でMapを作る {

	@Test
	public void 連続() throws Exception {

		int[] target = {1,2,3,5,6,7,8,9,10, 20,21,22};
		List<Set<Integer>> set = getIntegerSet(target);
		assertThat(set.size(), is(3));
	}

	@Test
	public void ランダム() throws Exception {

		int[] target = {5,6,1,2,20,3,8,9,10, 21,7,22};
		List<Set<Integer>> set = getIntegerSet(target);
		assertThat(set.size(), is(3));
	}

	@Test
	public void ランダム2() throws Exception {

		int[] target = {5,6,33,34,1,2,20,3,8,36,9,10,21,7,22,35};
		List<Set<Integer>> set = getIntegerSet(target);
		assertThat(set.size(), is(4));
	}

	static List<Set<Integer>> getIntegerSet(int[] target){

		List<Set<Integer>> global = new ArrayList<Set<Integer>>();

		for (int value: target){

			Set<Integer> firstMatch = null;

			for (int i = global.size() -1; -1 < i; i--){

				Set<Integer> set = global.get(i);
				if (isNext(value, set)){

					if (firstMatch == null){
						firstMatch = set;
					} else {
						global.remove(i);
						firstMatch.addAll(set);
					}
				}
			}

			if (firstMatch == null){
				Set<Integer> set = new HashSet<Integer>();
				set.add(value);
				global.add(set);
			} else {
				firstMatch.add(value);
			}
		}

		return global;
	}

	static boolean isNext(int target, Set<Integer> set){

		for (Integer value: set){

			if (value == (target - 1) ||value == (target + 1)){
				return true;
			}
		}
		return false;
	}


	@Test
	public void mapremove(){
		Set<Integer> set = new HashSet<Integer>();

		set.add(1);
		set.add(2);
		set.add(3);
		set.add(4);
		assertThat(set.size(), is(4));

		set.remove(3);
		assertThat(set.size(), is(3));
	}

	@Test
	public void map_mapRemove(){
		Set<Set<Integer>> set = new HashSet<Set<Integer>>();

		Set<Integer> first = new HashSet<Integer>();
		first.add(1);
		set.add(first);

		Set<Integer> second = new HashSet<Integer>();
		second.add(2);
		set.add(second);

		// 値が2と同じため、無視される
		Set<Integer> second_dummy = new HashSet<Integer>();
		second_dummy.add(2);
		set.add(second_dummy);

		// second_dummyは無視されたので、2
		assertThat(set.size(), is(2));

		// 一つ消す
		set.remove(second);
		assertThat(set.size(), is(1));

		// firstに値を加えたので、消せない
		first.add(3);
		set.remove(first);
		assertThat(set.size(), is(1));

		// firstに加えた値を消したので、消せる
		first.remove(3);
		set.remove(first);
		assertThat(set.size(), is(0));
	}

	@Test
	public void map_mapRemove2(){
		// がんばったが、無駄だった。
		Set<Set<Integer>> set = new HashSet<Set<Integer>>();

		Set<Integer> first = new HashSet<Integer>();
		first.add(1);
		set.add(first);

		Set<Integer> second = new HashSet<Integer>(){
			Integer hashCode = null;

			@Override
			public int hashCode(){
				return hashCode;
			}

			@Override
			public boolean add(Integer o){

				if (hashCode == null){
					hashCode = o.hashCode();
				}

				return super.add(o);
			}

			@Override
			public boolean equals(Object o){
				return true;
			}

		};
		second.add(2);
		set.add(second);

		// 一つ消す
		set.remove(second);
		assertThat(set.size(), is(1));

		// firstに値を加えたので、消せない
		first.add(3);
		assertThat(set.remove(first),is(true));
		assertThat(set.size(), is(0));
	}


	@Test
	public void list_mapremove(){
		List<Set<Integer>> list = new ArrayList<Set<Integer>>();

		Set<Integer> first = new HashSet<Integer>();
		first.add(1);
		list.add(first);

		Set<Integer> second = new HashSet<Integer>();
		second.add(2);
		list.add(second);

		// 一つ消す
		list.remove(second);
		assertThat(list.size(), is(1));

		// firstに値を加えたが、消せる
		first.add(3);
		assertThat(list.remove(first),is(true));
		assertThat(list.size(), is(0));
	}

	@Test
	public void array_map(){

		Set<Integer>[] array = new Set[2];

		Set<Integer> set = new HashSet<Integer>();
		set.add(1);
		array[0] = set;
		array[1] = set;

		array[0].add(3);

		assertThat(array[0].size(),is(2));
		assertThat(array[1].size(),is(2));
	}
}
