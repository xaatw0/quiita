package guice;

public class LogicDummy implements LogicInterface {

	@Override
	public String add(int a, int b) {
		return "(" + a + "足す" + b + ")";
	}

	@Override
	public String substract(int a, int b) {
		return "(" + a + "引く" + b + ")";
	}

}
