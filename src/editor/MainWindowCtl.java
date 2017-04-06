package editor;

import core.Level;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import physics.RigidBody;


public class MainWindowCtl {

    @FXML
    private BorderPane borderPane;
    private Pane wrapperPane;
    private Canvas graph;
    private Level currentLevel;

    public void initialize(){
        currentLevel = Load.level(System.getProperty("user.dir")+"\\ressources\\testLevel.json");

        wrapperPane = new Pane();
        wrapperPane.setStyle("-fx-background-color: black");
        borderPane.setCenter(wrapperPane);
        graph = new Canvas();
        wrapperPane.getChildren().add(graph);

        graph.widthProperty().bind(wrapperPane.widthProperty());
        graph.heightProperty().bind(wrapperPane.heightProperty());

        graph.widthProperty().addListener(event -> drawLevel(graph));
        graph.heightProperty().addListener(event -> drawLevel(graph));
        drawLevel(graph);

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

        gc.fillRect(50, 50, 20, 20);
    }

    private int drawLevel(Canvas canvas){
        if(currentLevel == null)
            return 1;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        double widthRatio = width/currentLevel.getMapSize()[0];
        double heightRatio = height/currentLevel.getMapSize()[1];

        gc.clearRect(0, 0, width, height);

        currentLevel.getPlanets().forEach(planet -> {
            RigidBody temp = planet.getRigidBody();
            if(planet.isSpawn())
                gc.setFill(Color.RED);
            else
                gc.setFill(Color.GREEN);
            gc.fillOval(temp.getPosition().getX()*widthRatio,
                    temp.getPosition().getY()*heightRatio,
                    Math.max(widthRatio, heightRatio)*temp.getSize(),
                    Math.max(widthRatio, heightRatio)*temp.getSize());


        });
        return 0;
    }
}
