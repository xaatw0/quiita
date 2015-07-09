package basic;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

// http://java-reference.sakuraweb.com/java_string_regex.html
public class RegexTest {

	@Test
	public void matchsfind(){

		Pattern pattern = Pattern.compile("ABC");

		// matches()は、文字列全体がパターンにマッチしているか
		// find()は、文字列の一部がパターンにマッチしているか
		Matcher matcher = pattern.matcher("ABC");
		assertThat(matcher.matches(), is(true));
		assertThat(matcher.find(), is(false));

		matcher = pattern.matcher("AABC");
		assertThat(matcher.matches(), is(false));
		assertThat(matcher.find(), is(true));

		matcher = pattern.matcher("ABCA");
		assertThat(matcher.matches(), is(false));
		assertThat(matcher.find(), is(true));

		matcher = pattern.matcher("ABD");
		assertThat(matcher.matches(), is(false));
		assertThat(matcher.find(), is(false));
	}

	@Test
	public void asPredicate(){

		// JDK 1.8以上
		// 文字列全体でも文字列の一部でもマッチして、便利そう
		// predicate 【自動】断言する 【他動】〔行動や議論などの〕基礎を置く(alc.co.jpより)

		Predicate<String> predicate = Pattern.compile("ABC").asPredicate();
		assertThat(predicate.test("ABC"), is(true));
		assertThat(predicate.test("AABC"), is(true));
		assertThat(predicate.test("ABCA"), is(true));
		assertThat(predicate.test("ABD"), is(false));
	}

	@Test
	public void pattern(){

		// 「.」は任意の一文字
		Pattern pattern = Pattern.compile("A.C");
		assertThat(pattern.matcher("ABC").matches(), is(true));
		assertThat(pattern.matcher("ADC").matches(), is(true));
		assertThat(pattern.matcher("ABBC").matches(), is(false));
	}

}
