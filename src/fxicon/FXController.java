package fxicon;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.When;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

public class FXController implements Initializable{

	private final static String PATH_ICON_CHECKED = "star.png";

	private final static String PATH_ICON_NO_CHECK = "x.png";

	@FXML
	private CheckBox check;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {
	}

	public void setIconProperty(ObservableList<Image> icons){

		ObjectProperty<Image> icon = new SimpleObjectProperty<>();
		icon.bind(new When(check.selectedProperty())
				.then(new Image(getClass().getResource(PATH_ICON_CHECKED).toString()))
				.otherwise(new Image(getClass().getResource(PATH_ICON_NO_CHECK).toString())));

		icon.addListener( (o, oldValue, newValue) -> {
			icons.remove(oldValue);
			icons.add(newValue);
		});

		icons.add(icon.get());
	}
}
