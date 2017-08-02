package taskservice;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;

public class StringTaskTest {

    @BeforeClass
    public static void initToolkit() throws InterruptedException
    {
        final CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            new JFXPanel();
            latch.countDown();
        });

        if (!latch.await(5L, TimeUnit.SECONDS))
            throw new ExceptionInInitializerError();
    }

	@Test
	public void call() throws Exception {

		StringTask task = new StringTask();
		assertThat(task.call(), is("task was done"));
	}

	@Test
	public void executor() throws Exception {

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		StringTask task = new StringTask();
		Future<String> future = (Future<String>) executorService.submit(task);
		assertThat(task.getState(), is(State.READY));
		assertThat(task.get(), is("task was done"));
		assertThat(future.get(), is(nullValue()));
		assertThat(task.get(), is("task was done"));

		// エラーのためコメントアウト
		// java.lang.IllegalStateException: Task must only be used from the FX Application Thread
		// assertThat(task.getState(), is(State.READY));
		// assertThat(task.getValue(), is(nullValue()));
	}

	@Test
	public void service(){

		Service<String> service = new Service<String>(){

			@Override
			protected Task<String> createTask() {
				return new StringTask();
			}
		};

		assertThat(service.getState(), is(State.READY));
		service.start();

		// エラーのためコメントアウト
		// java.lang.IllegalStateException: Task must only be used from the FX Application Thread
		// service.getValue();
		// service.getState();
	}
}
