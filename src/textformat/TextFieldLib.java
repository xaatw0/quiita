package textformat;

import java.util.function.Predicate;
import java.util.regex.Pattern;

import javafx.beans.property.StringProperty;

public class TextFieldLib {

	private static final Predicate<String> isNumber = Pattern.compile("^\\d$").asPredicate();

	public static void setNumberFormat(StringProperty value){

		value.addListener( (observable, oldValue, newValue) -> {

			StringProperty property = (StringProperty) observable;

			if (newValue == null){
				property.setValue("");
				return;
			} else if (newValue.isEmpty()){
				return;
			}

			if(isNumber.test(newValue)){
				return;
			}

			property.setValue(oldValue == null ? "": oldValue);
		});

	}

}
