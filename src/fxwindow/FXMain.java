package fxwindow;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMain extends Application {

	private Stage mainStage;

	private Stage subStage;

	private static FXMain instance;

	@Override
	public void start(Stage primaryStage) {

		try {
			mainStage = primaryStage;
			instance = this;

			changeWindow(LoginWindowController.FXML_FILE);

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

	public void changeWindow(String fxmlFile){

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(fxmlFile).openStream());

			mainStage.setScene(new Scene(loader.getRoot()));
			mainStage.show();
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public void backToMainWindow(){
		subStage.close();
	}

	public IPanel<?> openWindow(String fxmlFile){

		Stage newStage = new Stage();
		newStage.initOwner(mainStage);
		newStage.initModality(Modality.APPLICATION_MODAL);

		IPanel<?> controller = null;

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(fxmlFile).openStream());
			newStage.setScene(new Scene(loader.getRoot()));
			controller = loader.getController();
		} catch(IOException ex){
			ex.printStackTrace();
		}

		subStage = newStage;
		newStage.showAndWait();

		return controller;
	}
}
