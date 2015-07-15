import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

import org.junit.Test;


public class BindingTest {

	@Test
	public void BooleanBinding() throws Exception{

		BooleanProperty flg1 = new SimpleBooleanProperty();
		BooleanProperty flg2 = new SimpleBooleanProperty(true);

		assertThat(flg1.get(), is(false));
		assertThat(flg1.getValue(), is(false));
		assertThat(flg2.getValue(), is(true));

		assertThat(flg1.and(flg2).get(), is(false));
		assertThat(flg1.or(flg2).get(), is(true));
		assertThat(flg1.not().get(), is(true));

		assertThat(flg1.isEqualTo(flg2).get(), is(false));
		assertThat(flg1.isNotEqualTo(flg2).get(), is(true));

		StringBinding result = flg1.asString();
		assertThat(result.get(), is("false"));

		flg1.set(true);
		assertThat(result.get(), is("true"));
	}

	@Test
	public void NumberBinding() throws Exception {

		IntegerProperty x1 = new SimpleIntegerProperty(10);
		IntegerProperty x2 = new SimpleIntegerProperty(3);

		assertThat(x1.add(x2).getValue(), is(13));
		assertThat(x1.subtract(x2).getValue(), is(7));
		assertThat(x1.multiply(x2).getValue(), is(30));
		assertThat(x1.divide(x2).getValue(), is(3));

		NumberBinding result = x1.multiply(x2);
		assertThat(result.getValue(), is(30));

		x1.set(20);
		assertThat(result.getValue(), is(60));

		StringExpression expression = Bindings.format("x1:%d x2:%d x1*x2:%d",x1,x2,result);
		assertThat(expression.get(), is("x1:20 x2:3 x1*x2:60"));

		x2.set(4);
		assertThat(expression.get(), is("x1:20 x2:4 x1*x2:80"));
	}

	@Test
	public void Formula() throws Exception {

		DoubleProperty a = new SimpleDoubleProperty();
		DoubleProperty b = new SimpleDoubleProperty();
		DoubleProperty c = new SimpleDoubleProperty();

		DoubleBinding s = a.add(b).add(c).divide(2.0D);

		DoubleBinding result =
				new When(
					a.add(b).greaterThan(c).
					and(b.add(c).greaterThan(a).
					and(c.add(a).greaterThan(b)))).
				then(
						s.multiply(s.subtract(a)).
						multiply(s.subtract(b)).
						multiply(s.subtract(c))
				).otherwise(0.0D);

		a.set(3);
		b.set(4);
		c.set(5);
		assertThat(Math.sqrt(result.get()), is(6.0D));

		a.set(2);
		b.set(2);
		c.set(2);
		assertThat(result.get(), is(3.0D));

		a.set(10);
		assertThat(result.get(), is(0.0D));
	}


	@Test
	public void FormulaDirectExtension() throws Exception {

		DoubleProperty a = new SimpleDoubleProperty();
		DoubleProperty b = new SimpleDoubleProperty();
		DoubleProperty c = new SimpleDoubleProperty();

		DoubleBinding s = a.add(b).add(c).divide(2.0D);

		DoubleBinding result =new DoubleBinding() {

			{
				super.bind(a,b,c);
			}

			@Override
			protected double computeValue() {

				double a0 = a.get();
				double b0 = b.get();
				double c0 = c.get();

				if ((a0+b0 > c0) && (b0 + c0 > a0) &&(c0+a0 > b0)){

					double s = (a0 + b0 + c0)/2.0D;
					return (s*(s-a0)*(s-b0)*(s-c0));
				} else {
					return 0.0D;
				}
			}
		};

		a.set(3);
		b.set(4);
		c.set(5);
		assertThat(Math.sqrt(result.get()), is(6.0D));

		a.set(2);
		b.set(2);
		c.set(2);
		assertThat(result.get(), is(3.0D));

		a.set(10);
		assertThat(result.get(), is(0.0D));
	}
}
