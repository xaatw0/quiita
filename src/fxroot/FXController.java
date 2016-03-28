package fxroot;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class FXController implements Initializable{

	@FXML private ProdId prodId;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		prodId.prodIdProperty().set("2");
	}
}
