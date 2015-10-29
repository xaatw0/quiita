package guice.generics;

public class StringOutput<T> implements IStringOutput<T> {

	/* (非 Javadoc)
	 * @see guice.generics.IStringOutput#converter(T)
	 */
	@Override
	public String converter(T t) {
		return t.toString();
	}
}
