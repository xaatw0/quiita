package textformat;

import java.util.regex.Pattern;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RestrictedTextField extends javafx.scene.control.TextField {

    private IntegerProperty maxLength;
    public void setMaxLength(int value) { maxLengthProperty().set(value); }
    public int getMaxLength() { return maxLengthProperty().get(); }
    public IntegerProperty maxLengthProperty() {
        if (maxLength == null) maxLength = new SimpleIntegerProperty(this, "Maximum Length", -1);
        return maxLength;
    }

    private ObjectProperty<Pattern> verifier;
    public void setVerifier(Pattern value) { verifierProperty().set(value); }
    public Pattern getVerifier() { return verifierProperty().get(); }
    public ObjectProperty<Pattern> verifierProperty() {
        if (verifier == null) verifier = new SimpleObjectProperty<Pattern>(this, "Verifier");
        return verifier;
    }

    public static final Pattern NUMBER_ONLY;
    public static final Pattern ALPHABET_ONLY;
    static {
        NUMBER_ONLY     = Pattern.compile("[0-9]");
        ALPHABET_ONLY   = Pattern.compile("[a-z]", Pattern.CASE_INSENSITIVE);
    }

    @Override
    public void replaceText(int start, int end, String text) {
        // If the replaced text would end up being invalid, then simply
        // ignore this call!
        if (text.equals("")) {
            super.replaceText(start, end, text);
        } else {
            if (getMaxLength() > 0 && getLength() < getMaxLength()) {
                if (getVerifier().matcher(text).find()) {
                    super.replaceText(start, end, text);
                }
            }
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.equals("")) {
            super.replaceSelection(text);
        } else {
            if (getMaxLength() > 0 && getLength() < getMaxLength()) {
                if (getVerifier().matcher(text).find()) {
                    super.replaceSelection(text);
                }
            }
        }
    }
}

