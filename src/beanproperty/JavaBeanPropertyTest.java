package beanproperty;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

import org.junit.Test;

public class JavaBeanPropertyTest {

	@Test
	public void JavaBeanStringProperty() throws NoSuchMethodException{

		SampleBean bean = new SampleBean();

		StringProperty property =
				JavaBeanStringPropertyBuilder.create()
				.bean(bean)
				.name("name")
				.build();


		StringProperty bindProperty = new SimpleStringProperty();
		bindProperty.bindBidirectional(property);

		assertThat(bean.getName(), is(nullValue()));
		assertThat(property.get(), is(nullValue()));
		assertThat(bindProperty.get(), is(nullValue()));

		property.set("test");
		assertThat(bean.getName(), is("test"));
		assertThat(property.get(), is("test"));
		assertThat(bindProperty.get(), is("test"));
	}

	@Test
	public void JavaBeanObjectProperty() throws NoSuchMethodException{

		SampleBean bean = new SampleBean();

		ObjectProperty<LocalDate> property =
				JavaBeanObjectPropertyBuilder.create()
				.bean(bean)
				.name("birthday")
				.build();

		ObjectProperty<LocalDate> bindProperty = new SimpleObjectProperty<LocalDate>();
		bindProperty.bindBidirectional(property);

		assertThat(bean.getName(), is(nullValue()));
		assertThat(property.get(), is(nullValue()));
		assertThat(bindProperty.get(), is(nullValue()));

		LocalDate day = LocalDate.of(2000, 1, 1);
		property.set(day);
		assertThat(bean.getBirthday(), is(day));
		assertThat(property.get(), is(day));
		assertThat(bindProperty.get(), is(day));
	}

	@Test(expected=NoSuchMethodException.class)
	public void JavaBeanObjectProperty_error() throws NoSuchMethodException{

		SampleBean bean = new SampleBean();

		ObjectProperty<LocalDate> property =
				JavaBeanObjectPropertyBuilder.create()
				.bean(bean)
				.name("birthDay") // Dが大文字
				.build();
	}

}
