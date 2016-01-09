package fxbind;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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
	public void IntegerProperty(){

	       IntegerProperty num1 = new SimpleIntegerProperty(1);
	       IntegerProperty num2 = new SimpleIntegerProperty(2);

	       IntegerProperty sum = new SimpleIntegerProperty();
	       sum.bind(Bindings.add(num1,num2));

	       assertThat(sum.getValue(), is(3));

	       num1.setValue(2);
	       assertThat(sum.getValue(), is(4));
	}

	@Test
	public void IntegerProperty_同じ値にバインド(){

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
	public void bind(){
	       IntegerProperty num1 = new SimpleIntegerProperty(1);
	       IntegerProperty num2 = new SimpleIntegerProperty(2);
	       num2.bind(num1);

	       num1.set(1);

	       try{
	    	   // num2はnum1にバインドされているため、num2に設定すると例外が発生する
	    	   // java.lang.RuntimeException: A bound value cannot be set.

	    	   num2.set(2);
	    	   fail();
	       }catch(RuntimeException e){}
	}

	@Test
	public void bindBidirectional(){
	       IntegerProperty num1 = new SimpleIntegerProperty(1);
	       IntegerProperty num2 = new SimpleIntegerProperty(2);
	       num2.bindBidirectional(num1);

	       // bindBidirectionalでバインドすると、num1,num2どちらにも設定できる
	       num1.set(1);
	       assertThat(num1.getValue(), is(1));
	       assertThat(num2.getValue(), is(1));

	       num2.set(2);
	       assertThat(num1.getValue(), is(2));
	       assertThat(num2.getValue(), is(2));
	}

	@Test
	public void IntegerProperty_計算(){

	       IntegerProperty base = new SimpleIntegerProperty(10);

	       IntegerProperty add = new SimpleIntegerProperty();
	       IntegerProperty substract = new SimpleIntegerProperty();
	       IntegerProperty multiply = new SimpleIntegerProperty();
	       IntegerProperty divide = new SimpleIntegerProperty();
	       IntegerProperty negate = new SimpleIntegerProperty();

	       add.bind(base.add(10));
	       substract.bind(base.subtract(10));
	       multiply.bind(base.multiply(10));
	       divide.bind(base.divide(10));
	       negate.bind(base.negate());

	       assertThat(add.get(), is(20));
	       assertThat(substract.get(), is(0));
	       assertThat(multiply.get(), is(100));
	       assertThat(divide.get(), is(1));
	       assertThat(negate.get(), is(-10));

	       base.set(5);
	       assertThat(add.get(), is(15));
	       assertThat(substract.get(), is(-5));
	       assertThat(multiply.get(), is(50));
	       assertThat(divide.get(), is(0));
	       assertThat(negate.get(), is(-5));
	}

	@Test
	public void IntegerProperty_比較(){

		IntegerProperty base = new SimpleIntegerProperty(10);

		BooleanProperty isEqualTo = new SimpleBooleanProperty();
		BooleanProperty greaterThan = new SimpleBooleanProperty();
		BooleanProperty lessThan = new SimpleBooleanProperty();
		BooleanProperty greaterThanOrEqualTo = new SimpleBooleanProperty();
		BooleanProperty lessThanOrEqualTo = new SimpleBooleanProperty();

		isEqualTo.bind(base.isEqualTo(10));
		greaterThan.bind(base.greaterThan(10));
		lessThan.bind(base.lessThan(10));
		greaterThanOrEqualTo.bind(base.greaterThanOrEqualTo(10));
		lessThanOrEqualTo.bind(base.lessThanOrEqualTo(10));

		assertThat(isEqualTo.get(), is(true));
		assertThat(greaterThan.get(), is(false));
		assertThat(lessThan.get(),is(false));
		assertThat(greaterThanOrEqualTo.get(), is(true));
		assertThat(lessThanOrEqualTo.get(), is(true));
	}

	@Test
	public void IntegerProperty_台形の面積(){
		// 上底a、下底b、高さh →面積S (a+b) * h /2
		IntegerProperty a = new SimpleIntegerProperty();
		IntegerProperty b = new SimpleIntegerProperty();
		IntegerProperty h = new SimpleIntegerProperty();

		DoubleProperty S = new SimpleDoubleProperty();
		S.bind(a.add(b).multiply(h).divide(2));

		a.set(2);
		b.set(3);
		h.set(4);
		assertThat(S.get(),is(10D));

		a.set(4);
		assertThat(S.get(),is(14D));
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

	@Test
	public void BooleanBindWhenThenOtherwise(){

		// 分子が4，分母が2
		IntegerProperty numerator = new SimpleIntegerProperty(4);
		IntegerProperty denominato = new SimpleIntegerProperty(2);

		// 分母が0でないなら、分子を分母で割った値、
		// 上記以外の場合、0を返す。
		NumberBinding value =
			new When(denominato.isNotEqualTo(0))
			.then(numerator.divide(denominato))
			.otherwise(0);

		assertThat(value.getValue(),is(2));

		numerator.set(7);
		assertThat(value.getValue(),is(3));

		denominato.set(0);
		assertThat(value.getValue(), is(0));
	}

	@Test
	public void StringBind(){
		StringProperty concat = new SimpleStringProperty();
		BooleanProperty isEqualTo = new SimpleBooleanProperty();
		BooleanProperty isEqualToIgnoreCase = new SimpleBooleanProperty();
		BooleanProperty isNotEqualTo = new SimpleBooleanProperty();
		BooleanProperty isNotEqualToIgnoreCase = new SimpleBooleanProperty();
		BooleanProperty isEmpty = new SimpleBooleanProperty();
		BooleanProperty isNotEmpty = new SimpleBooleanProperty();
		BooleanProperty isNull = new SimpleBooleanProperty();

		StringProperty target = new SimpleStringProperty("A");
		concat.bind(target.concat("!"));
		isEqualTo.bind(target.isEqualTo("A"));
		isEqualToIgnoreCase.bind(target.isEqualToIgnoreCase("A"));
		isNotEqualTo.bind(target.isNotEqualTo("A"));
		isNotEqualToIgnoreCase.bind(target.isNotEqualToIgnoreCase("A"));
		isEmpty.bind(target.isEmpty());
		isNotEmpty.bind(target.isNotEmpty());
		isNull.bind(target.isNull());

		assertThat(concat.get(), is("A!"));
		assertThat(isEqualTo.get(),is(true));
		assertThat(isEqualToIgnoreCase.get(),is(true));
		assertThat(isNotEqualTo.get(),is(false));
		assertThat(isNotEqualToIgnoreCase.get(),is(false));
		assertThat(isEmpty.get(), is(false));
		assertThat(isNotEmpty.get(), is(true));
		assertThat(isNull.get(), is(false));

		target.set("a");
		assertThat(concat.get(), is("a!"));
		assertThat(isEqualTo.get(),is(false));
		assertThat(isEqualToIgnoreCase.get(),is(true));
		assertThat(isNotEqualTo.get(),is(true));
		assertThat(isNotEqualToIgnoreCase.get(),is(false));
		assertThat(isEmpty.get(), is(false));
		assertThat(isNotEmpty.get(), is(true));
		assertThat(isNull.get(), is(false));

		target.set("");
		assertThat(concat.get(), is("!"));
		assertThat(isEqualTo.get(),is(false));
		assertThat(isEqualToIgnoreCase.get(),is(false));
		assertThat(isNotEqualTo.get(),is(true));
		assertThat(isNotEqualToIgnoreCase.get(),is(true));
		assertThat(isEmpty.get(), is(true));
		assertThat(isNotEmpty.get(), is(false));
		assertThat(isNull.get(), is(false));

		target.set(null);
		assertThat(concat.get(), is("null!"));
		assertThat(isEqualTo.get(),is(false));
		assertThat(isEqualToIgnoreCase.get(),is(false));
		assertThat(isNotEqualTo.get(),is(true));
		assertThat(isNotEqualToIgnoreCase.get(),is(true));
		assertThat(isEmpty.get(), is(true));
		assertThat(isNotEmpty.get(), is(false));
		assertThat(isNull.get(), is(true));
	}
}
