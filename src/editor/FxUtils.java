package editor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

class FxUtils {

    static UnaryOperator<TextFormatter.Change> filter = t -> {

        if (t.isReplaced())
            if(t.getText().matches("[^0-9]"))
                t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

        if (t.isAdded()) {
            if (t.getText().matches("[^0-9]"))
                t.setText("");

        }
        return t;
    };

    static UnaryOperator<TextFormatter.Change> doubleFilter = t -> {
        if (t.isReplaced())
            if(t.getText().matches("[^0-9eE.]"))
                t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

        if (t.isAdded())
        {
            if (t.getControlText().contains(".") && !(t.getControlText().contains("e") || t.getControlText().contains("E"))) {
                if (t.getText().matches("[^0-9eE]")) {
                    t.setText("");
                }
            }
            else if (t.getControlText().contains(".") && (t.getControlText().contains("e") || t.getControlText().contains("E"))) {
                if (t.getText().matches("[^0-9]")) {
                    t.setText("");
                }
            }
            else if (t.getControlText().contains("e") || t.getControlText().contains("E")){
                if (t.getText().matches("[^0-9.]")) {
                    t.setText("");
                }
            }
            else if (t.getText().matches("[^0-9.eE]")) {
                t.setText("");
            }
        }

        return t;
    };

    static StringConverter<Number> converter = new StringConverter<Number>() {
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

    static void drawCursor(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.YELLOW);
        gc.strokeLine(x-10,y,x+10,y);
        gc.strokeLine(x,y-10,x,y+10);
    }
}
