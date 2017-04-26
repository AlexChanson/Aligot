package editor;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

public class FxUtils {

    public static UnaryOperator<TextFormatter.Change> intFilter = t -> {

        if (t.isReplaced())
            if(t.getText().matches("[^0-9]"))
                t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));

        if (t.isAdded()) {
            if (t.getText().matches("[^0-9]"))
                t.setText("");

        }
        return t;
    };

    public static UnaryOperator<TextFormatter.Change> doubleFilter = t -> {
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

    static Dialog<String[]> getNewLevelDialog(){
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("New Level");
        dialog.setHeaderText("Enter a name, a description and the size of the map.");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField height = new TextField();
        height.setPromptText("1234");
        height.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));

        TextField width = new TextField();
        width.setPromptText("123");
        width.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));

        TextField name = new TextField();
        name.setPromptText("A level.");

        TextField description = new TextField();
        description.setPromptText("Yeah it's fun to play.");

        grid.add(new Label("Width: "), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height: "), 0, 1);
        grid.add(height, 1, 1);
        grid.add(new Label("Name: "), 0, 2);
        grid.add(name, 1, 2);
        grid.add(new Label("Description: "), 0, 3);
        grid.add(description, 1, 3);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(width::requestFocus);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new String[]{name.getText(), description.getText(), width.getText(), height.getText()};
            }
            return null;
        });

        return dialog;
    }

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

    public static void drawCursor(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.RED);
        gc.strokeLine(x-10,y,x+10,y);
        gc.strokeLine(x,y-10,x,y+10);
    }

    public static void drawTarget(GraphicsContext gc, double x, double y) {
        gc.setFill(Color.ORANGERED);
        gc.fillOval(x-8, y-8, 16, 16);
        gc.setStroke(Color.ORANGERED);
        gc.strokeOval(x-15, y-15,30, 30);
        gc.setStroke(Color.YELLOW);
        gc.strokeLine(x-20,y,x+20,y);
        gc.strokeLine(x,y-20,x,y+20);
    }
}
