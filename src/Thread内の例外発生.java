import org.junit.Test;


public class Thread内の例外発生 {

	@Test
	public void test(){

		new Thread(new ThrowExceptionInThread()).start();
	}


	private class ThrowExceptionInThread implements Runnable{

		@Override
		public void run() {
			throw new IllegalArgumentException("意図した例外発生");
		}
	}

}
