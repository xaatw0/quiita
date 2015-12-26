package fxmultiplefxml;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class IncludeExampleDetail{

	@FXML private Label category;

	@FXML private Label name;

	@FXML private TextArea description;

	private Product product;
	private ChangeListener<String> listner;

	public void setProduct(Product product) {
		if(this.product != null){
			unhookListner();
		}
		this.product = product;
		hookTo(product);
	}

	private void unhookListner(){

		description.textProperty().removeListener(listner);
	}

	private void hookTo(Product product){
		if (product == null){
			category.setText("");
			name.setText("");
			description.setText("");
			listner = null;
		} else {
			category.setText(product.getCategory());
			name.setText(product.getName());
			description.setText(product.getDescritpion());
		}

		listner = (observable, oldValue,newValue)
				-> product.setDescritpion(newValue);
				description.textProperty().addListener(listner);
	}
}
