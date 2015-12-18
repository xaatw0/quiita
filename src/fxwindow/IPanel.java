package fxwindow;

public interface IPanel {

	public enum Result{
		OK,Cancel
	}

	String getResult();

	String getFxml();
}
