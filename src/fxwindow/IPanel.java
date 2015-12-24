package fxwindow;

public interface IPanel<T> {

	public enum Result{
		OK,Cancel
	}

	String getFxml();

	T getData();
}
