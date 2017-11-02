package fxtablemultitype;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class WeekdayData {

	public enum Schedule {NONE, SCHOOL, JOB, LESSON}

	public WeekdayData(String title, Schedule schedule){
		title().set(title);
		currentState().set(schedule);
		changeState().set(schedule);
		scheduled().set(currentState().get() != Schedule.NONE);

		scheduled().addListener((o,ov,nv)-> System.out.println("schedule was changed:" + ov+"->" + nv));
		changeState().addListener((o,ov,nv)-> System.out.println("changeState was changed:" + ov+"->" + nv));
	}

	private StringProperty title;

	public StringProperty title(){
		return title == null ? title = new SimpleStringProperty():title;
	}

	private BooleanProperty scheduled;

	public BooleanProperty scheduled(){
		return scheduled == null ? scheduled = new SimpleBooleanProperty(): scheduled;
	}

	private ObjectProperty<Schedule> currentState;

	public ObjectProperty<Schedule> currentState(){
		return currentState == null ? currentState = new SimpleObjectProperty<Schedule>(): currentState;
	}

	private ObjectProperty<Schedule> changeState;

	public ObjectProperty<Schedule> changeState(){
		return changeState == null ? changeState = new SimpleObjectProperty<Schedule>(): changeState;
	}
}
