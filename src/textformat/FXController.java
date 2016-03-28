package textformat;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class FXController implements Initializable{

	@FXML private TextField txtNumber;

	@FXML private TextField txtAlphabet;

	@FXML private TextField txtReg;

	FXLogic logic = new FXLogic();

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		logic.initialize();
		txtNumber.textProperty().bindBidirectional(logic.number());

	}
}
