package textformat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FXLogic {

	private StringProperty number = new SimpleStringProperty();

	private StringProperty alphabet = new SimpleStringProperty();

	private StringProperty reg = new SimpleStringProperty();

	public void initialize(){
		TextFieldLib.setNumberFormat(number);
	}

	public StringProperty number(){return number;}

}
