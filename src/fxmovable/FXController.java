package fxmovable;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;

public class FXController implements Initializable{

	@FXML private SplitPane sp1;
	@FXML private SplitPane sp2;
	@FXML private SplitPane sp3;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		for (int i = 0; i < 3; i++){
			for (SplitPane pane: Arrays.asList(sp2, sp3)){
				sp1.getDividers().get(i).positionProperty().bindBidirectional(pane.getDividers().get(i).positionProperty());
			}
		}
	}
}
