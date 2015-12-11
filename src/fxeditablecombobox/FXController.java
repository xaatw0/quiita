package fxeditablecombobox;

import java.net.URL;
import java.util.ResourceBundle;

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

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		lstMenu = FXCollections.observableArrayList();
		cmbTitle.setItems(lstMenu);
	}


	@FXML
	public void addMenu(ActionEvent event){

	}

	@FXML
	public void deleteMenu(ActionEvent event){

	}

}
