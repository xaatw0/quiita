package thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueTest {

	public static BlockingQueue<String> queue = new ArrayBlockingQueue<>(2);

	public void put(String value) throws InterruptedException{
		queue.put(value);
	}

	public String take() throws InterruptedException{
		return queue.take();
	}

	public static void main(String[] args) throws InterruptedException {

		long startTime = System.currentTimeMillis();

		BlockingQueueTest target = new BlockingQueueTest();

		new Thread(new WaitPut(5)).start();

		System.out.println("取得前:" + (System.currentTimeMillis() - startTime));
		String test = target.take();
		System.out.println("取得後:" + (System.currentTimeMillis() - startTime));
		System.out.println("取得値:" + test);

	}

	private static class WaitPut implements Runnable{

		private int second;

		public WaitPut(int second) {
			this.second = second;
		}

		@Override
		public void run() {

			try {
				Thread.sleep(second * 1000);
				queue.put("Thread: put after waiting for" + String.valueOf(second) + "seconds.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
