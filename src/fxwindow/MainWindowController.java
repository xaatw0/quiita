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
	public String getFxml() {
		return "MainWindow.fxml";
	}

	@FXML
	public void btnOpenPressed(ActionEvent event){
		IPanel panel = chkDate.selectedProperty().get() ? new DatePanelController(): new TextPanelController();
		Object result = FXMain.getInstance().openWindow(panel.getFxml()).getData();

		if (result != null){
			lblResult.setText(result.toString());
		}
	}


	@Override
	public Void getData() {
		return null;
	}
}
