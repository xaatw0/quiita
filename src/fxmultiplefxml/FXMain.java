package fxmultiplefxml;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class FXMain extends Application {

	private final String FXML_NAME = "IncludeExampleTree.fxml";

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(FXML_NAME).openStream());
			BorderPane root = loader.getRoot();
			Scene scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
