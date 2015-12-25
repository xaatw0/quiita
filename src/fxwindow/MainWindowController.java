package fxwindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;


public class MainWindowController<T> implements Initializable, IPanel<Void>{

	public static final String FXML_FILE = "MainWindow.fxml";

	@FXML Label lblResult;
	@FXML CheckBox chkDate;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@FXML
	public void btnOpenPressed(ActionEvent event){
		String fxmlFile = chkDate.selectedProperty().get() ? DatePanelController.FXML_FILE: TextPanelController.FXML_FILE;
		Object result = FXMain.getInstance().openWindow(fxmlFile).getData();

		if (result != null){
			lblResult.setText(result.toString());
		}
	}


	@Override
	public Void getData() {
		return null;
	}
}
