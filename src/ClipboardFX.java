import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;

public class ClipboardFX extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		TextArea text = new TextArea();
		Scene scene = new Scene(text);

		Clipboard clipboard = Clipboard.getSystemClipboard();
		primaryStage.focusedProperty().addListener(e ->{

			// ウィンドウにフォーカスが来たら、クリップボードのテキストをペーストする
			if (primaryStage.focusedProperty().get()){
				if (clipboard.hasString()){
					text.textProperty().set(clipboard.getString());
				}
			} else {

				// ウィンドウからフォーカスが外れたら、クリップボードにテキストをコピーする
				final ClipboardContent content = new ClipboardContent();
				content.putString(text.textProperty().get());
				clipboard.setContent(content);
			}
		});

		primaryStage.setTitle("クリップボードテスト");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
