package fxbind;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.StringConverter;

public class CmbMenu {

	private IntegerProperty id = new SimpleIntegerProperty();
	public IntegerProperty idProperty(){return id;}
	public int getId(){ return idProperty().get();}
	public void setId(int id) { idProperty().set(id);}

	private StringProperty title = new SimpleStringProperty();
	public StringProperty titleProperty(){ return title;}
	public String getTitle() { return titleProperty().get();}
	public void setTitle(String title) { titleProperty().set(title);}

	public CmbMenu(int id, String title){
		idProperty().set(id);
		titleProperty().set(title);
	}

	@Override
	public String toString(){
		return "CmbMenu[" + idProperty().get() + "," + titleProperty().get() + "]";
	}

	public static class CmbMenuStringConverter extends StringConverter<CmbMenu>{

		@Override
		public String toString(CmbMenu object) {

			if (object == null){ return "データなし";}
			return object.getTitle();
		}

		@Override
		public CmbMenu fromString(String string) {
			CmbMenu cmbMenu = new CmbMenu(-1,string);
			return cmbMenu;
		}
	}
}
