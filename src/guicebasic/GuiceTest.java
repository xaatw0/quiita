package guicebasic;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class GuiceTest {

	@Inject
    private EnglishSpeaker englishSpeaker;

	@Test
    public void EnglishSpeakerTest(){
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override protected void configure() {}
        });

        assertThat(injector.getInstance(GuiceTest.class).englishSpeaker.hello(), is("Hello Google Guice!!"));
    }

	@Test
    public void FrankEnglishSpeakerTest(){
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override protected void configure() {
            	bind(EnglishSpeaker.class).to(FrankEnglishSpeaker.class);
            }
        });

        assertThat(injector.getInstance(GuiceTest.class).englishSpeaker.hello(), is("Hi, guys"));
    }

}
