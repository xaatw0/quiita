import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ButtonDisableTest extends Application{

	private Button btnNoExecutor;
	private Button btnExecutor;

	private ExecutorService service;

	@Override
	public void start(Stage primaryStage) throws Exception {
		btnNoExecutor = new Button("サービス未使用");
		btnNoExecutor.setOnAction(this::btnNoExecutorClicked);

		btnExecutor = new Button("サービス使用");
		btnExecutor.setOnAction(this::btnExecutorClicked);

		primaryStage.setScene(new Scene(new HBox(btnNoExecutor, btnExecutor)));
		primaryStage.show();

		service = Executors.newSingleThreadExecutor();
	}

	private void btnNoExecutorClicked(ActionEvent e) {

		btnNoExecutor .disableProperty().set(true);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			btnNoExecutor .disableProperty().set(false);
	}

	private void btnExecutorClicked(ActionEvent e) {

		btnExecutor.disableProperty().set(true);
		service.submit(()->{
			Thread.sleep(5000);
			btnExecutor.disableProperty().set(false);
			return null;
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
