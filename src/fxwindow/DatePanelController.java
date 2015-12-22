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

	private Result result;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	@Override
	public Result getResult() {

		return result;
	}

	@Override
	public String getFxml() {
		return "DatePanel.fxml";
	}

	@Override
	public String getText() {

		return datePicker.getPromptText();
	}

	@FXML
	public void btnOKPressed(ActionEvent event){
		result = IPanel.Result.OK;
		FXMain.getInstance().backToMainWindow();
	}

	@Override
	public LocalDate getData() {
		return datePicker.getValue();
	}
}
