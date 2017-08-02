package taskservice;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

public class FXController implements Initializable{

	private Service<String> service;

	@FXML
	private ProgressBar progressBar;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		service = new Service<String>(){
			@Override
			protected Task<String> createTask() {
				return new StringTask();
			}
		};

		service.setOnReady(e -> System.out.println("service is ready."));
		service.setOnScheduled(e -> System.out.println("service is scheduled."));
		service.setOnRunning(e -> System.out.println("service is running."));
		service.setOnSucceeded(e -> System.out.println("service is succeed." + service.getValue()));

		progressBar.progressProperty().bind(service.progressProperty());
	}

	@FXML
	public void btnClick(ActionEvent e){
		System.out.println("Before restart:" + service.getValue());
		service.restart();
		System.out.println("After restart:" + service.getValue());
	}
}
