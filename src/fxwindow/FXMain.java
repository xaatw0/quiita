package fxwindow;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * アプリケーションの開始・終了、ウィンドウの表示や閉じる処理を管理するクラス。
 *
 * (参考)
 * http://www.javafxtutorials.com/tutorials/creating-a-pop-up-window-in-javafx/
 * http://qiita.com/mix/items/7c797eccad0a5e3a43c3
 */
public class FXMain extends Application {

	/**
	 * 主なウィンドウ(LoginWindowとMainWindow)。
	 * Application.startの引数のStateを設定し、主要なウィンドウになる。
	 * 他のパネル(情報入力用の他のウィンドウ)の親となり、アプリケーションが終わるまで存在する。
	 */
	private Stage mainWindows;

	/**
	 * データ入力用のパネル(DatePanelとTextPanel)
	 * mainWindowから呼び出し、データを入力するためのパネルを表示するStage。
	 * 入力用のパネルを表示すると、
	 */
	private Stage subPanel;

	/** アプリケーション内のどこからでも呼び出せるようにSingletonパターンでFXMainクラスを実装している。 */
	private static FXMain instance;

	@Override
	public void start(Stage primaryStage) {

		mainWindows = primaryStage;
		instance = this;

		try {
			changeMainWindow(LoginWindowController.FXML_FILE);

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
	 * メインウィンドウを切り替える。
	 * ログインウィンドウとメインウィンドウの移動に使用している。
	 * 同じStateを使用しているため、ログインウィンドウとメインウィンドウが同時に表示されることはない。
	 * @param fxmlFile
	 */
	public void changeMainWindow(String fxmlFile){

		FXMLLoader loader = new FXMLLoader();

		try{
			loader.load(getClass().getResource(fxmlFile).openStream());
		} catch(IOException ex){
			ex.printStackTrace();
		}

		mainWindows.setScene(new Scene(loader.getRoot()));
		mainWindows.show();
	}

	/**
	 * 入力用に表示したパネルを閉じて、メインウィンドウに戻る。
	 */
	public void backToMainWindow(){
		subPanel.close();
	}

	/**
	 * 入力用のパネルを表示する。入力が完了するまでメインウィンドウを選択できない(モーダル)。<br/>
	 * 読み込んだウィンドウのコントロールを返す。
	 * @param <T> 表示するパネルが取り扱うデータの型。
	 * @param fxmlFile 表示するパネルのFXMLファイル
	 * @param data 初期化に使用するデータ
	 * @return 表示するパネルのコントロール
	 */
	public <T> IPanel<T> openPanel(String fxmlFile, T data){

		Stage newStage = new Stage();
		newStage.initOwner(mainWindows);
		newStage.initModality(Modality.APPLICATION_MODAL);

		IPanel<T> controller = null;
		FXMLLoader loader = new FXMLLoader();

		try{
			loader.load(getClass().getResource(fxmlFile).openStream());
		} catch(IOException ex){
			ex.printStackTrace();
		}

		newStage.setScene(new Scene(loader.getRoot()));
		controller = loader.getController();

		// 以前データを選択して、そのデータの型がパネルの型と一致している場合、初期値に設定する
		if (data != null){
			controller.setData(data);
		}

		// データ入力が完了するまで待機する
		subPanel = newStage;
		newStage.showAndWait();

		return controller;
	}
}
