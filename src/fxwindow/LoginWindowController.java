package fxwindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController implements Initializable{

	public static final String FXML_FILE = "LoginWindow.fxml";

	private StringProperty loginName = new SimpleStringProperty();
	@FXML private TextField txtLoginName;

	private StringProperty password = new SimpleStringProperty();
	@FXML private TextField txtPassword;

	private StringProperty message = new SimpleStringProperty();
	@FXML private Label lblMessage;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginName.bind(txtLoginName.textProperty());
		password.bind(txtPassword.textProperty());
		lblMessage.textProperty().bind(message);
	}

	@FXML
	public void btnLoginPressed(ActionEvent event){
		if (login(loginName.get(), password.get()) ){

		} else {
			message.set("ログインIDかパスワードが違います");
		}
	}

	@FXML
	public void btnClosePressed(ActionEvent event){
		// 画面上のコントロールからウィンドウを取得する
		((Stage)(txtLoginName.getScene().getWindow())).close();
	}

	private boolean login(String loginName, String password){
		return ! loginName.isEmpty() && ! password.isEmpty();
	}
}
