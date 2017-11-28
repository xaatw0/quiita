package fxtablestring;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FXController implements Initializable{

	@FXML private TableView<String[]> table;

	@FXML private ComboBox<Integer> cmbRowNumber;

	@FXML private ComboBox<Integer> cmbColNumber;

	@FXML private TextField text;

	private ObservableList<String[]> observableList;

	private String[][] data ={
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
		int maxCol = Arrays.stream(data).mapToInt(p -> p.length).max().getAsInt();
		for (int i = 0; i < data.length; i++){
			observableList.add(data[i]);
		}

		for (int i =0; i < maxCol; i++){
			TableColumn<String[], String> column = new TableColumn<>();
			column.prefWidthProperty().set(60.0);
			table.getColumns().add(column);

			final int idxCol = i;
			column.setCellValueFactory(p -> new SimpleStringProperty(p.getValue()[idxCol]));
		}

		// コンボボックス
		IntStream.range(0, data.length).forEach(p-> cmbRowNumber.itemsProperty().getValue().add(Integer.valueOf(p)));
		IntStream.range(0, maxCol).forEach(p-> cmbColNumber.itemsProperty().getValue().add(Integer.valueOf(p)));
		cmbRowNumber.setValue(0);
		cmbColNumber.setValue(0);

		// テキストの変更をテーブルに反映
		text.textProperty().addListener((o,ov,nv) ->{
			data[cmbRowNumber.getValue().intValue()][cmbColNumber.getValue().intValue()] = text.getText();
			table.refresh();
		});
	}
}
