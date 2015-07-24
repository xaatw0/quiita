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


public class InputStreamのテスト {

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

		// C:\Users\[ユーザ名]\git\quiita\src\InputStreamのテスト.txt
		File file = new File("./src/InputStreamのテスト.txt");
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
	public void byteArrayInputStream5行() throws IOException{

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

}
