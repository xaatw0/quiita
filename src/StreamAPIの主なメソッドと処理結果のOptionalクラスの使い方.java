
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;


/**
 * 元ネタ:
 * Java 8はラムダ式でここまで変わる（4）：
 * Stream APIの主なメソッドと処理結果のOptionalクラスの使い方
 * http://www.atmarkit.co.jp/ait/articles/1405/20/news032.html
 * @author sakura
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

	@Test @Ignore
	public void ファイルからStreamの生成() throws IOException{

		// ファイルが必要なので、実行しない

		// Pathから生成
		try(Stream<String> stream1 = Files.lines(Paths.get("test"))){
			// StreamはAutoCloseを実装しているので、これでファイルは閉じるらしい
		}

		// BufferedReaderから生成
		BufferedReader br = new BufferedReader(null);
		Stream<String> stream2 = br.lines();
	}

	@Test
	public void 範囲からStreamの生成(){

		assertThat(IntStream.range(0, 10).sum(), is(45));
		assertThat(IntStream.rangeClosed(0, 10).sum(), is(55));

		assertThat(LongStream.range(0, 10).sum(), is(45L));

		// Integerの連続した数の配列を生成する
		Integer[] result = IntStream.range(10, 20).mapToObj(Integer::valueOf).toArray(Integer[]::new);
		assertThat(result.length, is(10));
		assertThat(result[0], is(Integer.valueOf(10)));
		assertThat(result[9], is(Integer.valueOf(19)));
	}

	@Test
	public void 要素を絞り込むメソッド(){

		Integer[] array = {1,2,3,4,5,1,2,3,4,5};

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
	public void Streamは変更せずに処理を加える(){
		Person person1 = new Person("サンプル 太郎");
		Person person2 = new Person("デモ 花子");

		List<Person> list = new ArrayList<>();
		list.add(person1);
		list.add(person2);

		// 最初は子供がいない
		assertThat(list.stream().mapToInt(p-> p.getChildren().size()).sum(), is(0));

		// peekでstream内の各Personに子供「test」を追加している。
		// 2人に1人づつ追加しているので、合計が2になる
		assertThat(list.stream()
				.peek(p -> p.addChild(new Person("test")))
				.mapToInt(p-> p.getChildren().size())
				.sum(), is(2));

		assertThat(person1.getChildren().size(), is(1));
	}

	@Test
	public void 要素を並び替えるメソッド() throws Exception {
		List<String> list = Arrays.asList(new String[]{"お", "あ","い"});

		Stream<String> sortedStream =
				list.stream()
				.sorted((e1,e2) -> e1.compareTo(e2));
		assertThat(sortedStream.findFirst().get(), is("あ"));

		sortedStream =
				list.stream()
				.sorted(Comparator.comparing(String::toString));
		assertThat(sortedStream.findFirst().get(), is("あ"));

		sortedStream =
				list.stream()
				.sorted(Comparator.comparing(String::toString).reversed());
		assertThat(sortedStream.findFirst().get(), is("お"));

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

		// OptionalでNullチェック
		assertThat(Optional.ofNullable("").isPresent(), is(true));
		assertThat(Optional.ofNullable(null).isPresent(), is(false));

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

		// 最大値の取得
		Optional<BigDecimal> max = list.stream().max((a, b) -> a.compareTo(b));
		assertThat(max.get(), is(BigDecimal.valueOf(5)));

		// 最小値の取得
		Optional<BigDecimal> min = list.stream().min((a, b) -> a.compareTo(b));
		assertThat(min.get(), is(BigDecimal.valueOf(1)));

		// 要素の最初のものを取得
		Optional<BigDecimal> first = list.stream().findFirst();
		assertThat(first.get(), is(BigDecimal.valueOf(3)));

		// 要素の3番目のものを取得
		Optional<BigDecimal> third = list.stream().skip(2).findFirst();
		assertThat(third.get(), is(BigDecimal.valueOf(5)));

		// 要素の中のどれか1つを取得
		Optional<BigDecimal> any = list.stream().findAny();
		assertThat(any.get(), is(anyOf(
				is( BigDecimal.valueOf(1)),
				is( BigDecimal.valueOf(2)),
				is( BigDecimal.valueOf(3)),
				is( BigDecimal.valueOf(4)),
				is( BigDecimal.valueOf(5)))));
	}

	@Test
	public void 並列処理() throws Exception {
		List<String> list = Arrays.asList(new String[]{"あ", "い", "う", "え", "お"});

		final StringBuilder sb = new StringBuilder();

		Stream<String> parallelStream1 = list.parallelStream();
		parallelStream1.forEach(value -> sb.append(value));
		assertThat(sb.toString(), is(not("あいうえお")));
		// ↑ まれに一致して、エラーになる。並列処理で順番通りに出力できたとき。

		// 順番に取得する
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
		assertThat(Arrays.stream(intValues).max().getAsInt(), is(5));
		assertThat(Arrays.stream(intValues).min().getAsInt(), is(1));

		long[] longValues = {1L,2L,3L,4L,5L};
		assertThat(Arrays.stream(longValues).sum(), is(15L));
		assertThat(Arrays.stream(longValues).average().getAsDouble(), is(3.0));

		double[] doubleValues = {1.0,2.0,3.0,4.0,5.0};
		assertThat(Arrays.stream(doubleValues).sum(), is(15.0));
		assertThat(Arrays.stream(doubleValues).average().getAsDouble(), is(3.0));

		// 合計と平均を同時に取得する
		IntSummaryStatistics statistics = Arrays.stream(intValues).summaryStatistics();
		assertThat(statistics.getCount(), is(5L));
		assertThat(statistics.getAverage(), is(3.0));
		assertThat(statistics.getSum(), is(15L));
		assertThat(statistics.getMax(), is(5));
		assertThat(statistics.getMin(), is(1));
	}

	@Test
	public void SummaryStatisticsの合成() throws Exception {

		int[] intValues1 = {1,2,3,4,5};
		int[] intValues2 = {6,7,8,9,10};

		IntSummaryStatistics statistics = Arrays.stream(intValues1).summaryStatistics();
		statistics.combine(Arrays.stream(intValues2).summaryStatistics());

		assertThat(statistics.getCount(), is(10L));
		assertThat(statistics.getAverage(), is(5.5));
		assertThat(statistics.getSum(), is(55L));
		assertThat(statistics.getMax(), is(10));
		assertThat(statistics.getMin(), is(1));
	}

	@Test
	public void 要素を配列として返すメソッド() throws Exception {

		List<BigDecimal> list = new ArrayList<>();
		list.add(BigDecimal.ONE);
		list.add(BigDecimal.ZERO);
		list.add(BigDecimal.TEN);

		BigDecimal[] bArray =list.stream().toArray(BigDecimal[]::new);

		assertThat(bArray[0], is(BigDecimal.ONE));
		assertThat(bArray[1], is(BigDecimal.ZERO));
		assertThat(bArray[2], is(BigDecimal.TEN));

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

	@Test
	public void 要素をListSetとして返すメソッド(){

		String[] array = {"A","B","C"};

		List <String> list = Arrays.stream(array).collect(Collectors.toList());
		assertThat(list.get(0), is("A"));
		assertThat(list.get(1), is("B"));
		assertThat(list.get(2), is("C"));

		Set<String> set = Arrays.stream(array).collect(Collectors.toSet());
		assertThat(set.contains("A"), is(true));
		assertThat(set.contains("B"), is(true));
		assertThat(set.contains("D"), is(false));

	}

	@Test
	public void 要素をMapとして返すメソッド(){

		Person person1 = new Person("サンプル 太郎");
		person1.addChild(new Person("サンプル 小太郎"));

		Person person2 = new Person("デモ 花子");
		person2.addChild(new Person("デモ 小太郎"));
		person2.addChild(new Person("デモ 小次郎"));

		List<Person> list = new ArrayList<>();
		list.add(person1);
		list.add(person2);

		// Map key:名前 value:子供の数
		Map<String, Integer> map =
				list.stream()
				.collect( Collectors.toMap(p -> p.getName(), p -> p.getChildren().size()));

		assertThat(map.get("サンプル 太郎"), is(1));
		assertThat(map.get("デモ 花子"), is(2));

		// Map key:名前 value: 子供のリスト
		Map<String, List<Person>> mapChild = list.stream()
				.collect( Collectors.toMap(Person::getName, Person::getChildren));

		assertThat(mapChild.get("サンプル 太郎").size(), is(1));
		assertThat(mapChild.get("デモ 花子").size(), is(2));

	}

	@Test
	public void toMap_要素重複(){
		String[] data = {"あ","い","うえ"};

		Map<String, Integer> result = Arrays.stream(data).collect(Collectors.toMap(p->p.toString(), p->p.length()));

		assertThat(result.get("あ"), is(1));
		assertThat(result.get("い"), is(1));
		assertThat(result.get("うえ"), is(2));
	}

	@Test(expected = IllegalStateException.class)
	public void toMap_鍵重複(){

		String[] data = {"あ","い","うえ"};
		Arrays.stream(data).collect(Collectors.toMap(p->p.length(), p->p.toString()));
		// java.lang.IllegalStateException: Duplicate key あ
	}

	@Test
	public void コレクター(){

		String[] array = {"A","BC","DEF"};

		//すべての文字列を連結(単に連結、間に文字を入れて連結、間と前後に文字を入れて連結)
		assertThat(Arrays.stream(array).collect(Collectors.joining()), is("ABCDEF"));
		assertThat(Arrays.stream(array).collect(Collectors.joining(",")), is("A,BC,DEF"));
		assertThat(Arrays.stream(array).collect(Collectors.joining(",","[","]")), is("[A,BC,DEF]"));

		// 合計
		assertThat(Arrays.stream(array).collect(Collectors.summingInt(s -> ((String) s).length())), is(6));
		assertThat(Arrays.stream(array).mapToInt(p->p.length()).sum(), is(6));

		// 平均
		assertThat(Arrays.stream(array).collect(Collectors.averagingInt((s -> ((String) s).length()))), is(2.0));
		assertThat(Arrays.stream(array).mapToInt(p->p.length()).average().getAsDouble(), is(2.0));
	}

	/**
	 * (参考)http://qiita.com/komiya_atsushi/items/8daac1b90d73b958c725
	 */
	@Test
	public void groupingBy_mappingコレクター(){

		String[] array = {"A","BC","DEF","G","HIJ",""};

		// Mapとグループ化
		Map<Integer,List<String>> result =
				Arrays.stream(array)
				.collect(Collectors.groupingBy(String::length,
					Collectors.mapping(String::toString, Collectors.toList())));

		assertThat(result.size(), is(4));
		assertThat(result.get(0).size(), is(1));
		assertThat(result.get(1).size(), is(2));
		assertThat(result.get(2).get(0), is("BC"));
	}

	@Test
	public void groupingBy_Count(){

		String[] array = {"AA","AC","BB","BC","BD"};

		// Mapとグループ化
		Map<String,Long> result =
				Arrays.stream(array)
				.collect(Collectors.groupingBy(p -> p.substring(0,1), Collectors.counting()));

		assertThat(result.size(), is(2));
		assertThat(result.get("A"), is(2));
		assertThat(result.get("B"), is(3));
	}

	@Test
	public void groupingBy_sum(){

		Integer[] array = {10,11,23,24,30};

		// Mapとグループ化
		Map<Integer,Integer> result =
				Arrays.stream(array)
				.collect(Collectors.groupingBy(p -> Integer.divideUnsigned(p, 10), Collectors.summingInt(Integer::intValue)));

		assertThat(result.size(), is(3));
		assertThat(result.get(1), is(21));
		assertThat(result.get(2), is(47));
		assertThat(result.get(3), is(30));
	}

	@Test
	public void reduce(){
		int[] values = {1,2,3,4,5,6,7,8,9,10};

		// 引数が2つ(初期値有り)
		int total = Arrays.stream(values).reduce(0, (x,y) -> x + y);
		assertThat(total, is(55));

		total = Arrays.stream(new int[]{}).reduce(0, (x,y) -> x + y);
		assertThat(total, is(0));

		// 引数が1つ(初期値無し)
		OptionalInt optTotal = Arrays.stream(values).reduce((x,y) -> x + y);
		assertThat(optTotal.getAsInt(), is(55));

		optTotal = Arrays.stream(new int[]{}).reduce((x,y) -> x + y);
		assertThat(optTotal.isPresent(), is(false));

		// 引数が3つ(3つめは並列の時のみ有効)
		String[] str = {"あ","い","う","え","お"};

		String appened = Arrays.stream(str).reduce("最初", (v1,v2) -> v1 + v2, (r1,r2) -> r1 +"連結"+ r2);
		assertThat(appened, is("最初あいうえお"));

		appened = Arrays.stream(str).parallel().reduce("最初", (v1,v2) -> v1 + v2, (r1,r2) -> r1 +"連結"+ r2);
		assertThat(appened, is("最初あ連結最初い連結最初う連結最初え連結最初お"));
	}

	private class Obj{

		String name;
		int x,y;
		public Obj(int x, int y){
			this.x = x; this.y = y;
		}
		public Obj(String name, int x, int y){
			this(x,y); this.name = name;
		}

		public String getName(){return name;}
		public int getX(){return x;}
		public int getY(){return y;}
	}

	@Test
	public void reduceObject(){

		List<Obj> lst = new ArrayList<>();
		lst.add(new Obj("A",1, 0));
		lst.add(new Obj("A",1, 1));
		lst.add(new Obj("B",2, 2));

		// 基データが変更にならない
		Optional<Obj> result = lst.stream().reduce((o1, o2)-> new Obj(o1.x + o2.x, o1.y + o2.y));
		assertThat(result.get().x, is(4));
		assertThat(result.get().y, is(3));

		Obj result2 = lst.stream().reduce(new Obj(0, 0), (o1, o2)-> new Obj(o1.x + o2.x, o1.y + o2.y));
		assertThat(result2.x, is(4));
		assertThat(result2.y, is(3));

		Map<String, IntSummaryStatistics> result3 =
				lst.stream()
				.collect(Collectors.groupingBy(Obj::getName, Collectors.summarizingInt(Obj::getX)));

		assertThat(result3.get("A").getSum(), is(2L));
		assertThat(result3.get("B").getSum(), is(2L));

		Map<String, Obj> result4 =
				lst.stream()
				.collect(Collectors.groupingBy(Obj::getName, Collectors.reducing(new Obj(0, 0), (o1, o2)-> new Obj(o1.x+o2.x, o1.y+o2.y))));

		assertThat(result4.get("A").getX(), is(2));
		assertThat(result4.get("B").getX(), is(2));
		assertThat(result4.get("A").getY(), is(1));
		assertThat(result4.get("B").getY(), is(2));

		// 基データが変更になる
		BinaryOperator<Obj> operator = (o1, o2) -> {
			o1.x = o1.x + o2.x;
			o1.y = o1.y + o2.y;
			return o1;
		};

		result = lst.stream().reduce(operator);
		assertThat(result.get().x, is(4));
		assertThat(result.get().y, is(3));

		result = lst.stream().reduce(operator);
		assertThat(result.get().x, is(7));
		assertThat(result.get().y, is(6));
	}
}
