package fxtablemultitype;

import java.util.List;

import fxtablemultitype.WeekdayData.Schedule;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

/**
 * 行の情報
 */
public class RowInfo {

	private final int rowIndex;

	public RowInfo(int rowIndex, String title){
		this.rowIndex = rowIndex;
		this.title().set(title);
	}

	private StringProperty title;
	public StringProperty title(){
		return title == null ? title = new SimpleStringProperty(): title;
	}

	public String getTitle(){
		return title().get();
	}

	private List<WeekdayData> list;
	public WeekdayData get(int index){
		return list.get(index);
	}
	public void setWeekdayDate(List<WeekdayData> list){
		this.list = list;
	}

	public void updateItem(TableCell<RowInfo, RowInfo> cell, WeekdayData item){

		if (rowIndex ==0){
			// 1行目 チェックボックス
			CheckBox checkBox = new CheckBox();
			checkBox.selectedProperty().bindBidirectional(item.scheduled());
			cell.setGraphic(checkBox);

		} else if (rowIndex == 1){
			// 2行目 テキスト
			cell.textProperty().bind(item.currentState().asString());

		} else if (rowIndex == 2){
			// 3行目 コンボボックス
			ComboBox<Schedule> comboBox = new ComboBox<>(FXCollections.observableArrayList(Schedule.values()));
			comboBox.valueProperty().bindBidirectional(item.changeState());
			cell.setGraphic(comboBox);
		} else {
			cell.setText("N/A");
			cell.setGraphic(null);
		}
	}
}
