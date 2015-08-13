package streamtest;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Test;

public class OutputStreamMethodTest {

	@Test
	public void write_1データ() throws IOException {

		OutputStreamMethod target = new OutputStreamMethod();
		target.put("北海道", "北海道");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		target.write(out);

		assertThat(new String(out.toByteArray()), is("北海道:北海道"));
	}

	@Test
	public void write_1行() throws IOException {

		OutputStreamMethod target = new OutputStreamMethod();
		target.put("青森県", "東北");
		target.put("岩手県", "東北");
		target.put("秋田県", "東北");

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		target.write(out);

		assertThat(new String(out.toByteArray()), is("東北:岩手県,秋田県,青森県"));
	}

	@Test
	public void write_3行() throws IOException {

		OutputStreamMethod target = new OutputStreamMethod();
		target.put("北海道", "北海道");
		target.put("青森県", "東北");
		target.put("岩手県", "東北");
		target.put("秋田県", "東北");
		target.put("茨城県", "関東");
		target.put("栃木県", "関東");
		target.put("群馬県", "関東");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		target.write(out);

		String[] expected = {
				"北海道:北海道"
				,"東北:岩手県,秋田県,青森県"
				,"関東:栃木県,群馬県,茨城県"
				};

		assertThat(new String(out.toByteArray()), is(String.join(System.lineSeparator(), expected)));
	}

}
