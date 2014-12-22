package guice;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import com.google.inject.Inject;

public class MainController implements Initializable{


	@FXML private TextField input1;
	@FXML private TextField input2;
	@FXML private Label lblAdd;
	@FXML private Label lblSubstract;

	private StringProperty propertyAdd = new SimpleStringProperty();
	private StringProperty propertySubstract = new SimpleStringProperty();

	@FXML private Button btn;

	@Inject
	private LogicInterface logic;

	@FXML
	public void onBtnClicked(ActionEvent event){

		int intInput1 = Integer.parseInt(input1.getText());
		int intInput2 = Integer.parseInt(input2.getText());

		propertyAdd.set(logic.add(intInput1, intInput2));
		propertySubstract.set(logic.substract(intInput1, intInput2));
	}

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		lblAdd.textProperty().bind(propertyAdd);
		lblSubstract.textProperty().bind(propertySubstract);
	}
}
