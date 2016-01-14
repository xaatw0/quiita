package fxwindow;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

public class DatePanelController implements Initializable, IPanel<LocalDate>{

	public final static String FXML_FILE = "DatePanel.fxml";

	@FXML private DatePicker datePicker;

	private LocalDate selectedDate;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@FXML
	public void btnOKPressed(ActionEvent event){
		selectedDate = datePicker.getValue();
		FXMain.getInstance().backToMainWindow();
	}

	@Override
	public LocalDate getData() {
		return selectedDate;
	}

	@Override
	public void setData(LocalDate date) {
		datePicker.setValue(date);
	}

	@Override
	public boolean isAvailableData(Object obj) {
		return obj instanceof LocalDate;
	}

}
