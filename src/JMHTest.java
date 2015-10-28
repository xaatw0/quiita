import java.util.ArrayList;
import java.util.List;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JMHTest {
	   private static final int size = 100;

	    // ★1 ベンチマークメソッドにアノテーションをつける
	   @Benchmark
	    public void withInitialSize() {
	        List<Integer> sizedList = new ArrayList<>(size);
	        for (int i = 0; i < size; i++) {
	            sizedList.add(i);
	        }
	    }

	    @Benchmark
	    public void withoutInitialSize() {
	        List<Integer> defaultList = new ArrayList<>();
	        for (int i = 0; i < size; i++) {
	            defaultList.add(i);
	        }
	    }

	    public static void main(String[] args) throws RunnerException {
	        Options opt = new OptionsBuilder()
	                .include(JMHTest.class.getSimpleName())
	                .forks(1)
	                .build();

	        new Runner(opt).run();
	    }
}
