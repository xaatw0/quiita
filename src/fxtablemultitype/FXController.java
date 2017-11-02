package fxtablemultitype;

import java.net.URL;
import java.util.ResourceBundle;

import fxtablemultitype.WeekdayData.Schedule;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class FXController implements Initializable{

	@FXML private TableView<RowInfo> table;

	@FXML private TableColumn<RowInfo, String> colTitle;

	private ObservableList<WeekdayData> list;

	private ObservableList<RowInfo> rowInfo;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		list = FXCollections.observableArrayList();
		list.add(new WeekdayData("月", Schedule.SCHOOL));
		list.add(new WeekdayData("火", Schedule.JOB));
		list.add(new WeekdayData("水", Schedule.LESSON));
		list.add(new WeekdayData("木", Schedule.SCHOOL));
		list.add(new WeekdayData("金", Schedule.NONE));
		list.add(new WeekdayData("土", Schedule.JOB));
		list.add(new WeekdayData("日", Schedule.NONE));

		rowInfo = FXCollections.observableArrayList();
		rowInfo.add(new RowInfo(0, "予定有"));
		rowInfo.add(new RowInfo(1, "現在の予定"));
		rowInfo.add(new RowInfo(2, "変更後の予定"));
		rowInfo.stream().forEach(p -> p.setWeekdayDate(list));
		table.setItems(rowInfo);

		colTitle.setCellValueFactory(new PropertyValueFactory<RowInfo, String>("title"));
		colTitle.sortableProperty().set(false);

		final Callback<CellDataFeatures<RowInfo, RowInfo>, ObservableValue<RowInfo>> callback =
				p -> new ReadOnlyObjectWrapper<RowInfo>(p.getValue());

		for (int i =0; i < list.size(); i ++){
			final int idxCol = i;

			TableColumn<RowInfo, RowInfo> column = new TableColumn<>();
			column.textProperty().bind(list.get(idxCol).title());
			column.sortableProperty().set(false);
			table.getColumns().add(column);

			column.setCellValueFactory(callback);
			column.setCellFactory(col ->{
				return new TableCell<RowInfo, RowInfo>(){
					@Override
			        protected void updateItem(RowInfo item, boolean isEmpty) {
			            super.updateItem(item, isEmpty);
			            if (item == null || isEmpty){
			            	return;
			            }

			            item.updateItem(this, item.get(idxCol));
					}
				};
			});
		}
	}
}
