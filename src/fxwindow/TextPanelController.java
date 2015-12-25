package fxwindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class TextPanelController implements Initializable, IPanel<String>{

	public static final String FXML_FILE = "TextPanel.fxml";

	@FXML private TextField text;

	private String data = null;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Override
	public String getData() {
		return data;
	}

	@FXML
	public void btnOkPressed(ActionEvent event){
		data = text.textProperty().get();
		FXMain.getInstance().backToMainWindow();
	}

	@FXML
	public void btnCancelPressed(ActionEvent event){
		data = null;
		FXMain.getInstance().backToMainWindow();
	}
}
