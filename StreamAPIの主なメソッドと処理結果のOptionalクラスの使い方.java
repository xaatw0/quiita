
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * Java 8はラムダ式でここまで変わる（4）：
 * Stream APIの主なメソッドと処理結果のOptionalクラスの使い方
 * http://www.atmarkit.co.jp/ait/articles/1405/20/news032.html
 * @author sakura
 *
 */
public class StreamAPIの主なメソッドと処理結果のOptionalクラスの使い方 {

	@Test
	public void Streamの生成(){

		List<String> list = Arrays.asList(
		        "list1", "list2", "list3", "list4", "list5"
		);
		String[] array = {"配列1", "配列2", "配列3", "配列4", "配列5"};

		// Streamインスタンスの生成
		Stream<String> stream1 = list.stream();
		Stream<String> stream2 = Arrays.stream(array);
		Stream<String> stream3 = Stream.of("１", "２", "３", "４", "５");
		Stream<String> stream4 = Stream.of(array);

		assertThat(stream1.count(), is(5L));
		assertThat(stream2.count(), is(5L));
		assertThat(stream3.count(), is(5L));
		assertThat(stream4.count(), is(5L));
	}

	@Test
	public void 二つのStreamから一つのStreamの生成するメソッド(){

		Stream<String> stream1 = Stream.of("あ", "い", "う", "え", "お");
		Stream<String> stream2 = Stream.of("ア", "イ", "ウ", "エ", "オ");
		Stream<String> stream3 = Stream.concat(stream1, stream2);

		// 一度処理されたStreamは使用できないので、実行エラー
		// assertThat(stream1.count(), is(5L));
		assertThat(stream3.count(), is(10L));
	}

	@Test
	public void 並列処理を行うStreamの生成(){

		List<String> list = Arrays.asList(
				"list1", "list2", "list3", "list4", "list5"
		);

		// 並列処理を行うStreamを生成
		Stream<String> parallelStream1 = list.parallelStream();
		Stream<String> parallelStream2 = list.stream().parallel();

		// 生成されたStreamが並列処理を行うものか確認
		assertThat(parallelStream1.isParallel(), is(true));
		assertThat(parallelStream2.isParallel(), is(true));

		// 並列処理から直接処理に変換
		Stream<String> sequentialStream = parallelStream1.sequential();
		assertThat(sequentialStream.isParallel(), is(false));
	}

	@Test
	public void 要素を絞り込むメソッド(){

		Integer[] array = {1,2,3,4,5,1,2,3,4,5};

		Stream<Integer> stream1 = Arrays.stream(array);

		// 要素を偶数のみに絞ります
		Stream<Integer> filterStream =
				Arrays.stream(array)
				.filter(value -> value%2 == 0);
		assertThat(filterStream.count(), is(4L));

		// 最初から3要素のみ
		Stream<Integer> limitStream =
				Arrays.stream(array)
				.limit(3);
		assertThat(limitStream.count(), is(3L));

		// 要素の重複をなくします
		Stream<Integer> distinctStream =
				Arrays.stream(array)
				.distinct();
		assertThat(distinctStream.count(), is(5L));
	}

	@Test
	public void Streamの要素から別の要素のStreamを生成するメソッド() throws Exception {
		Person person1 = new Person("サンプル 太郎");
		person1.addChild(new Person("サンプル 小太郎"));
		person1.addChild(new Person("サンプル 小次郎"));
		person1.addChild(new Person("サンプル 三郎"));

		Person person2 = new Person("デモ 花子");
		person2.addChild(new Person("デモ 小太郎"));
		person2.addChild(new Person("デモ 小次郎"));
		person2.addChild(new Person("デモ 小太郎"));
		person2.addChild(new Person("デモ 小太郎"));

		List<Person> list = new ArrayList<>();
		list.add(person1);
		list.add(person2);

		// PersonのListから名前のStreamを作成する
		Stream<String> mapStream = list.stream().map(person -> person.getName());
		assertThat(mapStream.count(), is(2L));

		// PersonのListから子どもの名前のStreamを作成する
		Stream<Person> flatMapStream =
				list.stream()
				.flatMap(person -> person.getChildren().stream());
		assertThat(flatMapStream.count(), is(7L));
	}

	class Person {
	    /** 名前 */
	    private String name;

	    /** 子ども */
	    private List<Person> children = new ArrayList<>();
	    public Person(String name) {
	        this.name = name;
	    }
	    public String getName() {
	        return name;
	    }
	    public void addChild(Person child) {
	        children.add(child);
	    }
	    public List<Person> getChildren() {
	        return children;
	    }
	}

