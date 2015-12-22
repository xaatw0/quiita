package fxwindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


public class MainWindowController<T> implements Initializable, IPanel<Void>{

	@FXML Label lblResult;
	@FXML CheckBox chkDate;

	Result result;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@Override
	public Result getResult() {
		return null;
	}

	@Override
	public String getFxml() {
		return "MainWindow.fxml";
	}

	@FXML
	public void btnOpenPressed(ActionEvent event){
		IPanel panel = new DatePanelController();
		FXMain.getInstance().openWindow(panel);

		if (panel.getResult() == Result.OK){
			lblResult.setText(panel.getData().toString());
		}
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public Void getData() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
