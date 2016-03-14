package threadcancelprogress;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * 実施、キャンセル、進捗状況報告を実装するタスク
 */
public abstract class BackgroundTask<V> implements Runnable, Future<V> {

	private final FutureTask<V> computation = new Computation();

	private class Computation extends FutureTask<V>{

		public Computation(){
			super(new Callable<V>() {
				@Override
				public V call() throws Exception {
					BackgroundTask.this.start();
					return BackgroundTask.this.compute();
				}
			});
		}

		@Override
		protected final void done(){
			Platform.runLater(() -> {

				V value = null;
				Throwable thrown = null;
				boolean canceled = false;

				try {
					value = get();
				} catch(ExecutionException ex){
					thrown = ex.getCause();
				} catch(CancellationException ex){
					canceled = true;
				} catch(InterruptedException ex){

				} finally{
					onCompletion(value, thrown, canceled);
				}
			});
		}
	}

	@Override
	public final void run() {
		computation.run();
	}

	/**
	 * 開始時の処理をアプリケーションスレッドに登録する
	 */
	private void start(){
		Platform.runLater(() -> onStart());;
	}

	/**
	 * computeメソッド(バックグラウンドスレッド)から呼び出し、進捗状況を数量的に表示する
	 * @param current 現状の値
	 * @param max 完了の値
	 */
	protected void setProgress(final int current, final int max){
		Platform.runLater(() -> onProgress(current, max));
	}

	/**
	 * 実際の処理を実装する。バックグラウンドスレッドで呼ばれる。<br/>
	 * メソッド内でsetProgressを使用して、画面上の進捗状況を更新する。
	 * @return 計算結果
	 */
	protected abstract V compute();

	/**
	 * 処理完了時に実施され、画面上に結果を反映させる。<br/>
	 * JavaFXのアプリケーションスレッドから呼ばれる
	 */
	protected void onCompletion(V result, Throwable exception, boolean canceled){}

	/**
	 * 処理開始時に実施され、画面を反映させる。<br/>
	 * JavaFXのアプリケーションスレッドから呼ばれる。
	 * @param current
	 * @param max
	 */
	protected void onStart(){}

	/**
	 * setProgressから起動され、イベントスレッドで動き、ユーザインターフェースのビジュアル的な進捗状況報告を更新する
	 * @param current
	 * @param max
	 */
	protected void onProgress(int current, int max){}


	@Override
	public final boolean cancel(boolean mayInterruptIfRunning) {
		return computation.cancel(mayInterruptIfRunning);
	}

	@Override
	public final boolean isCancelled() {
		return computation.isCancelled();
	}

	@Override
	public final boolean isDone() {
		return computation.isDone();
	}

	@Override
	public final V get() throws InterruptedException, ExecutionException {
		return computation.get();
	}

	@Override
	public final V get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException,
			TimeoutException {
		return computation.get(timeout, unit);
	}

	/**
	 * キャンセルイベントのハンドラを取得する<br/>
	 * キャンセルボタンのsetOnActionに設定する。
	 * @return キャンセルイベントのハンドラ
	 */
	protected final EventHandler<ActionEvent> getCancelEvent(){
		return event -> BackgroundTask.this.cancel(true);
	}
}
