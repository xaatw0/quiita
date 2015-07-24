import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class 例外のテスト {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void 例外が発生する() throws IOException {

		exception.expect(IOException.class);
		exception.expectMessage("ファイルないよー");

		throw new IOException("ファイルないよー");
	}

	@Test
	public void 例外が発生しない(){
		assertThat(1, is(1));
	}
}
