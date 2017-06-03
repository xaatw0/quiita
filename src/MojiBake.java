import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class MojiBake {

	@Test
	public void utf8(){

		byte[] bytes = "あ".getBytes();
		assertThat(bytes.length, is(3));
		assertThat(bytes[0], is((byte)-29));
		assertThat(bytes[1], is((byte)-127));
		assertThat(bytes[2], is((byte)-126));
	}

}
