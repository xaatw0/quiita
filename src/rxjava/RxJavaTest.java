package rxjava;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import rx.Observable;
import rx.functions.Func1;

public class RxJavaTest {

	@Test
	public void create(){

	}

	@Test
	public void just(){
		List<String> result = new ArrayList<>();
		Observable<String> observable = Observable.just("a","b");
		observable.subscribe(p->result.add(p));
		assertThat(result.size(), is(2));
		assertThat(result.get(0), is("a"));
		assertThat(result.get(1), is("b"));
	}

	@Test
	public void repeat(){
		List<String> result = new ArrayList<>();
		Observable<String> observable = Observable.just("a","b").repeat(3);
		observable.subscribe(p->result.add(p));
		assertThat(result.size(), is(6));
		assertThat(result.get(0), is("a"));
		assertThat(result.get(1), is("b"));
		assertThat(result.get(2), is("a"));
		assertThat(result.get(3), is("b"));
		assertThat(result.get(4), is("a"));
		assertThat(result.get(5), is("b"));
	}

	@Test
	public void from(){
		List<String> result = new ArrayList<>();

		String[] array = new String[]{"sasaki","ササキ","ささき","佐々木"};
		Observable<String> observable = Observable.from(array);
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(4));
		assertThat(result.get(0), is("sasaki"));
		assertThat(result.get(1), is("ササキ"));
		assertThat(result.get(2), is("ささき"));
		assertThat(result.get(3), is("佐々木"));
	}

	@Test
	public void range(){
		List<Integer> result = new ArrayList<>();
		Observable<Integer> observable = Observable.range(2, 4);
		observable.subscribe(p->result.add(p));
		assertThat(result.size(), is(4));
		assertThat(result.get(0), is(2));
		assertThat(result.get(1), is(3));
		assertThat(result.get(2), is(4));
		assertThat(result.get(3), is(5));
	}

	@Test
	public void filter(){

		List<Integer> result = new ArrayList<>();

		Func1<Integer, Boolean> filter = p -> p < 15;
		Observable<Integer> observable = Observable.just(1,10,4,21,19,2,13,9).filter(filter);
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(6));
		assertThat(result.get(0), is(1));
		assertThat(result.get(1), is(10));
		assertThat(result.get(2), is(4));
		assertThat(result.get(3), is(2));
		assertThat(result.get(4), is(13));
		assertThat(result.get(5), is(9));
	}

	@Test
	public void take(){
		List<Integer> result = new ArrayList<>();
		Observable<Integer> observable = Observable.just(1,10,4,21,19,2,13,9).take(2);
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(1));
		assertThat(result.get(1), is(10));
	}

	@Test
	public void takeLast(){
		List<Integer> result = new ArrayList<>();
		Observable<Integer> observable = Observable.just(1,10,4,21,19,2,13,9).takeLast(2);
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(13));
		assertThat(result.get(1), is(9));
	}

	@Test
	public void first(){
		List<Integer> result = new ArrayList<>();
		Observable<Integer> observable = Observable.just(1,10,4,21,19,2,13,9).first();
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(1));
	}

	@Test
	public void last(){
		List<Integer> result = new ArrayList<>();
		Observable<Integer> observable = Observable.just(1,10,4,21,19,2,13,9).last();
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(9));
	}

	@Test
	public void elementAt(){
		List<Integer> result = new ArrayList<>();
		Observable<Integer> observable = Observable.just(1,10,4,21,19,2,13,9).elementAt(2);
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(4));
	}

	@Test
	public void elementAtOrDefault(){
		List<Integer> result = new ArrayList<>();
		Observable<Integer> observable = Observable.just(1,10).elementAtOrDefault(3,100);
		observable.subscribe(p->result.add(p));

		assertThat(result.size(), is(1));
		assertThat(result.get(0), is(100));
	}
}
