package fxeditablecombobox;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class FXController implements Initializable{

	@FXML private ComboBox<ComboboxMenu> cmbTitle;

	@FXML private Button btnAdd;

	@FXML private Button btnDelete;

	private ObservableList<ComboboxMenu> lstMenu;

	private ObjectProperty<ComboboxMenu> selectedMenu = new SimpleObjectProperty<>();

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		// コンボボックスの設定
		lstMenu = FXCollections.observableArrayList();
		cmbTitle.setItems(lstMenu);
		//cmbTitle.setConverter(new ComboboxMenu.ComboboxMenuConverter());
		//cmbTitle.setEditable(true);

		// コンボボックスに選択肢を追加
		ComboboxMenu menu0 = new ComboboxMenu();
		menu0.setId(-1);
		menu0.setTitle("");
		lstMenu.add(menu0);

		ComboboxMenu menu1 = new ComboboxMenu();
		menu1.setId(0);
		menu1.setTitle("メニュー1");
		lstMenu.add(menu1);

		ComboboxMenu menu2 = new ComboboxMenu();
		menu2.setId(1);
		menu2.setTitle("メニュー2");
		lstMenu.add(menu2);

		BooleanProperty blnEdiable = new SimpleBooleanProperty(true);
		blnEdiable.bind(cmbTitle.getSelectionModel().selectedItemProperty().isEqualTo(menu0));
		cmbTitle.editableProperty().bind(blnEdiable);

		//selectedMenu.bind(cmbTitle.getSelectionModel().getSelectedItem());

	}


	@FXML
	public void addMenu(ActionEvent event){
		ComboboxMenu test = cmbTitle.getSelectionModel().getSelectedItem();
		System.out.println(selectedMenu.getValue().getTitle());
	}

	@FXML
	public void deleteMenu(ActionEvent event){

	}

	@FXML
	public void changedCmbMenu(ActionEvent event){
	}

}
