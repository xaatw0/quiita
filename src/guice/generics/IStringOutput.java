package guice.generics;

public interface IStringOutput<T> {

	public abstract String converter(T t);

}