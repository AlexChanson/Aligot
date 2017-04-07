package editor;

import com.google.gson.Gson;
import core.Level;
import core.Planet;
import generator.LevelGen;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import physics.RigidBody;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MainWindowCtl {

    @FXML
    private BorderPane borderPane;
    @FXML
    private TextField xCoordinate, yCoordinate, planetMass, planetRadius, planetTexture;
    @FXML
    private CheckBox isSpawnBox;
    @FXML
    private ChoiceBox<String> planetType;
    private Canvas graph;
    private Level currentLevel = null;
    private IntegerProperty cursorX = new SimpleIntegerProperty(0);
    private IntegerProperty cursorY = new SimpleIntegerProperty(0);

    public void initialize(){
        //Setting up the canvas in the center of the border pane
        Pane wrapperPane = new Pane();
        wrapperPane.setStyle("-fx-background-color: black");
        borderPane.setCenter(wrapperPane);
        graph = new Canvas();
        wrapperPane.getChildren().add(graph);
        //setting the bindings to resize
        graph.widthProperty().bind(wrapperPane.widthProperty());
        graph.heightProperty().bind(wrapperPane.heightProperty());
        //setting the canvas event handlers
        graph.widthProperty().addListener(event -> drawLevel(graph));
        graph.heightProperty().addListener(event -> drawLevel(graph));
        graph.setOnMouseClicked(this::mouseHandler);
        //Initializing the coordinates
        xCoordinate.setText(Integer.toString(0));
        yCoordinate.setText(Integer.toString(0));
        //Setting the text filters
        xCoordinate.setTextFormatter(new TextFormatter<>(TextUtils.filter));
        yCoordinate.setTextFormatter(new TextFormatter<>(TextUtils.filter));
        //Set the bindings for the coordinates and the cursor
        Bindings.bindBidirectional(xCoordinate.textProperty(), cursorX, TextUtils.converter);
        Bindings.bindBidirectional(yCoordinate.textProperty(), cursorY, TextUtils.converter);
        //set listeners to move the cursor if the property is modified
        cursorX.addListener(observable -> drawLevel(graph));
        cursorY.addListener(observable -> drawLevel(graph));

        drawLevel(graph);
    }

    private void mouseHandler(MouseEvent event){
        if(currentLevel != null) {
            cursorX.setValue((int) (event.getX() / (graph.getWidth() / currentLevel.getMapSize()[0])));
            cursorY.setValue((int) (event.getY() / (graph.getHeight() / currentLevel.getMapSize()[1])));
        } else {
            cursorX.setValue(event.getX());
            cursorY.setValue(event.getY());
        }
        drawLevel(graph);
    }

    @FXML
    private void newFileDialog(){
        //TODO: un dialog pour creer un nouveau niveau avec des entrees utilisateurs: nom, info, taille de la map
        //tuto ici http://code.makery.ch/blog/javafx-dialogs-official/
        //Attention prevenir l'utilisateur que le niveau en cours sera detruit si pas sauvegardé
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

    @FXML
    private void saveFileDialog(){
        FileChooser fc = new FileChooser();
        fc.setTitle("Save a level file");
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Level File", "*.lvl"));
        File toOpen = fc.showSaveDialog(new Stage());
        if(toOpen != null){
            try {
                toOpen.delete();
                toOpen.createNewFile();
                FileWriter fw = new FileWriter(toOpen);
                Gson gson = new Gson();
                System.out.println(gson.toJson(currentLevel));
                fw.write(gson.toJson(currentLevel));
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("The file couldn't be saved !");

                alert.showAndWait();
            }
        }
    }

    @FXML
    private void menuDelete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete level");
        alert.setContentText("Are you sure tou want to discard the current level ?");

        Optional<ButtonType> result = alert.showAndWait();

        if (Objects.equals(result.get(), ButtonType.OK))
            currentLevel = null;

        drawLevel(graph);
    }

    @FXML
    private void menuGenerate(){
        List<String> choices = new ArrayList<>();
        choices.add("Small");
        choices.add("Medium");
        choices.add("Large");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Medium", choices);
        dialog.setTitle("Generate level");
        dialog.setHeaderText("Create a level using the built-in procedural generator.");
        dialog.setContentText("Map Size:");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(size -> {
            int[] mapSize = LevelGen.SMALL;
            switch (size){
                case "Medium":
                    mapSize = LevelGen.MEDIUM;
                case "Large":
                    mapSize = LevelGen.LARGE;
            }
            LevelGen gen = new LevelGen(new Random().nextLong(), mapSize);
            currentLevel = gen.getLevel();
        });
        drawLevel(graph);
    }

    private void drawLevel(Canvas canvas){
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        if(currentLevel != null) {
            double widthRatio = width / currentLevel.getMapSize()[0];
            double heightRatio = height / currentLevel.getMapSize()[1];

            currentLevel.getPlanets().forEach(planet -> {
                RigidBody temp = planet.getRigidBody();
                if (planet.isSpawn())
                    gc.setFill(Color.RED);
                else
                    gc.setFill(Color.GREEN);
                gc.fillOval(temp.getPosition().getX() * widthRatio,
                        temp.getPosition().getY() * heightRatio,
                        Math.max(widthRatio, heightRatio) * temp.getSize(),
                        Math.max(widthRatio, heightRatio) * temp.getSize());


            });
            //TODO: Desiner une croix a la place du point moche
            gc.setFill(Color.valueOf("#ffff00"));
            gc.fillOval(cursorX.getValue()*widthRatio, cursorY.getValue()*heightRatio, 8, 8);
        }else{
            //TODO: Desiner une croix a la place du point moche
            gc.setFill(Color.valueOf("#ffff00"));
            gc.fillOval(cursorX.getValue(), cursorY.getValue(), 8, 8);
        }
    }

    private Planet nearestToCursor(){
        //TODO retourne la planet la plus proche du curseur null si aucun lvl chargé
        return null;
    }

    private boolean isCursorOn(Planet p){
        //TODO: retourne true si le curseur (seulment le point central) est sur la planete p
        return false;
    }
}
