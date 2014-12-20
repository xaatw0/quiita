package guice;

import com.google.inject.Inject;

public class Controller {

	@Inject
	private LogicInterface logic;

	public String add(int a, int b){
		return logic.add(a, b);
	}

	public String substract(int a, int b){
		return logic.substract(a, b);
	}
}
