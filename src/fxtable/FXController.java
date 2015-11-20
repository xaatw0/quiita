package fxtable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class FXController implements Initializable{

	/** 人を表示するテーブル */
	@FXML private TableView<Person> table;

	/** 名前の入力欄*/
	@FXML private TextField txtName;

	/** 年齢の選択肢*/
	@FXML private ChoiceBox<Integer> choiceAge;

	/** 追加ボタン*/
	@FXML private Button btnAdd;

	/** 削除ボタン*/
	@FXML private Button btnDelete;

	/** 氏名の列*/
	@FXML private TableColumn<Person, String> colName;

	/** 住所の列*/
	@FXML private TableColumn<Person, String> colAddress;

	/** 年齢の列*/
	@FXML private TableColumn<Person, Integer> colAge;

	/** 上部のメッセージ欄*/
	@FXML private Label lblMessage;

	/** 人を表示するテーブルに表示するデータのリスト */
	private final ObservableList<Person> list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		// テーブルに表示するリストを設定
		table.setItems(list);
		table.setEditable(true);

		// テーブルの行が選択されると、上記にメッセージがでる
		/**
		ChangeListener<Person> func = new ChangeListener<Person>() {
			@Override
			public void changed(ObservableValue<? extends Person> observableValue,
					Person oldPerson, Person newPerson) {
					 lblMessage.setText(newPerson.getName() + "が選択された");
			}
		};
		 **/
		table.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends Person> observableValue, Person oldPerson, Person newPerson)
				-> lblMessage.setText(newPerson.getName() + "が選択された"));


		// 読み込み専用のセルの設定
		// fxmlのIDにname,ageと設定しても、読んでくれなかった。
		colName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));


		// 編集可能なセルの設定
		colAddress.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
		colAddress.setCellFactory(TextFieldTableCell.forTableColumn());

		colAddress.setOnEditCommit(cell ->
			list.get(cell.getTablePosition().getRow()).setAddress(cell.getNewValue()));

		// 編集可能なコンボボックスのセル
		colAge.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));

		Integer[] listAge = IntStream.range(10, 20).mapToObj(Integer::valueOf).toArray(Integer[]::new);
		colAge.setCellFactory(ComboBoxTableCell.forTableColumn(new IntegerStringConverter(),
				FXCollections.observableArrayList(listAge)));


		// 参考に一つ入れておく
		Person person = new Person();
		person.setName("あいうえお");
		person.setAge(15);
		list.add(person);

		// 年齢の選択ボックスを設定する
		ObservableList<Integer> lstAge = FXCollections.observableArrayList();
		lstAge.addAll(listAge);
		choiceAge.setItems(lstAge);
	}

	@FXML
	public void addPerson(ActionEvent event){
		Person person = new Person();
		person.setName(txtName.getText());
		person.setAge(choiceAge.getValue());
		list.add(person);

		lblMessage.setText(person.getName() + "を追加しました");
	}

	@FXML
	public void deletePerson(ActionEvent event){

		table.getSelectionModel().getSelectedItems().stream().forEach(list::remove);

		lblMessage.setText("削除しました");
	}
}
