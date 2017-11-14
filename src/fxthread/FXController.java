package fxthread;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXController implements Initializable{

	@FXML private Label text;

	@FXML private Label bind;

	@FXML private Label bidirectional;

	private StringProperty bindProperty;

	private StringProperty bidirectionalProperty;

	private ExecutorService service;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
		service = Executors.newCachedThreadPool();

		bindProperty = new SimpleStringProperty("bind");
		bind.textProperty().bind(bindProperty);

		bidirectionalProperty = new SimpleStringProperty("bidirectionalProperty");
		bidirectional.textProperty().bindBidirectional(bidirectionalProperty);
	}

	@FXML
	public void btnFxThread(ActionEvent e){
		text.textProperty().set("textPropery by FX Thread");
		bindProperty.set("bindPropertyby FX Thread");
		bidirectionalProperty.set("bidirectionalProperty by FX Thread");
	}

	@FXML
	public void btnExecutorThread(ActionEvent e){
		service.submit(()->{
			text.textProperty().set("textPropery by Executor");
			bindProperty.set("bindProperty by Executor");
			bidirectionalProperty.set("bidirectionalProperty by Executor");
		});
	}

	@FXML
	public void btnRunLater(ActionEvent e){
		service.submit(()->{
			Platform.runLater(()->{
				text.textProperty().set("textPropery by runLater");
				bindProperty.set("bindProperty by runLater");
				bidirectionalProperty.set("bidirectionalProperty by runLater");
			});
		});
	}
}
