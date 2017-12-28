package fxmovable;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;


public class FXMain extends Application {

	private final String FXML_NAME = "FXMain.fxml";

	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(FXML_NAME).openStream());
			SplitPane root = loader.getRoot();
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
