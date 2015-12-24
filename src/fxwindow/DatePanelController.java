package fxwindow;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

public class DatePanelController implements Initializable, IPanel<LocalDate>{

	@FXML private DatePicker datePicker;

	private LocalDate data;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@Override
	public String getFxml() {
		return "DatePanel.fxml";
	}

	@FXML
	public void btnOKPressed(ActionEvent event){
		data = datePicker.getValue();
		FXMain.getInstance().backToMainWindow();
	}

	@Override
	public LocalDate getData() {
		return data;
	}
}
