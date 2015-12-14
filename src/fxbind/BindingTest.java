package fxbind;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.junit.Test;

public class BindingTest {

	@Test
	public void NumberBinding(){
	       IntegerProperty num1 = new SimpleIntegerProperty(1);
	       IntegerProperty num2 = new SimpleIntegerProperty(2);

	       NumberBinding sum = Bindings.add(num1,num2);
	       assertThat(sum.getValue(), is(3));

	       num1.setValue(2);
	       assertThat(sum.getValue(), is(4));
	}

	@Test
	public void IntegerBinding(){

	       IntegerProperty num1 = new SimpleIntegerProperty(1);
	       IntegerProperty num2 = new SimpleIntegerProperty(2);

	       IntegerProperty sum = new SimpleIntegerProperty();
	       sum.bind(Bindings.add(num1,num2));

	       assertThat(sum.getValue(), is(3));

	       num1.setValue(2);
	       assertThat(sum.getValue(), is(4));
	}

	@Test
	public void IntegerBinding_same(){

	       IntegerProperty num1 = new SimpleIntegerProperty(1);
	       IntegerProperty num2 = new SimpleIntegerProperty(2);

	       num2.bind(num1);

	       assertThat(num1.getValue(), is(1));
	       assertThat(num2.getValue(), is(1));

	       num1.setValue(2);
	       assertThat(num1.getValue(), is(2));
	       assertThat(num2.getValue(), is(2));
	}

	@Test
	public void IntegerBinding_add(){

	       IntegerProperty num1 = new SimpleIntegerProperty(1);
	       IntegerProperty num2 = new SimpleIntegerProperty(2);

	       num2.bind(num1.add(10));

	       assertThat(num1.getValue(), is(1));
	       assertThat(num2.getValue(), is(11));

	       num1.setValue(2);
	       assertThat(num1.getValue(), is(2));
	       assertThat(num2.getValue(), is(12));
	}

	@Test
	public void IntegerBinding_calc(){

	       IntegerProperty base = new SimpleIntegerProperty(10);

	       IntegerProperty add = new SimpleIntegerProperty();
	       IntegerProperty multiply = new SimpleIntegerProperty();
	       IntegerProperty divide = new SimpleIntegerProperty();

	       add.bind(base.add(-10));
	       multiply.bind(base.multiply(10));
	       divide.bind(base.divide(10));

	       assertThat(add.get(), is(0));
	       assertThat(multiply.get(), is(100));
	       assertThat(divide.get(), is(1));

	       base.set(5);
	       assertThat(add.get(), is(-5));
	       assertThat(multiply.get(), is(50));
	       assertThat(divide.get(), is(0));
	}

	@Test
	public void BooleanBind(){

	       IntegerProperty base = new SimpleIntegerProperty(10);

	       BooleanProperty blnEquqls10 = new SimpleBooleanProperty();
	       blnEquqls10.bind(base.isEqualTo(10));

	       BooleanProperty blnGreaterThan10 = new SimpleBooleanProperty();
	       blnGreaterThan10.bind(base.greaterThan(10));

	       assertThat(blnEquqls10.get(), is(true));
	       assertThat(blnGreaterThan10.get(), is(false));

	       base.set(11);
	       assertThat(blnEquqls10.get(), is(false));
	       assertThat(blnGreaterThan10.get(), is(true));
	}

	@Test
	public void BooleanBindAnd(){
		 BooleanProperty boolean1 = new SimpleBooleanProperty();
		 BooleanProperty boolean2 = new SimpleBooleanProperty();

		 BooleanProperty result = new SimpleBooleanProperty();
		 result.bind(boolean1.and(boolean2));

		 assertThat(result.get(), is(false));

		 boolean1.set(true);
		 assertThat(result.get(), is(false));

		 boolean2.set(true);
		 assertThat(result.get(), is(true));
	}

	@Test
	public void BooleanBindOr(){
		 BooleanProperty boolean1 = new SimpleBooleanProperty();
		 BooleanProperty boolean2 = new SimpleBooleanProperty();

		 BooleanProperty result = new SimpleBooleanProperty();
		 result.bind(boolean1.or(boolean2));

		 assertThat(result.get(), is(false));

		 boolean1.set(true);
		 assertThat(result.get(), is(true));

		 boolean2.set(true);
		 assertThat(result.get(), is(true));
	}

	@Test
	public void BooleanBindAndOr(){
		// 1 and (2 or 3)

		BooleanProperty boolean1 = new SimpleBooleanProperty();
		BooleanProperty boolean2 = new SimpleBooleanProperty();
		BooleanProperty boolean3 = new SimpleBooleanProperty();

		BooleanProperty result = new SimpleBooleanProperty();
		result.bind(boolean1.and(boolean2.or(boolean3)));

		assertThat(result.get(), is(false));

		boolean1.set(true);
		assertThat(result.get(), is(false));

		boolean2.set(true);
		assertThat(result.get(), is(true));

		boolean2.set(false);
		boolean3.set(true);
		assertThat(result.get(), is(true));

		boolean1.set(false);
		assertThat(result.get(), is(false));
	}
}
