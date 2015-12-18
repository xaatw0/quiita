package fxwindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

public class DatePanelController implements Initializable, IPanel{

	@FXML private DatePicker datePicker;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@Override
	public String getResult() {
		return null;
	}

	@Override
	public String getFxml() {
		return "DatePanel.fxml";
	}
}
