package fxeditablecombobox;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

public class ComboboxMenu {

	private IntegerProperty id = new SimpleIntegerProperty();
	public IntegerProperty idProperty() {return id;}
	public int getId(){ return idProperty().get();}
	public void setId(int value){ idProperty().set(value);}

	private StringProperty title = new SimpleStringProperty();
	public StringProperty titleProperty() { return title;}
	public String getTitle() {return title.get();}
	public void setTitle(String value){ title.set(value);}

	public static class ComboboxMenuConverter extends StringConverter<ComboboxMenu>{

		@Override
		public String toString(ComboboxMenu object) {
			return object.getTitle();
		}

		@Override
		public ComboboxMenu fromString(String string) {

			ComboboxMenu menu = new ComboboxMenu();
			menu.setTitle(string);
			return menu;
		}
	}
}
