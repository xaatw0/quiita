package fxwindow;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class FXMain extends Application {

	private final String FXML_NAME = "MainWindow.fxml";

	private Stage stage;

	@Override
	public void start(Stage primaryStage) {

		try {

			stage = primaryStage;

			MainWindowController firstWindows = new MainWindowController();

			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(FXML_NAME).openStream());
			Pane root = loader.getRoot();
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

	public void changeWindow(IPanel panel) throws IOException{

		FXMLLoader loader = new FXMLLoader();
		loader.load(getClass().getResource(panel.getFxml()).openStream());

		stage.setScene(new Scene(loader.getRoot()));
		stage.show();
	}
}
