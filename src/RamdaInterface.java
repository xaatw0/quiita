import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;


public class RamdaInterface {

	@FunctionalInterface
	interface ThrowingConsumer<T, E extends Throwable> {
		void accept(T t) throws E;
	}

	static <T, E extends Throwable> void forEachThrowing(Iterable<T> iter, ThrowingConsumer<T, E> cons) throws E {
		for (T element : iter) {
			cons.accept(element);
		}
	}

	@Test
	public void throwInterface() throws IOException {
		String[] args = {"aa","bb","c"};
		List<String> list = Arrays.asList(args);
		try (FileWriter w = new FileWriter("hoge.txt")) {
			forEachThrowing(list, str -> w.write(str));
		}
	}

	/**
	 * メソッド名: finisher
	 * 戻り値の型: Function<A,R>
	 * ラムダ式表現: A -> R
	 * 内容: 後処理 → 集積オブジェクトを最後に変換する
	 */
	private class StringStringFunction implements Function<String, String>{

		StringBuffer sb = new StringBuffer();

		@Override
		public String apply(String t) {
			sb.append(t);
			return "[" + sb.toString() + "]";
		}
	}

	@Test
	public void StringStringFunctionTest(){

		StringStringFunction target = new StringStringFunction();
		assertThat(target.apply("test"),is("[test]"));

		String[] data = {"AB","CD","EF"};
		String[] result = Arrays.stream(data).map(new StringStringFunction()).toArray(String []::new);

		assertThat(result.length, is(data.length));
		assertThat(result[0],is("[AB]"));
		assertThat(result[1],is("[ABCD]"));
		assertThat(result[2],is("[ABCDEF]"));

		Function<String, String> func = new StringStringFunction();
		result = Arrays.stream(data).map(func).toArray(String []::new);
		assertThat(result.length, is(data.length));
	}

	@Test
	public void Fucntion(){
		Function<String, Integer> function = str -> Integer.parseInt(str);
		assertThat(function.apply("123"), is(123));

		String[] data = {"1","12","123"};
		Integer[] result = Arrays.stream(data).map(function).toArray(Integer []::new);
		assertThat(result.length, is(data.length));
		assertThat(result[0],is(1));
		assertThat(result[1],is(12));
		assertThat(result[2],is(123));

		result = Arrays.stream(data).map(str -> Integer.parseInt(str)).toArray(Integer []::new);
		assertThat(result.length, is(data.length));
		assertThat(result[0],is(1));
		assertThat(result[1],is(12));
		assertThat(result[2],is(123));
	}


	/**
	 * メソッド名: combiner
	 * 戻り値の型: BinaryOperator<A>
	 * ラムダ式表現: (A,A) -> A
	 * 内容: 結合 → 並列処理の結果2つから1つの結果にまとめる
	 * 同じ型の2つの引数を受け取り、演算を行い、引数と同じ型の値を返す。
	 */
	private class BinaryOperatorTest implements BinaryOperator<String>{

		@Override
		public String apply(String t, String u) {

			return t + u;
		}


	}

	@Test
	public void BinaryOperator(){

		String[] data = {"A","BC","DE","F"};

		String result = Arrays.stream(data).reduce(new BinaryOperatorTest()).get();
		assertThat(result, is("ABCDEF"));

		Arrays.stream(data).reduce((e1, e2)-> e1 + e2).get();
		assertThat(result, is("ABCDEF"));
	}


	/**
	 * メソッド名: supplier
	 * 戻り値の型: Supplier<A>
	 * ラムダ式表現: () -> A
	 * 内容: 前処理 → 要素の集積に使うオブジェクトを生成する
	 * （引数なしで）値を返す。基本的には固定値を返すことに使う。（いわゆる「遅延評価」用）
	 * 返す値は呼ばれるごとに毎回異なっていても構わない（固定であることは要求されていない）。
	 */
	private class SupplierTest implements Supplier<String>{

		@Override
		public String get() {
			return "A";
		}
	}

	@Test
	public void Supplier(){

		String result = new SupplierTest().get();
		assertThat(result, is("A"));

		Supplier<String> func = ()->"a";
		assertThat( func.get() , is("a"));
	}


	/**
	 * 値を返さないので、基本的に副作用を起こす目的で使用する。
	 */
	public class ConsumerTest implements Consumer<String>{

		@Override
		public void accept(String t) {
			System.out.println(t);
		}
	}

	@Test
	public void Consumer(){

		ConsumerTest test = new ConsumerTest();
		test.accept("ConsumerTes: test");

		String[] data = {"AB","CDE","F"};
		Arrays.stream(data).peek(test).forEach(test);

	}


	@FunctionalInterface
	private interface FuncInterfaceTest{
		public int example(int n);
		// public int test(); ← @FunctionalInterface がついているので、コンパイルエラーになる
	}

	@FunctionalInterface
	private interface FuncInterfaceTestThree{
		public int example(int v1, int v2, int v3);
	}

	private class FuncClass{

		public int calc(int value, FuncInterfaceTest func){
			return func.example(value);
		};

		public int calc(FuncInterfaceTestThree func, int v1, int v2, int v3){
			return func.example(v1, v2, v3);
		};
	}

	@Test
	public void FuncUsage(){

		FuncClass target = new FuncClass();

		assertThat(target.calc(5, v-> v ), is(5));
		assertThat(target.calc(5, v-> v * v ), is(25));


		assertThat(target.calc((v1,v2,v3)-> v1 + v2 + v3, 1, 2, 3 ), is(6));
	}

	@Test
	public void reduce(){

		int [] data = {1, 5, 9, 12};

		int result = Arrays.stream(data).reduce( (t, u) -> t + u ).getAsInt();
		assertThat(result, is(27));
	}
}



