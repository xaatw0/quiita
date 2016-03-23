package beanproperty;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
	public void JavaBeanObjectProperty_途中が大文字() throws NoSuchMethodException{

		SampleBean bean = new SampleBean();

		ObjectProperty<LocalDate> property =
				JavaBeanObjectPropertyBuilder.create()
				.bean(bean)
				.name("birthDay") // Dが大文字
				.build();
	}

	@Test
	public void JavaBeanObjectProperty_先頭が大文字は大丈夫() throws NoSuchMethodException{

		SampleBean bean = new SampleBean();

		ObjectProperty<LocalDate> property =
				JavaBeanObjectPropertyBuilder.create()
				.bean(bean)
				.name("Birthday") // 先頭が大文字
				.build();
	}

	private String result1;
	private String result2;

	@Test
	public void addListener() throws NoSuchMethodException{

		SampleBean bean = new SampleBean();

		StringProperty property =
				JavaBeanStringPropertyBuilder.create()
				.bean(bean)
				.name("name")
				.build();

		property.addListener((o,ov,nv)-> { result1 = "property:" + nv;});

		StringProperty bindProperty = new SimpleStringProperty();
		bindProperty.addListener((o,ov,nv)-> { result2 = "bindProperty:" + nv;});

		bindProperty.bindBidirectional(property);

		BooleanProperty isEquals = new SimpleBooleanProperty();
		isEquals.bind(property.isEqualTo("1").or(property.isEqualTo("2")));

		assertThat(result1, is(nullValue()));
		assertThat(result2, is(nullValue()));

		// Beanを設定。バインドとリスナーは動かない。
		bean.setName("1");
		assertThat(bean.getName(), is("1"));
		assertThat(property.get(), is("1"));
		assertThat(bindProperty.get(), is(nullValue()));
		assertThat(result1, is(nullValue()));
		assertThat(result2, is(nullValue()));
		assertThat(isEquals.get(), is(false));
		assertThat(property.isEqualTo("1").get(), is(true));

		// プロパティを設定
		property.set("2");
		assertThat(bean.getName(), is("2"));
		assertThat(property.get(), is("2"));
		assertThat(bindProperty.get(), is("2"));
		assertThat(result1, is("property:2"));
		assertThat(result2, is("bindProperty:2"));
		assertThat(isEquals.get(), is(true));

		// バインドしたプロパティを設定
		bindProperty.set("3");
		assertThat(bean.getName(), is("3"));
		assertThat(property.get(), is("3"));
		assertThat(bindProperty.get(), is("3"));
		assertThat(result1, is("property:3"));
		assertThat(result2, is("bindProperty:3"));
		assertThat(isEquals.get(), is(false));

	}
}
