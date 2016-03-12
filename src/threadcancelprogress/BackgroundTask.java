package threadcancelprogress;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javafx.application.Platform;

public abstract class BackgroundTask<V> implements Runnable, Future<V> {

	private final FutureTask<V> computation = new Computation();

	private class Computation extends FutureTask<V>{

		public Computation(){
			super(new Callable<V>() {
				@Override
				public V call() throws Exception {

					return BackgroundTask.this.compute();
				}
			});
		}

		protected final void done(){
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					V value = null;
					Throwable thrown = null;
					boolean canceled = false;

					try{
						value = get();

					}catch(ExecutionException ex){
						thrown = ex.getCause();
					}catch(CancellationException ex){
						canceled = true;
					}catch(InterruptedException ex){

					}finally{
						onCompletion(value, thrown, canceled);
					}
				}
			});
		}

	}

	@Override
	public void run() {
		// TODO 自動生成されたメソッド・スタブ

	}

	/**
	 * computeメソッドから呼び出し、進捗状況を数量的に表示する
	 * @param current
	 * @param max
	 */
	protected void setProgress(final int current, final int max){

		// GuiExecutor.instance().execute(new Runnable(){
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				onProgress(current, max);
			}
		});
	}

	// バックグラウンドスレッドで呼ばれる
	protected abstract V compute();

	// イベントスレッドスレッドで呼ばれる
	protected void onCompletion(V result, Throwable exception, boolean canceled){}

	/**
	 * setProgressから起動され、イベントスレッドで動き、ユーザインターフェースのビジュアル的な進捗状況報告を更新する
	 * @param current
	 * @param max
	 */
	protected void onProgress(int current, int max){}


	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return computation.cancel(mayInterruptIfRunning);
	}

	@Override
	public boolean isCancelled() {
		return computation.isCancelled();
	}

	@Override
	public boolean isDone() {
		return computation.isDone();
	}

	@Override
	public V get() throws InterruptedException, ExecutionException {
		return computation.get();
	}

	@Override
	public V get(long timeout, TimeUnit unit)
			throws InterruptedException, ExecutionException,
			TimeoutException {
		return computation.get(timeout, unit);
	}
}
