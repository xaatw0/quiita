package fxtable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

	private StringProperty name = new SimpleStringProperty();
	public StringProperty name() {return name;}
	public String getName() {return name().get();}
	public void setName(String name){ name().set(name);}

	private IntegerProperty age = new SimpleIntegerProperty();
	public IntegerProperty age() {return age;}
	public int getAge(){return age().get();}
	public void setAge(int age){ age().set(age);}

	private StringProperty address = new SimpleStringProperty();
	public StringProperty address() {return address;}
	public String getAddress() {return address().get();}
	public void setAddress(String address){ address().set(address);}

}