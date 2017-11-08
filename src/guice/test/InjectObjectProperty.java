package guice.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class InjectObjectProperty {

	@Inject
	private ObjectProperty<LocalDate> localDateProperty;

	@Inject
	private ObjectProperty<BigDecimal> bigDecimalProperty;

	@Test
	public void test(){

		ObjectProperty<LocalDate> localDate = new SimpleObjectProperty<>();
		ObjectProperty<BigDecimal> bigDecimal = new SimpleObjectProperty<>();

		Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            	bind(new TypeLiteral<ObjectProperty<LocalDate>>(){}).toInstance(localDate);
            	bind(new TypeLiteral<ObjectProperty<BigDecimal>>(){}).toInstance(bigDecimal);
            }
        });

		InjectObjectProperty target = injector.getInstance(InjectObjectProperty.class);
		assertThat(target.localDateProperty.get(),is(nullValue()));
		assertThat(target.bigDecimalProperty.get(),is(nullValue()));

		localDate.set(LocalDate.of(2017, 1, 1));
		bigDecimal.set(BigDecimal.ONE);
		assertThat(target.localDateProperty.get(),is(LocalDate.of(2017, 1, 1)));
		assertThat(target.bigDecimalProperty.get(),is(BigDecimal.ONE));
	}

}
