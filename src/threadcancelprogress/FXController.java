package threadcancelprogress;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

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

		final ChangeListener<ActionEvent> listener = new ChangeListener<ActionEvent> () {

			private BackgroundTask<Void> task;

			@Override
			public void changed(ObservableValue<? extends ActionEvent> arg0,
					ActionEvent arg1, ActionEvent arg2) {
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
				btnCancel.onActionProperty().removeListener(listener);
				lblStatus.setText(canceled ? "キャンセルされた":"完了した");
			}
		};

		btnCancel.onActionProperty().addListener((o,e1,e2)->{

		});
		btnCancel.onActionProperty().addListener(listener);
		backgroundExec.execute(task);
	}
}
