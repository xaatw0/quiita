package fxfxml;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;

import org.junit.Before;
import org.junit.Test;

public class FXMLBeanTest {

	private FXMLBean target;

	@Before
	public void tearUp() throws IOException {
		target = FXMLLoader.load(FXMLBean.class.getResource("FXMLBean.fxml"));
	}

	@Test
	public void FXMLBean() {
		assertThat(target.getName(), is("John Smith"));
		assertThat(target.getAddress(), is("12345 Main St."));
		assertThat(target.isFlag(), is(true));
		assertThat(target.getCount(), is(12345));

		assertThat(target.getForeground(), is(Color.RED));
		assertThat(target.getBackground(), is(new Color(0.0,0.5,1.0,1.0)));

		assertThat(target.getPrice(), is(3.1415));
		assertThat(target.getPrice2(), is(123.45));

		assertThat(target.getDiscounts(),is(Double.valueOf(0.1d)));


		List<Integer> sizes = target.getSizes();
		assertThat(sizes.size(), is(3));
		assertThat(sizes.get(0), is(1));
		assertThat(sizes.get(1), is(2));
		assertThat(sizes.get(2), is(3));

		assertThat(target.getInventory(), is(9765625L));

		Map<String,String> abbreviations = target.getAbbreviation();
		assertThat(abbreviations.get("CA"), is("California"));
		assertThat(abbreviations.get("NY"), is("New York"));

		Map<String, String> profits = target.getProfits();
		assertThat(profits.size(), is(3));
		assertThat(profits.get("q1") ,is("1000"));

	}

}
