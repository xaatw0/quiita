package threadcancelprogress;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * http://www.torutk.com/projects/swe/wiki/JavaFXとマルチスレッド
 * https://docs.oracle.com/javase/jp/8/javafx/api/javafx/application/Platform.html#runLater-java.lang.Runnable-
 *https://docs.oracle.com/javase/jp/8/javafx/interoperability-tutorial/concurrency.htm
 */
public class FXController implements Initializable{

	@FXML
	private Button btnCancel;

	@FXML
	private Label lblStatus;

	@FXML
	private ProgressBar progressBar;

	private ExecutorService backgroundExec = Executors.newCachedThreadPool();

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@FXML
	public void execute(ActionEvent e){

		final EventHandler<ActionEvent> listener = new EventHandler<ActionEvent>() {

			private BackgroundTask<Void> task;

			@Override
			public void handle(ActionEvent event) {
				if (task != null){
					task.cancel(true);
				}
			}
		};

		BackgroundTask<Void> task = new BackgroundTask<Void>() {

			@Override
			protected Void compute() {
				while (morework() && ! isCancelled()){
					doSomeWork();
				}
				return null;
			}



			@Override
			public void onCompletion(Void result, Throwable exception, boolean canceled){
				btnCancel.setOnAction(null);
				lblStatus.setText(canceled ? "キャンセルされた":"完了した");
			}
		};


		btnCancel.setOnAction(listener);
		backgroundExec.execute(task);
	}
}
