package fxtable;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

	private StringProperty name = new SimpleStringProperty();
	public StringProperty nameProperty() {return name;}
	public String getName() {return nameProperty().get();}
	public void setName(String name){ nameProperty().set(name);}

	private IntegerProperty age = new SimpleIntegerProperty();
	public IntegerProperty ageProperty() {return age;}
	public int getAge(){return ageProperty().get();}
	public void setAge(int age){ ageProperty().set(age);}

	private StringProperty address = new SimpleStringProperty();
	public StringProperty addressProperty() {return address;}
	public String getAddress() {return addressProperty().get();}
	public void setAddress(String address){ addressProperty().set(address);}

	private BooleanProperty male = new SimpleBooleanProperty();
	public BooleanProperty maleProperty(){return male;}
	public Boolean getMale(){return maleProperty().get();}
	public void setMale(Boolean male){maleProperty().set(male);}

	private Property<Person> partner = new SimpleObjectProperty<Person>();
	public Property<Person> partnerProperty(){ return partner;}
	public Person getPartner(){ return partnerProperty().getValue();}
	public void setPartner(Person partner){partnerProperty().setValue(partner);}


	@Override
	public String toString(){
		return  Stream.of(getName(),getAddress(), getMale()?"男性":"女性")
				.collect(Collectors.joining(",","Person[","]"));
	}

}
