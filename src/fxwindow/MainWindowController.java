package fxwindow;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


public class MainWindowController implements Initializable{

	public static final String FXML_FILE = "MainWindow.fxml";

	@FXML Label lblResult;
	@FXML CheckBox chkDate;

	/** パネルから入力されたデータ。新しくパネルが表示された場合、初期値として使用する*/
	private Object data;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	/**
	 * 「テキスト」ボタン押下時のイベント。TextPenelを開く。
	 * @param event
	 */
	@FXML
	public void btnTextPressed(ActionEvent event){

		IPanel<Object> panel = FXMain.getInstance().openPanel(TextPanelController.FXML_FILE, convert(data, String.class));

		// パネルでデータが入力されていれば、入力結果を表示する
		Object result = panel.getData();
		if (result != null){
			data = result;
			lblResult.textProperty().set(data.toString());
		}
	}

	/**
	 * 「日付」ボタン押下時のイベント。DatePanelを開く。
	 * @param event
	 */
	@FXML
	public void btnDatePressed(ActionEvent event){

		IPanel<Object> panel = FXMain.getInstance().openPanel(DatePanelController.FXML_FILE, convert(data, LocalDate.class));

		// パネルでデータが入力されていれば、入力結果を表示する
		Object result = panel.getData();
		if (result != null){
			data = result;
			lblResult.textProperty().set(data.toString());
		}
	}

	/**
	 * 「ログアウト」ボタンを押下時のイベント。ログイン画面に遷移する。
	 * @param event
	 */
	@FXML
	public void btnLogoutPressed(ActionEvent event){
		FXMain.getInstance().changeMainWindow(LoginWindowController.FXML_FILE);
	}

	/**
	 * 既存データが開くパネルに対応したデータであれば値を、対応していないデータであればnullを返す
	 * @param obj データ
	 * @param clazz パネルに対応したクラス型
	 * @return データ、もしくは、null
	 */
	private Object convert(Object obj, Class clazz){

		return clazz.isInstance(obj) ? obj:null;
	}
}
