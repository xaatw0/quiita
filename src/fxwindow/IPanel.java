package fxwindow;

/**
 * パネル(メインウィンドウから呼び出し、データを入力されて、閉じて、メインウィンドウにデータを返すStage)のインターフェース。
 */
public interface IPanel<T> {

	/**
	 * パネルで入力したデータを取得する。
	 * @return 入力値
	 */
	T getData();


	/**
	 * パネルに初期表示するデータを設定する。
	 * @param data
	 */
	void setData(T data);

	/**
	 * 引数のデータが本派ネルで取り扱うデータの型か調べる
	 * @param obj データ
	 * @return true:型が正しい false:型が正しくない
	 */
	boolean isAvailableData(Object obj);
}
