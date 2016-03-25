package fxcss;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class FirstCSS extends Application {

	@Override
	public void start(Stage primaryStage) {

		Scene scene = new Scene(new Group());
		scene.getStylesheets().add(getClass().getResource("test.css").toString());
		Rectangle rect = new Rectangle(100,100);
		rect.setLayoutX(50);
		rect.setLayoutY(50);
		rect.getStyleClass().add("my-rect");
		//((Group)scene.getRoot()).getStyleClass().add("my-rect");
		((Group)scene.getRoot()).getChildren().add(rect);

		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
