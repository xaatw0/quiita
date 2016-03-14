package threadcancelprogress;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	private Button btnExecute;

	@FXML
	private Button btnCancel;

	@FXML
	private Label lblStatus;

	@FXML
	private ProgressBar progressBar;

	private ExecutorService backgroundExec = Executors.newCachedThreadPool();

	private enum ExecuteStatus{
		 BEFORE_EXECUTE
		,EXECUTING
		,CANCELED
		,DONE
	}

	private StringProperty messageProperty = new SimpleStringProperty();

	private ObjectProperty<ExecuteStatus> status =
			new SimpleObjectProperty<>();

	private BooleanProperty isBtnExecuteDisabled = new SimpleBooleanProperty(false);

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		status.addListener((obs,oldValue,newValue) ->{
			if (newValue == ExecuteStatus.EXECUTING){
				messageProperty.set("実施中");
			} else if (newValue == ExecuteStatus.CANCELED){
				messageProperty.set("キャンセルされました");
			} else if (newValue == ExecuteStatus.DONE){
				messageProperty.set("完了しました");
			} else {
				messageProperty.set("実施前");
			}
		});

		lblStatus.textProperty().bind(messageProperty);
		status.set(ExecuteStatus.BEFORE_EXECUTE);

		btnExecute.disableProperty().bind(isBtnExecuteDisabled);
		btnCancel.disableProperty().bind(isBtnExecuteDisabled.not());
	}

	@FXML
	public void execute(ActionEvent e){

		final BackgroundTask<Void> backgroundTask = new BackgroundTask<Void>() {

			@Override
			protected Void compute() {

				int count = 0;

				while ( ! isCancelled()){

					setProgress(count, 10);

					if (10 < count){
						break;
					}


					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					count ++;
				}
				return null;
			}

			@Override
			protected void onStart(){
				isBtnExecuteDisabled.set(true);
				progressBar.setProgress(0);
				status.set(ExecuteStatus.EXECUTING);
			}

			@Override
			protected void onProgress(int current, int max) {
				progressBar.setProgress((float)current/max);
			};

			@Override
			public void onCompletion(Void result, Throwable exception, boolean canceled){
				btnCancel.setOnAction(null);
				status.set(canceled ? ExecuteStatus.CANCELED:ExecuteStatus.DONE);
				isBtnExecuteDisabled.set(false);
			}
		};

		final EventHandler<ActionEvent> cancelHander = new EventHandler<ActionEvent>() {

			private BackgroundTask<Void> task = backgroundTask;

			@Override
			public void handle(ActionEvent event) {
				task.cancel(true);
			}
		};

		btnCancel.setOnAction(cancelHander);
		backgroundExec.execute(backgroundTask);
	}
}
