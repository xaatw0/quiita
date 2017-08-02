package taskservice;

import javafx.concurrent.Task;

public class StringTask extends Task<String>{

	@Override
	protected String call() throws Exception {

		for (int i = 0; i <= 10; i++){
			Thread.sleep(100);
			updateProgress(i, 10);
		}
		return "task was done";
	}
}
