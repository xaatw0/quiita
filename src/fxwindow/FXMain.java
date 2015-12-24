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

			changeWindow(new MainWindowController());

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

			mainStage.setScene(new Scene(loader.getRoot()));
			mainStage.show();
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public void backToMainWindow(){
		subStage.close();
	}

	public IPanel openWindow(String fileName){

		Stage newState = new Stage();
		newState.initOwner(mainStage);
		newState.initModality(Modality.APPLICATION_MODAL);

		IPanel controller = null;

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(fileName).openStream());
			newState.setScene(new Scene(loader.getRoot()));
			controller = loader.getController();
		} catch(IOException ex){
			ex.printStackTrace();
		}

		subStage = newState;
		newState.showAndWait();

		return controller;
	}
}
