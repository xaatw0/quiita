package fxbind;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;

import org.junit.Ignore;
import org.junit.Test;

public class StringPropertyTest {

	private class Bean{

		StringProperty firstName;

		public StringProperty firstNameProperty(){

			if (firstName == null){
				firstName = new SimpleStringProperty("testName");
			}
			return firstName;
		}

		public String getFirstName(){return firstNameProperty().get();}
		public void setFirstName(String name){ firstNameProperty().set(name);}
	}



	@Test @Ignore
	public void beanName() throws NoSuchMethodException{

		StringProperty target = new SimpleStringProperty();
		assertThat(target.get(), is(nullValue()));

		target = new SimpleStringProperty("test");
		assertThat(target.get(), is("test"));

		Bean bean = new Bean();
		assertThat(bean.firstNameProperty().get(), is("testName"));

		target = new SimpleStringProperty(bean, "name");
		//assertThat(target.toString(), is("StringProperty [bean: fxbind.StringPropertyTest$Bean@32c033, name: name, value: null]"));
		assertThat(target.get(), is(not("testName")));

		target = JavaBeanStringPropertyBuilder.create().bean(bean)
				.name("name")
				.getter("getFirstName")
				.build();
		assertThat(target.get(), is("testName"));

	}

}
