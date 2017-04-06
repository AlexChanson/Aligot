package editor;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class MainWindowCtl {

    @FXML
    private BorderPane borderPane;
    private Pane wrapperPane;
    private Canvas graph;
    private GraphicsContext gc;

    public void initialize(){
        wrapperPane = new Pane();
        borderPane.setCenter(wrapperPane);
        graph = new Canvas();
        wrapperPane.getChildren().add(graph);

        // Bind the width/height property to the wrapper Pane
        graph.widthProperty().bind(wrapperPane.widthProperty());
        graph.heightProperty().bind(wrapperPane.heightProperty());
        // redraw when resized
        graph.widthProperty().addListener(event -> draw(graph));
        graph.heightProperty().addListener(event -> draw(graph));
        draw(graph);

        /*
        gc = graph.getGraphicsContext2D();
        gc.setFill(Color.valueOf("#ff4466"));
        gc.fillRect(0, 0, 1000, 1000);
        */
    }

    /**
     * Draw crossed red lines which each each end is at the corner of window,
     * and 4 blue circles whose each center is at the corner of the window,
     * so that make it possible to know where is the extent the Canvas draws
     */
    private void draw(Canvas canvas) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
        gc.setStroke(Color.RED);
        gc.strokeLine(0, 0, width, height);
        gc.strokeLine(0, height, width, 0);
        gc.setFill(Color.BLUE);
        gc.fillOval(-30, -30, 60, 60);
        gc.fillOval(-30 + width, -30, 60, 60);
        gc.fillOval(-30, -30 + height, 60, 60);
        gc.fillOval(-30 + width, -30 + height, 60, 60);
    }
}
