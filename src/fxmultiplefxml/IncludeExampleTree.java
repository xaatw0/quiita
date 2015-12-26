package fxmultiplefxml;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.VBox;

public class IncludeExampleTree implements Initializable{

	@FXML private TreeTableView<Product> treeTableView;

	@FXML private TreeTableColumn<Product,String> category;

	@FXML private TreeTableColumn<Product, String> name;

	@FXML private VBox details;

	// コントロールのIDは「FXMLのID + Controller」
	@FXML private IncludeExampleDetail detailsController;

	@Override
	public void initialize(URL paramURL, ResourceBundle paramResourceBundle) {

		Product[] products = new Product[101];

		for(int i = 0; i <products.length; i++){
			products[i] = new Product();
			products[i].setCategory("category"+(i/10));
			products[i].setDescritpion("descritpion " + i);
			products[i].setName("name"+i);
		}

		TreeItem<Product> root = new TreeItem<>(products[100]);
		root.setExpanded(true);
		for (int i = 0; i <10; i++){
			TreeItem<Product> firstLevel = new TreeItem<>(products[i*10]);
			firstLevel.setExpanded(true);
			for (int j =0; j <10; j++){
				TreeItem<Product> secondLevel = new TreeItem<>(products[i*10+j]);
				secondLevel.setExpanded(true);
				firstLevel.getChildren().add(secondLevel);
			}
			root.getChildren().add(firstLevel);
		}

		category.setCellValueFactory(p ->
			new ReadOnlyStringWrapper(p.getValue().getValue().getCategory())
		);
		name.setCellValueFactory(p ->
			new ReadOnlyStringWrapper(p.getValue().getValue().getName())
		);

		treeTableView.setRoot(root);

		treeTableView.getSelectionModel().selectedItemProperty().addListener(
				(observal,oldValue,newValue) -> {
					Product product = null;
					if (newValue != null){
						product = newValue.getValue();
					}

					detailsController.setProduct(product);
				});
	}
}
