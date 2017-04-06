package editor;

import com.google.gson.Gson;
import core.Level;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import physics.RigidBody;
import java.io.File;
import java.util.Scanner;

public class MainWindowCtl {

    @FXML
    private BorderPane borderPane;
    private Pane wrapperPane;
    private Canvas graph;
    private Level currentLevel = null;

    public void initialize(){
        wrapperPane = new Pane();
        wrapperPane.setStyle("-fx-background-color: black");
        borderPane.setCenter(wrapperPane);
        graph = new Canvas();
        wrapperPane.getChildren().add(graph);

        graph.widthProperty().bind(wrapperPane.widthProperty());
        graph.heightProperty().bind(wrapperPane.heightProperty());

        graph.widthProperty().addListener(event -> drawLevel(graph));
        graph.heightProperty().addListener(event -> drawLevel(graph));
        graph.setOnMouseClicked(this::mouseHandler);
        drawLevel(graph);

    }

    private void mouseHandler(MouseEvent event){
        System.out.println("Click ! x:" + event.getX() + " y:" + event.getY());
    }

    @FXML
    private void openFileDialog(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Open a level file");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Level File", "*.lvl"));
        File toOpen = fc.showOpenDialog(new Stage());
        Gson gson = new Gson();
        String content = "";
        try {
            content = new Scanner(toOpen).useDelimiter("\\Z").next();
        } catch (Exception e) {
            System.out.println("Error opening file !");
        }
        try{
            currentLevel = gson.fromJson(content, Level.class);
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("The file is corrupted or it's format is incorrect !");

            alert.showAndWait();
        }
        drawLevel(graph);
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
