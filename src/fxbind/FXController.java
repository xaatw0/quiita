package fxbind;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class FXController implements Initializable{

	@FXML CheckBox checkBox1;
	@FXML CheckBox checkBox2;
	@FXML CheckBox checkBox3;
	@FXML CheckBox checkBox4;
	@FXML CheckBox checkBox5;

	@FXML Button button;
	@FXML Label label;
	@FXML Label lblCheckbox5;

	@FXML ComboBox<String> cmbBox;
	@FXML Label lblCmbBox;

	BooleanProperty blnBindBidirectional = new SimpleBooleanProperty();

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		// 相互に影響し合う
		checkBox1.selectedProperty().bindBidirectional(checkBox2.selectedProperty());

		// チェックボックス3の値は、チェックボックス4によるため、チェックボックス3のチェックを変更すると例外発生
		checkBox3.selectedProperty().bind(checkBox4.selectedProperty());

		// チェックがあるとき、有効になる
		button.disableProperty().bind(checkBox1.selectedProperty().not());

		StringProperty stringProperty = new SimpleStringProperty("");
		stringProperty.bind( checkBox1.selectedProperty().asString().concat(" ←チェックの中身"));
		label.textProperty().bind(stringProperty);

		checkBox5.selectedProperty().bindBidirectional(blnBindBidirectional);
		lblCheckbox5.textProperty().bind(blnBindBidirectional.asString());
		cmbBox.editableProperty().bind(blnBindBidirectional);;

	}

	@FXML
	public void buttonPressed(ActionEvent event){
		blnBindBidirectional.set(! blnBindBidirectional.get());
	}
}
