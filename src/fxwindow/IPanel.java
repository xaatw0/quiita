package fxwindow;

public interface IPanel<T> {

	public enum Result{
		OK,Cancel
	}

	Result getResult();

	String getText();

	String getFxml();

	T getData();
}
