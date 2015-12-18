package fxwindow;

public interface IPanel {

	public enum Result{
		OK,Cancel
	}

	Result getResult();

	String getText();

	String getFxml();
}