	@Test
	public void 要素を並び替えるメソッド() throws Exception {
		List<String> list = Arrays.asList(new String[]{"お", "あ","い"});

		Stream<String> sortedStream =
				list.stream()
				.sorted((e1,e2) -> e1.compareTo(e2));
		assertThat(sortedStream.findFirst().get(), is("あ"));

		List<Integer> intList = Arrays.asList(3,2,1);
		Stream<Integer> sortedStreamInt =
				intList.stream()
				.sorted();
		assertThat(sortedStreamInt.findFirst().get(), is(1));
	}

	@Test
	public void 結果として使われるOptional() throws Exception {

		Optional<String> optional = Optional.of("あ");
		Optional<String> empty = Optional.empty();

		// String"あ"を格納したOptionalから、その値を取得しています
		assertThat(optional.get(), is("あ"));

		try{
			empty.get();
			fail();
		}catch(NoSuchElementException ex){
			// 要素がない場合、NoSuchElementExceptionをthrowする
		}

		// Optionalに値が存在する場合はTrueを返します
		assertThat(optional.isPresent(), is(true));
		assertThat(empty.isPresent(), is(false));

		// // Optionalに値があればその値を、なければorElseで指定した値を返します
		assertThat(optional.orElse("else"), is("あ"));
		assertThat(empty.orElse("else"), is("else"));

		// Optionalに値があればその値を、なければSupplierが生成した値を返します
		assertThat(optional.orElseGet(()->"else"), is("あ"));
		assertThat(empty.orElseGet(()->"else"), is("else"));

		// Optionalに値がなければ指定した例外をThrowします
		try{
			optional.orElseThrow(()->new Exception("発生しない"));
		    empty.orElseThrow(()->new Exception("発生する"));
		    fail();
		}
		catch (Exception e){
		    assertThat(e.getMessage(), is("発生する"));
		}

		// 引数の値がNullでなければその値を格納したOptionalを、NullならemptyなOptionalを返します
		assertThat(Optional.ofNullable(optional), is(Optional.of(optional)));
		assertThat(Optional.ofNullable(null), is(Optional.empty()));

		// Optionalに値がある場合、ifPresentの処理が実行されます
		Optional.of(optional).ifPresent(value -> assertThat(value.get(), is("あ")));
		//Optional.of(empty).ifPresent(value -> fail());


		// Optionalの値から別の値を生成したOptionalを返します。
		Optional<String> stringOptional = Optional.of("12345");
		Optional<BigDecimal> bigDecimalOptional = stringOptional.map(value -> new BigDecimal(value));
		assertThat(bigDecimalOptional.get(), is(BigDecimal.valueOf(12345)));

		// filterの結果がTrueの場合はそのままのOptionalを返します
		Optional<BigDecimal> bigdiOptional = Optional.of(BigDecimal.ONE)
		.filter(value -> value.compareTo(BigDecimal.ZERO) > 0);
		assertThat(bigdiOptional.isPresent(), is(true));
		assertThat(bigdiOptional.get(), is(BigDecimal.ONE));

		// filterの結果がFalseの場合はEmptyなOptionalを返します
		bigdiOptional =
				Optional.of(BigDecimal.ONE)
				.filter(value -> value.compareTo(BigDecimal.TEN) > 0);
		assertThat(bigdiOptional.isPresent(), is(false));
	}

	@Test
	public void Streamの中から特定の要素を取得するメソッド() throws Exception {

		List<BigDecimal> list = new ArrayList<>();
		list.add(BigDecimal.valueOf(3));
		list.add(BigDecimal.valueOf(4));
		list.add(BigDecimal.valueOf(5));
		list.add(BigDecimal.valueOf(1));
		list.add(BigDecimal.valueOf(2));

		//
		Optional<BigDecimal> max = list.stream().max((a, b) -> a.compareTo(b));
		assertThat(max.get(), is(BigDecimal.valueOf(5)));

		// 最小値の取得
		Optional<BigDecimal> min = list.stream().min((a, b) -> a.compareTo(b));
		assertThat(min.get(), is(BigDecimal.valueOf(1)));

		// 要素の最初のものを取得
		Optional<BigDecimal> first = list.stream().findFirst();
		assertThat(first.get(), is(BigDecimal.valueOf(3)));

		// 要素の中のどれか1つを取得
		Optional<BigDecimal> any = list.stream().findAny();
		assertThat(max.get(), is(anyOf(
				is( BigDecimal.valueOf(1)),
				is( BigDecimal.valueOf(2)),
				is( BigDecimal.valueOf(3)),
				is( BigDecimal.valueOf(4)),
				is( BigDecimal.valueOf(5)))));
	}

