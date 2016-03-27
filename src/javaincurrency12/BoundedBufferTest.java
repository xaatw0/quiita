package javaincurrency12;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class BoundedBufferTest {


	@Test
	public void isEmptyWhenConstructed(){
		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		assertThat( bb.isEmpty(), is(true));
		assertThat( bb.isFull(), is(false));
	}

	@Test
	public void isFullAfterPuts() throws InterruptedException{

		BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		for (int i=0;i<10;i++){
			bb.put(i);
		}

		assertThat( bb.isEmpty(), is(false));
		assertThat( bb.isFull(), is(true));
	}

	@Test
	public void takeBlocksWhenEmpty(){

		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		Thread taker = new Thread(){
			@Override
			public void run(){
				try{
					int unused = bb.take();
					fail();
				} catch(InterruptedException e){}
			}
		};

		try{
			taker.start();
			assertThat(taker.isAlive(), is(true));

			Thread.sleep(1000);
			assertThat(taker.isAlive(), is(true));

			taker.interrupt();
			taker.join(1000);
			assertThat(taker.isAlive(), is(false));
		}catch(InterruptedException ex){
			fail();
		}
	}
}
