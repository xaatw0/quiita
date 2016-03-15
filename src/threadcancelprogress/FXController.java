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

	/**
	 * アプリケーションの実行状態を表す
	 */
	private enum ExecuteStatus{
		 /** 実行前*/BEFORE_EXECUTE
		,/** 実行中*/EXECUTING
		,/** キャンセル*/CANCELED
		,/** 完了*/DONE
	}

	/** アプリケーションの実行状態*/
	private ObjectProperty<ExecuteStatus> status = new SimpleObjectProperty<>();

	/** ラベルのメッセージ*/
	private StringProperty messageProperty = new SimpleStringProperty();

	/** 実行・キャンセルボタンの状態(最初は実行ボタンのみが有効、実行後キャンセルボタンのみ有効になる)*/
	private BooleanProperty isBtnExecuteDisabled = new SimpleBooleanProperty(false);

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		// 状態が変化すると、ラベルが変更になる
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

		// ラベルの内容はメッセージにバインドして、状態実行前にする
		lblStatus.textProperty().bind(messageProperty);
		status.set(ExecuteStatus.BEFORE_EXECUTE);

		// 実行ボタンとキャンセルボタンの使用可否を逆の状態にする
		btnExecute.disableProperty().bind(isBtnExecuteDisabled);
		btnCancel.disableProperty().bind(isBtnExecuteDisabled.not());
	}

	@FXML
	public void execute(ActionEvent e){

		BackgroundTask<Void> task = new BackgroundTask<Void>() {

			@Override
			protected Void compute() {

				int count = 0;

				while (! isCancelled()){

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
				btnCancel.setOnAction(getCancelEvent());
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

		backgroundExec.execute(task);
	}
}
