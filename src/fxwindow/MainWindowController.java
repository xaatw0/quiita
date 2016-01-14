package fxwindow;

import java.net.URL;
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
	 * 「パネル開く」ボタン押下時のイベント。チェックがあればDatePanel,なければTextPenelを開く。
	 * @param event
	 */
	@FXML
	public void btnOpenPressed(ActionEvent event){

		boolean isDateSelected = chkDate.selectedProperty().get();

		// チェックボックスに、チェックがあれば日付、なければテキストを入力するパネルを表示する
		String fxmlFile = isDateSelected ? DatePanelController.FXML_FILE: TextPanelController.FXML_FILE;
		IPanel<?> panel = FXMain.getInstance().openPanel(fxmlFile, data);

		// パネルでデータが入力されていれば、入力結果を表示する
		Object result = panel.getData();
		if (result != null){
			data = result;
			lblResult.setText(data.toString());
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
}
