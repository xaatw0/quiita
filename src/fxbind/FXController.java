package fxbind;

import java.awt.Label;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;

public class FXController implements Initializable{

	@FXML private Label lblCheck1;
	@FXML private Label lblCheck2;
	@FXML private ProgressBar progressBar;
	@FXML private Slider sliderH;
	@FXML private Slider sliderV;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}
}
