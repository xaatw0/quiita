package fxmultiplefxml;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class FXMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(this.getClass().getResource("IncludeExampleTree.fxml"));
		final BorderPane borderPane = loader.load();
		Scene scen = new Scene(borderPane,600,400);
		primaryStage.setTitle("Include Example");
		primaryStage.setScene(scen);
		primaryStage.show();
	}

	public static void main(String[] args){
		launch(args);
	}
}
