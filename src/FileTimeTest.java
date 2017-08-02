import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.Test;


public class FileTimeTest {

	@Test
	public void now() throws IOException{
		Path tempFile = Files.createTempFile(null, null);
		Instant time = Files.getLastModifiedTime(tempFile).toInstant();

		Instant time2 = ZonedDateTime.now().toInstant();

		Instant midnight =ZonedDateTime.of(LocalDate.now().atStartOfDay(), ZoneId.systemDefault()).toInstant();

		assertThat(time.isAfter(midnight), is(true));
	}

}
