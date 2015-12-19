package fxattributeresolutions;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResolutionAndBindingController {
	@FXML
	private Label resourcesLabel;
	@FXML
	private Label locationLabel;
	@FXML
	private Label currentDateLabel;
	@FXML
	private URL location;
	@FXML
	private ResourceBundle resources;

	@FXML
	public void initialize() {
		locationLabel.setText(location.toString());
		resourcesLabel.setText(resources.getBaseBundleName() + " ("
				+ resources.getLocale().getCountry() + ", "
				+ resources.getLocale().getLanguage() + ")");
	}
}