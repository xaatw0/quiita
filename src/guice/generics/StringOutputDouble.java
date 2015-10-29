package guice.generics;

public class StringOutputDouble implements IStringOutput<Double>{

	@Override
	public String converter(Double t) {
		return t.toString();
	}

}
