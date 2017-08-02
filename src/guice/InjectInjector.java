package guice;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class InjectInjector {

	@Inject
	private Injector injector;

	@Test
	public void injectInjector(){
		Injector target = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
            }
        });

		InjectInjector mock1 = target.getInstance(InjectInjector.class);
		assertThat(mock1.injector, is(target));

		InjectInjector mock2 = new InjectInjector();
		assertThat(mock2.injector, is(nullValue()));
		target.injectMembers(mock2);
		assertThat(mock2.injector, is(target));
	}
}
