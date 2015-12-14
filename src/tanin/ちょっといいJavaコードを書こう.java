package tanin;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

import org.junit.Test;

public class ちょっといいJavaコードを書こう {


	@Test(expected=NullPointerException.class)
	public void プリミティブのラッパークラスを使わない(){
		OptionalInt test = OptionalInt.of(5);


		Map<String, Long> map = new HashMap<>();
		long value = map.get("nothing").longValue();
	}

	@Test
	public void intとInteger(){

		Map<String, Integer> map = new HashMap<>();

		map.put("target1", Integer.valueOf(100));
		map.put("target2", Integer.valueOf("100"));
		assertThat(map.get("target1") == map.get("target2"), is(true));

		map.put("target1", Integer.valueOf(127));
		map.put("target2", Integer.valueOf("127"));
		assertThat(map.get("target1") == map.get("target2"), is(true));

		map.put("target1", new Integer(127));
		map.put("target2", Integer.valueOf("127"));
		assertThat(map.get("target1") == map.get("target2"), is(false));

		map.put("target1", Integer.valueOf(128));
		map.put("target2", Integer.valueOf("128"));
		assertThat(map.get("target1") == map.get("target2"), is(false));

		map.put("target1", Integer.valueOf(Integer.MAX_VALUE));
		map.put("target2", Integer.valueOf(Integer.MAX_VALUE));
		assertThat(map.get("target1") == map.get("target2"), is(false));

		map.put("target1", Integer.valueOf(300));
		map.put("target2", Integer.valueOf("300"));
		assertThat(map.get("target1").intValue() == map.get("target2").intValue(), is(true));

		Integer one = Integer.valueOf(300);
		Integer one2 = Integer.valueOf(300);
		assertThat(one == one2, is(false));

		assertThat(one.equals(300), is(true));
		assertThat(one == 300, is(true));
		assertThat(300 == one, is(true));


		map.put("target1", Integer.valueOf(127));
		map.put("target2", Integer.valueOf("127"));
		assertThat(map.get("target1") == map.get("target2"), is(true)); // キャッシュを取得しているので同じインスタンス

		map.put("target1", new Integer(127));
		map.put("target2", Integer.valueOf("127"));
		assertThat(map.get("target1") == map.get("target2"), is(false));// インスタンス生成のため、違うインスタンス

		map.put("target1", Integer.valueOf(128));
		map.put("target2", Integer.valueOf("128"));
		assertThat(map.get("target1") == map.get("target2"), is(false)); // キャッシュ外のため、違うインスタンス
		assertThat(map.get("target1").intValue() == map.get("target2").intValue(), is(true)); // 意味する数字の値は同じ
		assertThat(map.get("target1") == 128, is(true));                 // 比較前にオートボクシングされている

		map.put("target1", Integer.valueOf(-128));
		map.put("target2", Integer.valueOf("-128"));
		assertThat(map.get("target1") == map.get("target2"), is(true)); // キャッシュを取得しているので同じインスタンス

		map.put("target1", Integer.valueOf(Integer.MAX_VALUE));
		map.put("target2", Integer.valueOf(Integer.MAX_VALUE));
		assertThat(map.get("target1") == map.get("target2"), is(false)); // キャッシュ外のため、違うインスタンス
	}

	@Test
	public void 三項演算子(){
		// (参考) http://hp.vector.co.jp/authors/VA010341/conditional.html
		boolean flg1 = false;
		boolean flg2 = false;
		boolean flg3 = true;

		String test =
				flg1 ? "flg1"
				: flg2 ? "flg2"
				: flg3 ? "flg3"
				:"else";

		assertThat(test,is("flg3"));

		test = flg1
				? flg2 ? "flg2=true": "flg2=false"
				: flg3 ? "flg3=true": "flg3=false";
		assertThat(test,is("flg3=true"));
	}

}
