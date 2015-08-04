package streamtest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;


/**
 Javaのアプリケーションをリリースするにあたり、jarファイルでまとめて、バッチファイルをクリックして実施できるようにしたい。
 そのとき、設定ファイルはユーザでも編集できるようにjarファイルではなく、バッチファイルのある場所に置きたい。
 開発するときは設定ファイルの読み込みクラスのテストもしたい。
 その方法を検討した。


 */
public class InputStreamTest {

	@Test
	public void inputstream() throws IOException {

		InputStream target = getClass().getResourceAsStream("InputStreamのテスト.txt");

		// InputStreamをReaderに変更
		BufferedReader reader = new BufferedReader(new InputStreamReader(target));

		assertThat(reader.readLine(), is("ああああ:いいいい"));
		assertThat(reader.readLine(), is("ううう:えええ"));
		assertThat(reader.readLine(), is(nullValue()));

		reader.close();
	}

	@Test
	public void file2InputStream() throws IOException{

		// C:\Users\[ユーザ名]\git\quiita\src\streamtest\InputStreamのテスト.txt
		// ルートは、ワークスペース
		File file = new File("./src/streamtest/InputStreamのテスト.txt");
		InputStream inputStream = new FileInputStream(file);

		// InputStreamをReaderに変更
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		assertThat(reader.readLine(), is("ああああ:いいいい"));
		assertThat(reader.readLine(), is("ううう:えええ"));
		assertThat(reader.readLine(), is(nullValue()));

		reader.close();
	}

	@Test
	public void byteArrayInputStream1行() throws IOException{

		ByteArrayInputStream stream = new ByteArrayInputStream("test".getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		assertThat(reader.readLine(), is("test"));
	}

	@Test
	public void byteArrayInputStream3行() throws IOException{

		String SEP = System.getProperty("line.separator");

		StringBuffer sb = new StringBuffer();
		sb.append("AAA").append(SEP);
		sb.append("BBB").append(SEP);
		sb.append("CCC").append(SEP);

		ByteArrayInputStream stream = new ByteArrayInputStream(sb.toString().getBytes());

		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		assertThat(reader.readLine(), is("AAA"));
		assertThat(reader.readLine(), is("BBB"));
		assertThat(reader.readLine(), is("CCC"));
		assertThat(reader.readLine(), is(nullValue()));
	}

	public static void main(String[] arg) throws IOException{

		// 設定ファイル
		File envFile = null;

		if (arg.length ==0){
			// 実稼働(jarから呼び出すケース)
			File jarDir = new File(System.getProperty("java.class.path")).getParentFile();
			envFile = new File(jarDir, "env.ini");

		} else if (arg.length == 1){
			// テスト環境
			envFile = new File(arg[0]);

		} else {
			System.err.println("java -jar test.jar");
			System.err.println("java InputStreamTest env.ini ");
			System.exit(-1);
		}
	}
}
