package rxjava;

import rx.Observable;
import rx.functions.Action1;

public class HelloRxJava {

	public static void main(String[] args) {
		new HelloRxJava().hello("Ben", "George");
	}

	public void hello(String... names){
		Observable.from(names).subscribe(new Action1<String>() {

	        @Override
	        public void call(String s) {
	            System.out.println("Hello " + s + "!");
	        }
	    });
	}
}
