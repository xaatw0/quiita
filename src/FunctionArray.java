import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Test;

/**
 * 関数インターフェースを配列にして使用したときの例
 */
public class FunctionArray {

	/**
	 * 地域名
	 */
	private enum Region{
		北海道地方,
		東北地方,
		関東地方,
		中部地方,
		近畿地方,
		中国地方,
		四国地方,
		九州地方
	}

	/**
	 * 都道府県の情報
	 */
	private static class Prefecture{
		public String name;
		public Region region;

		public Prefecture(String name, Region region){
			this.name = name;
			this.region = region;
		}
	}

	/**
	 * 地域名と関数を結びつけるクラス
	 */
	private static class RegionFunc{
		public String name;
		public Predicate<Prefecture> func;

		public RegionFunc(String name, Predicate<Prefecture> func){
			this.name = name;
			this.func = func;
		}
	}

	@Test
	public void test(){

		List<Prefecture> data = new ArrayList<>();
		data.add(new Prefecture("北海道",Region.北海道地方));

		data.add(new Prefecture("青森", Region.東北地方));
		data.add(new Prefecture("岩手", Region.東北地方));
		data.add(new Prefecture("宮城", Region.東北地方));
		data.add(new Prefecture("秋田", Region.東北地方));
		data.add(new Prefecture("山形", Region.東北地方));
		data.add(new Prefecture("福島", Region.東北地方));

		data.add(new Prefecture("東京", Region.関東地方));
		data.add(new Prefecture("茨城", Region.関東地方));
		data.add(new Prefecture("栃木", Region.関東地方));
		data.add(new Prefecture("群馬", Region.関東地方));
		data.add(new Prefecture("埼玉", Region.関東地方));
		data.add(new Prefecture("千葉", Region.関東地方));
		data.add(new Prefecture("神奈川", Region.関東地方));

		RegionFunc[] functions = {
				new RegionFunc("東北", p -> p.region == Region.東北地方),
				new RegionFunc("関東", p -> p.region == Region.関東地方),
				new RegionFunc("北海道・東北", p -> p.region == Region.北海道地方 || p.region == Region.東北地方)
		};

		String[] regionName = new String[]{"東北", "関東", "北海道・東北"};
		long[] result = {6L, 7L, 7L};

		for (int i = 0; i < functions.length; i++){
			assertThat(functions[i].name, is(regionName[i]));
			assertThat(data.stream().filter(functions[i].func).count(), is(result[i]));
		}
	}
}
