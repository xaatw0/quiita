package fxtablestring;

import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

public class FXController implements Initializable{

	@FXML private TableView<StringProperty[]> table;

	@FXML private ComboBox<Integer> cmbRowNumber;

	@FXML private ComboBox<Integer> cmbColNumber;

	@FXML private TextField text;

	private StringProperty[][] data;

	private ObservableList<StringProperty[]> observableList;

	private String[][] initData ={
		{"あ","い","う","え","お"},
		{"か","き","く","け","こ"},
		{"さ","し","す","せ","そ"},
		{"た","ち","つ","て","と"},
		{"な","に","ぬ","ね","の"},
		{"は","ひ","ふ","へ","ほ"},
		{"ま","み","む","め","も"}
	};

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		observableList = FXCollections.observableArrayList();
		table.setItems(observableList);
		int maxCol = Arrays.stream(initData).mapToInt(p->p.length).max().getAsInt();
		data = new StringProperty[initData.length][];
		for (int i = 0; i < data.length; i++){
			data[i] = new StringProperty[maxCol];
			observableList.add(data[i]);

			for (int j = 0; j < maxCol; j++){
				data[i][j]= new SimpleStringProperty(Optional.ofNullable(initData[i][j]).orElse(""));
			}
		}

		final Callback<CellDataFeatures<StringProperty[], StringProperty[]>, ObservableValue<StringProperty[]>> callback =
				p -> new ReadOnlyObjectWrapper<StringProperty[]>(p.getValue());

		for (int i =0; i < maxCol; i++){
			final int index = i;

			String strIndex = Integer.toString(i);
			TableColumn<StringProperty[], StringProperty[]> column = new TableColumn<>(strIndex);
			table.getColumns().add(column);

			column.setCellValueFactory(callback);
			column.setCellFactory(col ->{
				return
				  new TableCell<StringProperty[], StringProperty[]>(){
					@Override
			        protected void updateItem(StringProperty[] item, boolean empty) {
						super.updateItem(item, empty);

						if (item != null){
							textProperty().set(item[index].get());
						}
					}
				 };
			});
		}

		IntStream.rangeClosed(1, data.length).forEach(p-> cmbRowNumber.itemsProperty().getValue().add(Integer.valueOf(p)));
		IntStream.rangeClosed(1, maxCol).forEach(p-> cmbColNumber.itemsProperty().getValue().add(Integer.valueOf(p)));
		cmbRowNumber.setValue(1);
		cmbColNumber.setValue(1);

		text.textProperty().addListener((o,ov,nv) ->{
			data[cmbRowNumber.getValue().intValue()][cmbColNumber.getValue().intValue()].set(text.getText());
			table.refresh();
		});
	}

}
