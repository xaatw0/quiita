package quartz;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulerListener;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskExecutor;

import java.time.LocalTime;

public class Cron4j {

	public static void main(String[] args) {

		Scheduler scheduler = new Scheduler();
		scheduler.schedule("* * * * *", new SayHello());
		scheduler.schedule("*/5 * * * *", new SayHelloTask());
		scheduler.addSchedulerListener(new SayHelloListener());
		scheduler.start();
	}

	private static class SayHello implements Runnable {

		@Override
		public void run() {
			System.out.println("Hello, world!");
		}
	}

	private static class SayHelloTask extends Task {

		@Override
		public boolean supportsStatusTracking() {
			return true;
		}

		@Override
		public void execute(TaskExecutionContext ctx) throws RuntimeException {
			System.out.println("Hello, world! @" + LocalTime.now().toString());
			ctx.setStatusMessage("STATUS: OK");
		}
	}

	private static class SayHelloListener implements SchedulerListener {

		@Override
		public void taskFailed(TaskExecutor te, Throwable e) {
			System.out.println("Failed: " + e.getMessage());
		}

		@Override
		public void taskLaunching(TaskExecutor te) {
			System.out.println("Starting: "
					+ te.getTask().getClass().getSimpleName());
		}

		@Override
		public void taskSucceeded(TaskExecutor te) {
			System.out.println("Succeed: " + te.getStatusMessage());
		}

	}
}
