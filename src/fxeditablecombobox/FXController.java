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
import javafx.scene.control.Label;

public class FXController implements Initializable{

	@FXML private ComboBox<ComboboxMenu> cmbTitle;

	@FXML private Button btnAdd;

	@FXML private Button btnDelete;

	@FXML private Label lblMessage;

	private ObservableList<ComboboxMenu> lstMenu;

	private ObjectProperty<ComboboxMenu> selectedMenu = new SimpleObjectProperty<>();

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		// コンボボックスの設定
		lstMenu = FXCollections.observableArrayList();
		cmbTitle.setPromptText("New Menu");
		cmbTitle.setItems(lstMenu);
		cmbTitle.setConverter(new ComboboxMenu.ComboboxMenuConverter());
		selectedMenu.bind(cmbTitle.getSelectionModel().selectedItemProperty());

		// コンボボックスに選択肢を追加
		ComboboxMenu menu0 = new ComboboxMenu();
		menu0.setId(-1);
		menu0.setTitle("");
		lstMenu.add(menu0);

		ComboboxMenu menu1 = new ComboboxMenu();
		menu1.setId(1);
		menu1.setTitle("メニュー1");
		lstMenu.add(menu1);

		ComboboxMenu menu2 = new ComboboxMenu();
		menu2.setId(2);
		menu2.setTitle("メニュー2");
		lstMenu.add(menu2);

		BooleanProperty blnEdiable = new SimpleBooleanProperty(true);
		blnEdiable.bind(cmbTitle.getSelectionModel().selectedItemProperty().isEqualTo(menu0));
		cmbTitle.editableProperty().bind(blnEdiable);
	}


	@FXML
	public void addMenu(ActionEvent event){

		ComboboxMenu selectedMenu1 = cmbTitle.getSelectionModel().getSelectedItem();

		ComboboxMenu selectedMenu2 = cmbTitle.valueProperty().get();

		String title = selectedMenu1.getTitle();

		if (selectedMenu1.getId() == 0){

			ComboboxMenu newMenu = new ComboboxMenu();
			newMenu.setTitle(selectedMenu1.getTitle());
			lstMenu.add(newMenu);
		} else {
			selectedMenu1.setTitle(selectedMenu1.getTitle());
		}
	}

	@FXML
	public void deleteMenu(ActionEvent event){
		ComboboxMenu selectedMenu = cmbTitle.getSelectionModel().getSelectedItem();
		lstMenu.remove(selectedMenu);
	}

	@FXML
	public void changeCombobox(ActionEvent e){
	}

}
