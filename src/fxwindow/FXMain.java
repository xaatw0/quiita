package fxwindow;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class FXMain extends Application {

	private Stage mainWindows;

	private Stage subPanel;

	private static FXMain instance;

	@Override
	public void start(Stage primaryStage) {

		try {
			mainWindows = primaryStage;
			instance = this;

			changeWindow(LoginWindowController.FXML_FILE);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static FXMain getInstance(){
		return instance;
	}

	/**
	 * メインウィンドウを切り替える。ログインウィンドウとメインウィンドウの移動に使用している。
	 * @param fxmlFile
	 */
	public void changeWindow(String fxmlFile){

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(fxmlFile).openStream());

			mainWindows.setScene(new Scene(loader.getRoot()));
			mainWindows.show();
		} catch(IOException ex){
			ex.printStackTrace();
		}
	}

	/**
	 * 入力用に表示したパネルを閉じる。
	 */
	public void backToMainWindow(){
		subPanel.close();
	}

	/**
	 * 入力用のパネルを表示する。入力が完了するまでメインウィンドウを選択できない(モーダル)。<br/>
	 * 読み込んだウィンドウのコントロールを返す。
	 * @param fxmlFile
	 * @return
	 */
	public IPanel<?> openPanel(String fxmlFile){

		Stage newStage = new Stage();
		newStage.initOwner(mainWindows);
		newStage.initModality(Modality.APPLICATION_MODAL);

		IPanel<?> controller = null;

		try{
			FXMLLoader loader = new FXMLLoader();
			loader.load(getClass().getResource(fxmlFile).openStream());
			newStage.setScene(new Scene(loader.getRoot()));
			controller = loader.getController();
		} catch(IOException ex){
			ex.printStackTrace();
		}

		subPanel = newStage;

		// データ入力が完了するまで待機する
		newStage.showAndWait();

		return controller;
	}
}
