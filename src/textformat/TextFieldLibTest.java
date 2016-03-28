package textformat;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import org.junit.Test;

public class TextFieldLibTest {

	@Test
	public void number() {
		StringProperty number = new SimpleStringProperty();
		TextFieldLib.setNumberFormat(number);

		number.set("a");
		assertThat(number.get(), is(""));


		number.set("1");
		assertThat(number.get(), is("1"));

		number.set("1a");
		assertThat(number.get(), is("1"));
	}

}
