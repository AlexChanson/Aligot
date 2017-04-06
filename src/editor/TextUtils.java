package editor;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

public class TextUtils {
    public static UnaryOperator<TextFormatter.Change> filter = t -> {

        if (t.isReplaced())
            if(t.getText().matches("[^0-9]"))
                t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

        if (t.isAdded()) {
            if (t.getText().matches("[^0-9]"))
                t.setText("");

        }
        return t;
    };

    public static StringConverter<Number> converter = new StringConverter<Number>() {
        @Override
        public Integer fromString(String s) {
            try{
                return Integer.parseInt(s);
            } catch (Exception e){
                return -1;
            }
        }

        @Override
        public String toString(Number i) {
            return i.toString();
        }
    };
}
