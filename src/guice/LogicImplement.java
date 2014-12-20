package guice;

public class LogicImplement implements LogicInterface {

	@Override
	public String add(int a, int b) {
		return String.valueOf(a + b);
	}

	@Override
	public String substract(int a, int b) {
		return String.valueOf(a - b);
	}

}
