import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BrowserFromJavaFX extends Application {

	private static String url = "www.google.com";

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button button = new Button("ブラウザを呼ぶ");
		button.onActionProperty().set(e->{
			try {
				Desktop.getDesktop().browse(new URI(url));
			} catch (IOException | URISyntaxException e1) {
				e1.printStackTrace();
			}
		});

		final Scene scene = new Scene(button);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
