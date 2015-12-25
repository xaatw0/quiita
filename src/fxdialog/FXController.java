package fxdialog;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class FXController implements Initializable{

	@FXML  Label label;

	StringProperty message = new SimpleStringProperty("メッセージが表示される");

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		label.textProperty().bind(message);
	}

	@FXML
	public void show(ActionEvent event){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Show");
		alert.getDialogPane().setHeaderText("AlertType.INFORMATION");
		alert.getDialogPane().setContentText("Content Text");
		alert.show();

		message.set("show()はダイアログ表示とともに、後続のコードを実施");
	}

	@FXML
	public void showAndWait(ActionEvent event){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Show and Wait");
		alert.getDialogPane().setHeaderText("AlertType.ERROR");
		alert.getDialogPane().setContentText("Content Text");
		alert.showAndWait();

		message.set("showAndWait()はダイアログを閉じたら、後続のコードを実施");
	}

	@FXML
	public void dialog(ActionEvent event){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("AlertType.CONFIRMATION");
		alert.getDialogPane().setHeaderText("どちらかを押すか、閉じるを押してください");
		alert.getDialogPane().setContentText("なにか詳細を書くスペース");

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isPresent()){
			message.set(result.get().getText() + "が押された");
		} else {
			message.set("何も押されてない？");
		}
	}
}
