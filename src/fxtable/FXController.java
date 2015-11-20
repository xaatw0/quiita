package fxtable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

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
import javafx.scene.control.cell.PropertyValueFactory;

public class FXController implements Initializable{

	@FXML
	private TableView<Person> table;

	@FXML
	private TextField txtName;

	@FXML
	private ChoiceBox<Integer> choiceAge;

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnDelete;

	@FXML
	private TableColumn<Person, String> colName;

	@FXML
	private TableColumn<Person, Integer> colAge;

	@FXML
	private Label lblMessage;

	ObservableList<Person> list = FXCollections.observableArrayList();

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		table.setItems(list);

		// fxmlのIDにname,ageと設定しても、読んでくれなかった。
		colName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		colAge.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));

		// 参考に一つ入れておく
		Person person = new Person();
		person.setName("あいうえお");
		person.setAge(15);
		list.add(person);

		// 年齢の選択ボックスを設定する
		ObservableList<Integer> lstAge = FXCollections.observableArrayList();
		IntStream.range(13, 21).forEach(lstAge::add);
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
