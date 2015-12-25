import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * (元ネタ)<br/>
 *  http://java-reference.sakuraweb.com/java_string_regex.html<br/>
 *  http://www.ne.jp/asahi/hishidama/home/tech/java/regexp.html<br/>
 */
public class Javaの正規表現 {

	/**
	 * matches()は、文字列全体がパターンにマッチしているか<br/>
	 * find()は、文字列の一部がパターンにマッチしているか
	 */
	@Test
	public void matchsfind(){

		/***   ABC     ***/
		Pattern pattern = Pattern.compile("ABC");

		Matcher matcher = pattern.matcher("ABC");
		assertThat(matcher.matches(), is(true));

		matcher = pattern.matcher("ABC");
		assertThat(matcher.find(), is(true));

		// AABC
		matcher = pattern.matcher("AABC");
		assertThat(matcher.matches(), is(false));

		matcher = pattern.matcher("AABC");
		assertThat(matcher.find(), is(true));

		// ABCA
		matcher = pattern.matcher("ABCA");
		assertThat(matcher.matches(), is(false));

		matcher = pattern.matcher("ABCA");
		assertThat(matcher.find(), is(true));

		// ABD
		matcher = pattern.matcher("ABD");
		assertThat(matcher.matches(), is(false));

		matcher = pattern.matcher("ABD");
		assertThat(matcher.find(), is(false));

		/***   ^ABC$     ***/
		pattern = Pattern.compile("^ABC$");

		// ABC
		matcher = pattern.matcher("ABC");
		assertThat(matcher.matches(), is(true));

		matcher = pattern.matcher("ABC");
		assertThat(matcher.find(), is(true));

		// ABCD
		matcher = pattern.matcher("ABCD");
		assertThat(matcher.matches(), is(false));

		matcher = pattern.matcher("ABCD");
		assertThat(matcher.find(), is(false));
	}

	/**
	 * 正規表現の基礎(元ネタ:http://java-reference.sakuraweb.com/java_string_regex.html)
	 */
	@Test
	public void 任意の文字と繰り返し(){

		Pattern pattern;

		//.: 任意の１文字にマッチする
		pattern = Pattern.compile("a.c");
		assertThat(pattern.matcher("abc").matches(), is(true));
		assertThat(pattern.matcher("abbc").matches(), is(false));
		assertThat(pattern.matcher("ac").matches(), is(false));

		//?: 直前の文字が0個または1個にマッチする
		pattern = Pattern.compile("^A?");
		assertThat(pattern.matcher("").matches(), is(true));
		assertThat(pattern.matcher("A").matches(), is(true));
		assertThat(pattern.matcher("AA").matches(), is(false));
		assertThat(pattern.matcher("B").matches(), is(false));

		//*: 直前の文字が0個以上にマッチする
		pattern = Pattern.compile("^A*");
		assertThat(pattern.matcher("").matches(), is(true));
		assertThat(pattern.matcher("A").matches(), is(true));
		assertThat(pattern.matcher("AA").matches(), is(true));
		assertThat(pattern.matcher("B").matches(), is(false));

		//+: 直前の文字が1個以上にマッチする
		pattern = Pattern.compile("^A+");
		assertThat(pattern.matcher("A").matches(), is(true));
		assertThat(pattern.matcher("AA").matches(), is(true));
		assertThat(pattern.matcher("B").matches(), is(false));
		assertThat(pattern.matcher("").matches(), is(false));

		//{n}:直前の文字にn回マッチする
		pattern = Pattern.compile("^A{3}");
		assertThat(pattern.matcher("AAA").matches(), is(true));
		assertThat(pattern.matcher("AA").matches(), is(false));
		assertThat(pattern.matcher("AAAA").matches(), is(false));

		//{n,}:直前の文字にn回以上マッチする
		pattern = Pattern.compile("^A{3,}");
		assertThat(pattern.matcher("AAA").matches(), is(true));
		assertThat(pattern.matcher("AAAA").matches(), is(true));
		assertThat(pattern.matcher("AA").matches(), is(false));

		//{n,m}:直前の文字にn回以上m回以下マッチする
		pattern = Pattern.compile("^A{3,4}");
		assertThat(pattern.matcher("AAA").matches(), is(true));
		assertThat(pattern.matcher("AAAA").matches(), is(true));
		assertThat(pattern.matcher("AA").matches(), is(false));
		assertThat(pattern.matcher("AAAAA").matches(), is(false));
	}