	@Test
	public void testName() throws Exception {
		List<String> list = Arrays.asList(new String[]{"あ", "い", "う", "え", "お"});

		final StringBuilder sb = new StringBuilder();

		Stream<String> parallelStream1 = list.parallelStream();
		parallelStream1.forEach(value -> sb.append(value));
		assertThat(sb.toString(), is(not("あいうえお")));

		final StringBuilder sb2 = new StringBuilder();
		parallelStream1 = list.parallelStream();
		parallelStream1.forEachOrdered(value -> sb2.append(value));
		assertThat(sb2.toString(), is("あいうえお"));
	}

	@Test
	public void 要素を判定するメソッド() throws Exception {

		List<BigDecimal> list = new ArrayList<>();
		list.add(BigDecimal.valueOf(3));
		list.add(BigDecimal.valueOf(4));
		list.add(BigDecimal.valueOf(5));
		list.add(BigDecimal.valueOf(1));
		list.add(BigDecimal.valueOf(2));

		// 要素の全てが0より大きければTrue
		boolean result = list.stream().allMatch(value -> value.compareTo(BigDecimal.ZERO) > 0);
		assertThat(result, is(true));

		// 要素の全てが1より大きければTrue
		result = list.stream().allMatch(value -> value.compareTo(BigDecimal.ONE) > 0);
		assertThat(result, is(false));

		// 要素のどれかが3と等しければTrue
		result = list.stream().anyMatch(value -> value.compareTo(BigDecimal.ONE) == 0);
		assertThat(result, is(true));

		// 要素のどれかが10と等しければTrue
		result = list.stream().anyMatch(value -> value.compareTo(BigDecimal.TEN) == 0);
		assertThat(result, is(false));

		// 要素の中に1以下のものが1つも含まれないならTrue
		result = list.stream().noneMatch(value -> value.compareTo(BigDecimal.ONE) <= 0);
		assertThat(result, is(false));

		// 要素の中に0以下のものが1つも含まれないならTrue
		result = list.stream().noneMatch(value -> value.compareTo(BigDecimal.ZERO) <= 0);
		assertThat(result, is(true));
	}

	@Test
	public void Streamが持つ要素数を返すメソッド() throws Exception {

		List<BigDecimal> list = new ArrayList<>();
		list.add(BigDecimal.valueOf(3));
		list.add(BigDecimal.valueOf(4));

		assertThat(list.stream().count(), is(2L));
	}

	@Test
	public void 数値の集計を行うメソッド() throws Exception {

		int[] intValues = {1,2,3,4,5};
		assertThat(Arrays.stream(intValues).sum(), is(15));
		assertThat(Arrays.stream(intValues).average().getAsDouble(), is(3.0));

		long[] longValues = {1L,2L,3L,4L,5L};
		assertThat(Arrays.stream(longValues).sum(), is(15L));
		assertThat(Arrays.stream(longValues).average().getAsDouble(), is(3.0));

		double[] doubleValues = {1.0,2.0,3.0,4.0,5.0};
		assertThat(Arrays.stream(doubleValues).sum(), is(15.0));
		assertThat(Arrays.stream(doubleValues).average().getAsDouble(), is(3.0));
	}

	@Test
	public void 要素を配列として返すメソッド() throws Exception {

		List<BigDecimal> list = new ArrayList<>();
		list.add(BigDecimal.ONE);
		list.add(BigDecimal.ZERO);
		list.add(BigDecimal.TEN);

		Object[] array = list.stream().toArray();
		assertThat(array[0], is(BigDecimal.ONE));
		assertThat(array[1], is(BigDecimal.ZERO));
		assertThat(array[2], is(BigDecimal.TEN));

		array =
				list.stream()
				.toArray(count -> {
					return new BigDecimal[count];
				});
		assertThat(array[0], is(BigDecimal.ONE));
		assertThat(array[1], is(BigDecimal.ZERO));
		assertThat(array[2], is(BigDecimal.TEN));

		array =
				list.stream()
				.toArray(count ->new BigDecimal[count]);
		assertThat(array[0], is(BigDecimal.ONE));
		assertThat(array[1], is(BigDecimal.ZERO));
		assertThat(array[2], is(BigDecimal.TEN));

	}
}
