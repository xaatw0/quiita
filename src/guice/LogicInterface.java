package guice;

import com.google.inject.ImplementedBy;

@ImplementedBy(LogicImplement.class)
public interface LogicInterface {
	String add(int a, int b);
	String substract(int a, int b);
}
