package streamtest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

public class InputStreamMethodTest {

	@Test
	public void read1行(){

		// 一行お試し。メモリー上にストリームを作成して、テストを実施する
		ByteArrayInputStream stream = new ByteArrayInputStream("東北:青森県,岩手県,秋田県,宮城県,山形県,福島県".getBytes());

		InputStreamMethod target = new InputStreamMethod();
		Map<String,String> result = target.read(stream);

		assertThat(result.get("青森県"), is("東北"));
		assertThat(result.get("秋田県"), is("東北"));
		assertThat(result.get("福島県"), is("東北"));
	}

	@Test
	public void read3行(){
		String SEP = System.getProperty("line.separator");

		StringBuffer sb = new StringBuffer();
		sb.append("北海道:北海道").append(SEP);
		sb.append("東北:青森県,岩手県,秋田県,宮城県,山形県,福島県").append(SEP);
		sb.append("関東:茨城県,栃木県,群馬県,埼玉県,千葉県,東京都,神奈川県").append(SEP);

		ByteArrayInputStream stream = new ByteArrayInputStream(sb.toString().getBytes());

		InputStreamMethod target = new InputStreamMethod();
		Map<String,String> result = target.read(stream);

		assertThat(result.get("北海道"), is("北海道"));
		assertThat(result.get("青森県"), is("東北"));
		assertThat(result.get("神奈川県"), is("関東"));
	}

	@Test
	public void read() {

		InputStreamMethod target = new InputStreamMethod();
		Map<String,String> result = target.read(getClass().getResourceAsStream("InputStreamMethodTest.txt"));

		assertThat(result.get("北海道"), is("北海道"));
		assertThat(result.get("青森県"), is("東北"));
		assertThat(result.get("秋田県"), is("東北"));
		assertThat(result.get("福島県"), is("東北"));
		assertThat(result.get("神奈川県"), is("関東"));
		assertThat(result.get("鳥取県"), is("中国"));
		assertThat(result.get("徳島県"), is("四国"));
		assertThat(result.get("沖縄県"), is("九州"));

		assertThat(result.get("うどん県"), is(nullValue()));

	}


	/**
	 * ファイルフォーマットがおかしかった
	 */
	@Test
	public void readファイルエラー(){

		ByteArrayInputStream stream = new ByteArrayInputStream("東北青森県,岩手県,秋田県,宮城県,山形県,福島県".getBytes());

		InputStreamMethod target = new InputStreamMethod();
		assertThat(target.read(stream), is(nullValue()));
	}

	/**
	 * InputStreamMethod.readの内部でByteArrayInputStream streamを使用したInputStreamReaderをtryで囲んでいるが、この場合closeが呼ばれているか気になったのでテスト。呼ばれているようだ。
	 */
	@Test
	public void close(){

		final StringBuilder sb = new StringBuilder();

		ByteArrayInputStream stream =
				new ByteArrayInputStream("東北:青森県,岩手県,秋田県,宮城県,山形県,福島県".getBytes())
				{
					@Override
					public void close() throws IOException{
						sb.append("stream was closed");
						super.close();
					}
				};

		InputStreamMethod target = new InputStreamMethod();
		Map<String,String> result = target.read(stream);

		assertThat(sb.toString(), is("stream was closed"));
	}
}
