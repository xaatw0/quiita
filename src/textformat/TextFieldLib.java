package textformat;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import javafx.beans.property.StringProperty;

public class TextFieldLib {

	private static final Predicate<String> isNumber = Pattern.compile("^\\d$").asPredicate();

	public static void setNumberFormat(StringProperty value){

		value.addListener( (observable, oldValue, newValue) -> {

			StringProperty property = (StringProperty) observable;

		    if (newValue == null) {
		    		property.set("");
		    } else {

		          if (property.get() == null || "".equals(property.get())) {
		            // no action required, text property is already blank, we don't need to set it to 0.
		          } else {
		            property.set(newValue.toString());
		          }
		        }
		});

	}

}
