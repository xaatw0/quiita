package fxwindow;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FXMain extends Application {

	private Stage stage;

	private static FXMain instance;

	private MainWindowController mainWindow;

	@Override
	public void start(Stage primaryStage) {

		try {

			stage = primaryStage;
			instance = this;

			changeWindow(mainWindow = new MainWindowController());

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static FXMain getInstance(){
		return instance;
	}

	public void changeWindow(IPanel panel){

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(panel.getFxml()).openStream());

			stage.setScene(new Scene(loader.getRoot()));
			stage.show();
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public void backToMainWindow(){
		changeWindow(mainWindow);
	}

	public void openWindow(IPanel panel){
		Stage newState = new Stage();
		newState.initOwner(stage);
		newState.showAndWait();
	}
}
