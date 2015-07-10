

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

// http://java-reference.sakuraweb.com/java_string_regex.html
public class Javaの正規表現 {

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

	/**
	 * JDK 1.8以上
	 * 文字列全体でも文字列の一部でもマッチして、便利そう
	 * predicate 【自動】断言する 【他動】〔行動や議論などの〕基礎を置く(alc.co.jpより)
	 */
	@Test
	public void asPredicate(){

		Predicate<String> predicate = Pattern.compile("ABC").asPredicate();
		assertThat(predicate.test("ABC"), is(true));
		assertThat(predicate.test("AABC"), is(true));
		assertThat(predicate.test("ABCA"), is(true));
		assertThat(predicate.test("ABD"), is(false));
	}

	/**
	 * 正規表現の基礎(元ネタ:http://java-reference.sakuraweb.com/java_string_regex.html)
	 */
	@Test
	public void pattern(){

		Pattern pattern;

		// 「^」先頭にマッチする
		pattern = Pattern.compile("^ab");
		assertThat(pattern.matcher("abcd").find(), is(true));
		assertThat(pattern.matcher("cdab").find(), is(false));

		//$: 末尾にマッチする
		pattern = Pattern.compile("ab$");
		assertThat(pattern.matcher("cdab").find(), is(true));
		assertThat(pattern.matcher("abcd").find(), is(false));

		//.: 任意の１文字にマッチする
		pattern = Pattern.compile("a.c");
		assertThat(pattern.matcher("abc").find(), is(true));
		assertThat(pattern.matcher("abbc").find(), is(false));
		assertThat(pattern.matcher("ac").find(), is(false));

		//?: 直前の文字が0個または1個にマッチする
		pattern = Pattern.compile("^A?$");
		assertThat(pattern.matcher("").find(), is(true));
		assertThat(pattern.matcher("A").find(), is(true));
		assertThat(pattern.matcher("AA").find(), is(false));
		assertThat(pattern.matcher("B").find(), is(false));

		//*: 直前の文字が0個以上にマッチする
		pattern = Pattern.compile("^A*$");
		assertThat(pattern.matcher("").find(), is(true));
		assertThat(pattern.matcher("A").find(), is(true));
		assertThat(pattern.matcher("AA").find(), is(true));
		assertThat(pattern.matcher("B").find(), is(false));

		//+: 直前の文字が1個以上にマッチする
		pattern = Pattern.compile("^A+$");
		assertThat(pattern.matcher("A").find(), is(true));
		assertThat(pattern.matcher("AA").find(), is(true));
		assertThat(pattern.matcher("B").find(), is(false));
		assertThat(pattern.matcher("").find(), is(false));

		//\d: 半角数値(0～9)にマッチする ※ [0-9]と同じ
		pattern = Pattern.compile("\\d");
		assertThat(pattern.matcher("0123").find(), is(true));
		assertThat(pattern.matcher("０１２３").find(), is(false));
		assertThat(pattern.matcher("abc").find(), is(false));

		//\D: 半角数値(0～9)以外にマッチする ※ [^0-9]と同じ
		pattern = Pattern.compile("\\D");
		assertThat(pattern.matcher("０１２３").find(), is(true));
		assertThat(pattern.matcher("abc").find(), is(true));
		assertThat(pattern.matcher("0123").find(), is(false));

		//\w: 半角英数値(0～9、a～z、A～Z、_)にマッチする ※ [0-9a-zA-Z_]と同じ
		pattern = Pattern.compile("\\w");
		assertThat(pattern.matcher("012_AbC").find(), is(true));
		assertThat(pattern.matcher("１２Ａｂ").find(), is(false));
		assertThat(pattern.matcher("#$%&").find(), is(false));

		// \W: 半角英数値(0～9、a～z、A～Z、_)以外にマッチする
		pattern = Pattern.compile("\\W");
		assertThat(pattern.matcher("１２Ａｂ").find(), is(true));
		assertThat(pattern.matcher("#$%&").find(), is(true));
		assertThat(pattern.matcher("012_AbC").find(), is(false));

		//[ ]:いずれかの文字とマッチする
		pattern = Pattern.compile("[ABC]");
		assertThat(pattern.matcher("A").find(), is(true));
		assertThat(pattern.matcher("B").find(), is(true));
		assertThat(pattern.matcher("D").find(), is(false));

		//( ):１つのグループとして扱う
		pattern = Pattern.compile("(ABC)");
		assertThat(pattern.matcher("ABC").find(), is(true));
		assertThat(pattern.matcher("CBA").find(), is(false));

		//{n}:直前の文字にn回マッチする
		pattern = Pattern.compile("^A{3}$");
		assertThat(pattern.matcher("AAA").find(), is(true));
		assertThat(pattern.matcher("AA").find(), is(false));
		assertThat(pattern.matcher("AAAA").find(), is(false));

		//{n,}:直前の文字にn回以上マッチする
		pattern = Pattern.compile("^A{3,}$");
		assertThat(pattern.matcher("AAA").find(), is(true));
		assertThat(pattern.matcher("AAAA").find(), is(true));
		assertThat(pattern.matcher("AA").find(), is(false));

		//{n,m}:直前の文字にn回以上m回以下マッチする
		pattern = Pattern.compile("^A{3,4}$");
		assertThat(pattern.matcher("AAA").find(), is(true));
		assertThat(pattern.matcher("AAAA").find(), is(true));
		assertThat(pattern.matcher("AA").find(), is(false));
		assertThat(pattern.matcher("AAAAA").find(), is(false));

		//|:いずれかとマッチする
		pattern = Pattern.compile("ABC|DEF");
		assertThat(pattern.matcher("ABC").find(), is(true));
		assertThat(pattern.matcher("DEF").find(), is(true));
		assertThat(pattern.matcher("CBA").find(), is(false));
		assertThat(pattern.matcher("DE").find(), is(false));

		//-:範囲を指定する
		pattern = Pattern.compile("[3-7]");
		assertThat(pattern.matcher("3").find(), is(true));
		assertThat(pattern.matcher("5").find(), is(true));
		assertThat(pattern.matcher("7").find(), is(true));
		assertThat(pattern.matcher("7.1").find(), is(true));
		assertThat(pattern.matcher("55").find(), is(true));
		assertThat(pattern.matcher("2").find(), is(false));
		assertThat(pattern.matcher("1").find(), is(false));

		//^:否定（[ ]の中で使用する）
		pattern = Pattern.compile("[^AB]");
		assertThat(pattern.matcher("C").find(), is(true));
		assertThat(pattern.matcher("ABC").find(), is(true));
		assertThat(pattern.matcher("A").find(), is(false));
		assertThat(pattern.matcher("B").find(), is(false));

		//&&:かつ
		pattern = Pattern.compile("[0-9&&[^4]]");
		assertThat(pattern.matcher("0").find(), is(true));
		assertThat(pattern.matcher("5").find(), is(true));
		assertThat(pattern.matcher("4").find(), is(false));

	}

}
