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

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@FXML
	public void btnOpenPressed(ActionEvent event){

		// チェックボックスに、チェックがあれば日付、なければテキストを入力するダイアログを表示する
		String fxmlFile = chkDate.selectedProperty().get() ? DatePanelController.FXML_FILE: TextPanelController.FXML_FILE;
		IPanel<?> panel = FXMain.getInstance().openWindow(fxmlFile);

		Object result = panel.getData();
		if (result != null){
			lblResult.setText(result.toString());
		}
	}
}
