package threadcancelprogresstask;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

/**
 * (参考) https://docs.oracle.com/javase/jp/8/javafx/interoperability-tutorial/concurrency.htm
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

	/** ラベルのメッセージ*/
	private StringProperty messageProperty = new SimpleStringProperty();

	/** 実行・キャンセルボタンの状態(最初は実行ボタンのみが有効、実行後キャンセルボタンのみ有効になる)*/
	private BooleanProperty isBtnExecuteDisabled = new SimpleBooleanProperty(false);

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		// ラベルの内容はメッセージにバインドして、状態実行前にする
		lblStatus.textProperty().bind(messageProperty);
		messageProperty.set("実行前");

		// 実行ボタンとキャンセルボタンの使用可否を逆の状態にする
		btnExecute.disableProperty().bind(isBtnExecuteDisabled);
		btnCancel.disableProperty().bind(isBtnExecuteDisabled.not());
	}

	@FXML
	public void execute(ActionEvent e){

		final Task<Void> task = new Task<Void>(){

			@Override
			protected Void call() throws Exception {

				int count = 0;

				while (count < 10){

					updateProgress(count, 10);
					updateMessage("count: " + count);

					if (isCancelled()){
						break;
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					count ++;
				}

				updateProgress(count, 10);
				return null;
			}

			@Override
			protected void running(){
				updateMessage("実施中");
				isBtnExecuteDisabled.set(true);
			}

		    @Override
		    protected void succeeded() {
		        updateMessage("完了しました");
		    }

		    @Override
		    protected void cancelled() {
		        updateMessage("キャンセルされました");
		    }

		    @Override
		    protected void failed() {
			    updateMessage("失敗した");
		    }

		    @Override
		    protected void done(){
		        btnCancel.setOnAction(null);
		        isBtnExecuteDisabled.set(false);
		    }
		};

		progressBar.progressProperty().bind(task.progressProperty());
		lblStatus.textProperty().bind(task.messageProperty());
		btnCancel.setOnAction(event ->{task.cancel(true);});

		backgroundExec.execute(task);
	}
}