	@Test
	public void 最初と最後(){

		Pattern pattern;

		// 「^」先頭にマッチする
		pattern = Pattern.compile("^ab");
		assertThat(pattern.matcher("abcd").find(), is(true));
		assertThat(pattern.matcher("cdab").find(), is(false));

		//$: 末尾にマッチする
		pattern = Pattern.compile("ab$");
		assertThat(pattern.matcher("cdab").find(), is(true));
		assertThat(pattern.matcher("abcd").find(), is(false));
	}

	@Test
	public void 定義済みの文字クラス(){

		Pattern pattern;

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

		// \s: 空白文字:[ \t\n\x0B\f\r]
		pattern = Pattern.compile("\\s");
		assertThat(pattern.matcher(" ").find(), is(true));
		assertThat(pattern.matcher("A C").find(), is(true));
		assertThat(pattern.matcher("").find(), is(false));
		assertThat(pattern.matcher("ABC").find(), is(false));
	}

	@Test
	public void グループと組み合わせ(){

		Pattern pattern;
		//[ ]:いずれかの文字とマッチする
		pattern = Pattern.compile("[ABC]");
		assertThat(pattern.matcher("A").find(), is(true));
		assertThat(pattern.matcher("B").find(), is(true));
		assertThat(pattern.matcher("D").find(), is(false));

		//( ):１つのグループとして扱う
		pattern = Pattern.compile("(ABC)");
		assertThat(pattern.matcher("ABC").find(), is(true));
		assertThat(pattern.matcher("CBA").find(), is(false));

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

	@Test
	public void group(){

		//                                          012345678901234
		Matcher m = Pattern.compile("1.3").matcher("abc123def1B3ghi");

		// 通常はwhile(m.find)で実施する
		assertThat(m.find(), is(true));
		assertThat(m.group(),is("123"));
		assertThat(m.group(0),is("123"));
		assertThat(m.start(), is(3));
		assertThat(m.end(), is(6));

		assertThat(m.find(), is(true));
		assertThat(m.group(),is("1B3"));
		assertThat(m.group(0),is("1B3"));
		assertThat(m.start(), is(9));
		assertThat(m.end(), is(12));

		assertThat(m.find(), is(false));
	}

	@Test
	public void group複数(){

		//郵便番号の正規表現
		Pattern pattern = Pattern.compile("(\\d{3})-(\\d{4})");

		Matcher matcher = pattern.matcher("123-4567");
		assertThat(matcher.matches(), is(true));

		assertThat(matcher.groupCount(), is(2));
		assertThat(matcher.group(0), is("123-4567"));
		assertThat(matcher.group(1), is("123"));
		assertThat(matcher.group(2), is("4567"));


		matcher = pattern.matcher("123-4567");
		assertThat(matcher.find(), is(true));
	}

	@Test
	public void 時刻チェック(){

		Pattern pattern = Pattern.compile("([0-2]?[0-9]):([0-9]{2})");

		Matcher matcher = pattern.matcher("12:00");

		if (matcher.find()){
			int test= 100 * Integer.parseInt(matcher.group(1)) + Integer.parseInt(matcher.group(2));
			assertThat(test, is(1200));
		}

		assertThat(pattern.matcher("1:30").matches(), is(true));
		assertThat(pattern.matcher("01:30").matches(), is(true));
		assertThat(pattern.matcher("1:00").matches(), is(true));
		assertThat(pattern.matcher("25:45").matches(), is(true));

		assertThat(pattern.matcher("0145").matches(), is(false));
		assertThat(pattern.matcher("A1:45").matches(), is(false));
		assertThat(pattern.matcher(":45").matches(), is(false));
		assertThat(pattern.matcher("01:").matches(), is(false));
		assertThat(pattern.matcher("01:1").matches(), is(false));
		assertThat(pattern.matcher("01:123").matches(), is(false));

	}

	@Test
	public void カッコ(){

		// 「交代(氏名)」にヒットして、氏名のみを取得したい
		//  正規表現:交代\(([^)]*)\)
		//               \(       \): カッコをそのまま使うので、エスケープ処理に\を付けている
		//                            正規表現とJavaのエスケープ処理が必要なので、ソースでは「\\(」となる
		//                 (     )  : グループの()
		//                  [^)]*   :「)」以外の文字の連続。「)」の前までの文字をグループ化する
		//                            []内のカッコにはエスケープ処理が不要らしい、、、

		Pattern ptnRelacement = Pattern.compile("交代\\(([^)]*)\\)");

		Matcher matcher = ptnRelacement.matcher("交代(あいう)");
		assertThat(matcher.matches(), is(true));
		assertThat(matcher.group(), is("交代(あいう)"));
		assertThat(matcher.group(1), is("あいう"));

		assertThat(ptnRelacement.matcher("交代(あいう)").matches(), is(true));
		assertThat(ptnRelacement.matcher("交代あいう)").matches(), is(false));
		assertThat(ptnRelacement.matcher("交代(あいう").matches(), is(false));


		// ちょっとテスト
		ptnRelacement = Pattern.compile("交代\\(([^\\)]*)\\)");
		matcher = ptnRelacement.matcher("交代(あい\\う)");
		assertThat(matcher.matches(), is(true));
		assertThat(matcher.group(), is("交代(あい\\う)"));
		assertThat(matcher.group(1), is("あい\\う"));

		ptnRelacement = Pattern.compile("交代\\(([^\\\\)]*)\\)");
		assertThat(ptnRelacement.matcher("交代(あいう)").matches(), is(true));
		assertThat(ptnRelacement.matcher("交代(あい\\う)").matches(), is(false));
		assertThat(ptnRelacement.matcher("交代())").matches(), is(false));

	}

	@Test
	public void 天気表記(){
		Pattern ptnWeather = Pattern.compile("[晴曇雨雪](/[晴曇雨雪])?");

		assertThat(ptnWeather.matcher("晴").matches(), is(true));
		assertThat(ptnWeather.matcher("晴後").matches(), is(false));
		assertThat(ptnWeather.matcher("晴/雨").matches(), is(true));
		assertThat(ptnWeather.matcher("晴/雨雨").matches(), is(false));
		assertThat(ptnWeather.matcher("晴雨").matches(), is(false));
	}

	@Test
	public void 日本語テキストへのマッチ(){

		Pattern pattern;

		// ひらがな
		pattern = Pattern.compile("[\u3040-\u309F]");
		assertThat(pattern.matcher("あ").matches(), is(true));
		assertThat(pattern.matcher("ん").matches(), is(true));
		assertThat(pattern.matcher("ば").matches(), is(true));

		assertThat(pattern.matcher("A").matches(), is(false));
		assertThat(pattern.matcher("ア").matches(), is(false));
		assertThat(pattern.matcher("ｱ").matches(), is(false));

		// 全角カタカナ
		pattern = Pattern.compile("[\u30A0-\u30FF]");
		assertThat(pattern.matcher("ア").matches(), is(true));
		assertThat(pattern.matcher("ン").matches(), is(true));
		assertThat(pattern.matcher("ヴ").matches(), is(true));

		assertThat(pattern.matcher("A").matches(), is(false));
		assertThat(pattern.matcher("あ").matches(), is(false));
		assertThat(pattern.matcher("ｱ").matches(), is(false));

		// 半角カタカナ
		pattern = Pattern.compile("[\uFF61-\uFF9F]");
		assertThat(pattern.matcher("ｱ").matches(), is(true));
		assertThat(pattern.matcher("ﾝ").matches(), is(true));
		assertThat(pattern.matcher("ﾞ").matches(), is(true)); // ｳﾞｧの点々

		assertThat(pattern.matcher("A").matches(), is(false));
		assertThat(pattern.matcher("あ").matches(), is(false));
		assertThat(pattern.matcher("ア").matches(), is(false));

		// 漢字
		pattern = Pattern.compile("[\u4E00-\u9FFF]");
		assertThat(pattern.matcher("亜").matches(), is(true));
		assertThat(pattern.matcher("両").matches(), is(true));
		assertThat(pattern.matcher("廣").matches(), is(true));

		assertThat(pattern.matcher("A").matches(), is(false));
		assertThat(pattern.matcher("あ").matches(), is(false));
		assertThat(pattern.matcher("ア").matches(), is(false));
		assertThat(pattern.matcher("ｱ").matches(), is(false));
	}

	/**
	 * ?: 最短一致数量子<br/>
	 * 元ネタ: http://www.javazuki.com/tag/正規表現
	 */
	@Test
	public void 欲張らない繰り返し(){

		// 通常の繰り返しではできる限りたくさんマッチさせようとする。
		// (いわゆる欲張りな繰り返し)
		Pattern pattern = Pattern.compile("<li>.*</li>");
		Matcher matcher = pattern.matcher("<li>baseball</li><li>soccer</li>");
		assertThat(matcher.matches(), is(true));
		assertThat(matcher.group(), is("<li>baseball</li><li>soccer</li>"));

		// 部分抽出など繰り返しの範囲を狭くしたい場合は
		// ｢?｣(最短一致数量子)を指定する。
		pattern = Pattern.compile("<li>.*?</li>");
		matcher = pattern.matcher("<li>baseball</li><li>soccer</li>");
		assertThat(matcher.find(), is(true));
		assertThat(matcher.group(), is("<li>baseball</li>"));
		assertThat(matcher.find(), is(true));
		assertThat(matcher.group(), is("<li>soccer</li>"));
	}

	@Test
	public void 置換(){
		// ファイル名の連番は維持して、ファイル名を変更する
		Pattern pattern = Pattern.compile("[^\\d.]+");
		Matcher matcher = pattern.matcher("IMG001.jpg");
		assertThat(matcher.find(), is(true));
		assertThat(matcher.group(), is("IMG"));
		assertThat(matcher.replaceFirst("写真"), is("写真001.jpg"));
		assertThat(matcher.replaceAll("写真"), is("写真001.写真"));
	}

	@Test
	public void 置換キャメル式スネーク式(){
		Pattern pattern = Pattern.compile("(^|_)(.)");
		Matcher matcher = pattern.matcher("this_is_a_pen");
		assertThat(matcher.replaceAll("\\u$2"), is("ThisIsAPen"));
	}

	/**
	 * 「メールアドレスの正規表現がめちゃめちゃ遅くなることがある件について」<br/>
	 * (http://d.hatena.ne.jp/n_shuyo/20111020/regular_expression)<br/>
	 * という記事があったので、テスト。この量では実感できず、、、<br/>
	 * 要約: 連続文字の表現に「+」を使うより、{1,64}と制限をつけた連続文字のほうが早い。<br/>
	 */
	@Test
	public void メールアドレスが遅い(){

		String[] mailAddresses = {
				"a234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"b23456789012345678912345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"c234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"d234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"e234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"f234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"};


		// ファイル名の連番は維持して、ファイル名を変更する
		Pattern pattern = Pattern.compile("[-_.0-9A-Za-z]+@[-_0-9A-Za-z]+[-_.0-9A-Za-z]+");

		for(String address: mailAddresses){
			assertThat(pattern.matcher(address).matches(), is(true));
		}
	}

	@Test
	public void メールアドレスが遅い2(){

		String[] mailAddresses = {
				"a234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"b23456789012345678912345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"c234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"d234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"e234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"
				,"f234567890123456789012345678901234567890123456789012345678901234@bbbbbbbbbbbbbb.com"};


		// ファイル名の連番は維持して、ファイル名を変更する
		Pattern pattern = Pattern.compile("[-_.0-9A-Za-z]{0,64}@[-_0-9A-Za-z]+[-_.0-9A-Za-z]+");

		for(String address: mailAddresses){
			assertThat(pattern.matcher(address).matches(), is(true));
		}
	}

	/**
	 * JDK 1.5以降<br/>
	 * MatchResult（JDK1.5以降）は独立したインスタンスとなっているので変化しない。<br/>
     *（ただしMatchResultはstart/end以外のデータもコピーして保持するので、start/endしか使わないならちょっとコストが高いかも）
	 */
	@Test
	public void matchResult(){

		//                                          012345678901234
		Matcher m = Pattern.compile("1.3").matcher("abc123def1B3ghi");

		assertThat(m.find(), is(true));
		MatchResult result1 = m.toMatchResult();
		assertThat(result1.group(),is("123"));
		assertThat(result1.group(0),is("123"));
		assertThat(result1.start(), is(3));
		assertThat(result1.end(), is(6));

		assertThat(m.toMatchResult(), is(not(sameInstance(result1))));
		assertThat(m.toMatchResult(), is(not(sameInstance(result1))));

		assertThat(m.find(), is(true));
		MatchResult result2 = m.toMatchResult();
		assertThat(result2.group(),is("1B3"));
		assertThat(result2.group(0),is("1B3"));
		assertThat(result2.start(), is(9));
		assertThat(result2.end(), is(12));

		assertThat(m.find(), is(false));
	}

	/**
	 * エスケープ処理が必要な文字:<br/>
	 * ¥ * + . ? { } ( ) [ ] ^ $ - |
	 */
	@Test
	public void メタ文字(){
		Pattern pattern = Pattern.compile("\\*");

		// こちらは実行エラー
		//pattern = Pattern.compile("*");

		Matcher matcher = pattern.matcher("*");
		assertThat(matcher.matches(), is(true));

		// 文字列内に「\」があるか判断する場合、
		// Javaソースのエスケープ処理も必要なため、「\\」のエスケープつきは「\\\\」となる
		// Javaソース内のエスケープ文字と、Java実行環境の正規表現でのエスケープ文字を考えないといけない
		// (嗚呼、ややこしい)
		pattern = Pattern.compile("\\\\");
		matcher = pattern.matcher("\\");
		assertThat(matcher.find(), is(true));
	}

	/**
	 * 参考: http://homepage2.nifty.com/jr-kun/hidemaru_qa/4_regulr.html
	 */
	@Test
	public void キャラクタクラス(){
		// 数字
		Pattern pattern = Pattern.compile("[0-9]");
		assertThat(pattern.matcher("0").matches(), is(true));
		assertThat(pattern.matcher("9").matches(), is(true));
		assertThat(pattern.matcher("A").matches(), is(false));

		// 全角ひらがな
		pattern = Pattern.compile("[ぁ-ん]");
		assertThat(pattern.matcher("あ").matches(), is(true));
		assertThat(pattern.matcher("ば").matches(), is(true));
		assertThat(pattern.matcher("ア").matches(), is(false));

		// 全角カタカナ
		pattern = Pattern.compile("[ァ-ヶ]");
		assertThat(pattern.matcher("ァ").matches(), is(true));
		assertThat(pattern.matcher("ア").matches(), is(true));
		assertThat(pattern.matcher("あ").matches(), is(false));

		// 任意の１バイト文字
		pattern = Pattern.compile("[ -~]");
		assertThat(pattern.matcher("0").matches(), is(true));
		assertThat(pattern.matcher("A").matches(), is(true));
		assertThat(pattern.matcher("ア").matches(), is(false));

		// 漢字
		pattern = Pattern.compile("[亜-黑]");
		assertThat(pattern.matcher("亜").matches(), is(true));
		assertThat(pattern.matcher("廣").matches(), is(true));
		assertThat(pattern.matcher("0").matches(), is(false));
		assertThat(pattern.matcher("A").matches(), is(false));
		assertThat(pattern.matcher("ア").matches(), is(false));
	}

	/**
	 * JDK 1.5以降<br/>
	 * (よくわからんが、)エスケープした文字列を返してくれる

	 */
	@Test
	public void quote(){
		assertThat(Pattern.quote("."), is("\\Q.\\E"));
		assertThat(Pattern.quote(","), is("\\Q,\\E"));
		assertThat(Pattern.quote("\\"), is("\\Q\\\\E"));

		assertThat("a.b.c".split(".").length, is(0));
		assertThat("a.b.c".split("\\.").length, is(3));
		assertThat("a.b.c".split(Pattern.quote(".")).length, is(3));
	}

	/**
	 * JDK 1.8以降
	 */
	@Test
	public void group名前を指定(){

		//電話番号の正規表現
		Pattern pattern = Pattern.compile("0(?<shigai>\\d{1,4})-(?<shinai>\\d+)-(?<bango>\\d{4})");

		Matcher matcher = pattern.matcher("0120-863-5730");
		assertThat(matcher.matches(), is(true));
		assertThat(matcher.groupCount(), is(3));

		assertThat(matcher.group(0), is("0120-863-5730"));
		assertThat(matcher.group(1), is("120"));
		assertThat(matcher.group(2), is("863"));
		assertThat(matcher.group(3), is("5730"));

		assertThat(matcher.group("shigai"), is("120"));
		assertThat(matcher.group("shinai"), is("863"));
		assertThat(matcher.group("bango"), is("5730"));

		// 日本語を付けると、実行エラー(コンパイルは通る)
		//Pattern pattern2 = Pattern.compile("0(?<市外>\\d{1,4})-(?<市内>\\d+)-(?<番号>\\d{4})");
	}

	/**
	 * JDK 1.8以降<br/>
	 * 文字列全体でも文字列の一部でもマッチして、便利そう<br/>
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
	 * JDK 1.8以降
	 */
	@Test
	public void splitAsStream(){

		Pattern pattern = Pattern.compile("-");
		Stream<String> stream = pattern.splitAsStream("0120-863-5730");
		assertThat(stream.count(), is(3L));
	}

}
