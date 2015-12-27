package fxroot;

import java.io.IOException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class ProdId extends HBox{

	@FXML private TextField prefix;

	@FXML private TextField prodCode;

	private StringProperty prodId = new SimpleStringProperty();

	public ProdId() throws IOException{
		FXMLLoader loader = new FXMLLoader(ProdId.class.getResource("ProdId.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		loader.load();
	}

	@FXML
	public void initialize(){

		prefix.textProperty().addListener(
				(observable, oldValue, newValue) -> {
 					switch(newValue){
 					case "A":
 					case "B":
 					case "C":
 						prodCode.requestFocus();
 						break;
 					default:
 						prefix.setText("");
 						break;
					}
				}
			);


		prodCode.textProperty().addListener(
			(observable, oldValue, newValue) -> {
				if (newValue.length() > 6){
					prodCode.setText(newValue.substring(0,6));
				} else if(newValue.length() ==0){
					prefix.requestFocus();
				}
			}
		);
		prodId.bind(prefix.textProperty().concat("-").concat(prodCode.textProperty()));
	}

	public String getProdId(){
		return prodId.get();
	}

	public StringProperty prodIdProperty(){
		return prodId;
	}
	public void setProdId(String prodId){
		this.prodId.set(prodId);
	}
}
