package guice.generics;

public class StringOutputInteger implements IStringOutput<Integer>{

	@Override
	public String converter(Integer t) {

		return t.toString();
	}
}
