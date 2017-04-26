package editor;

import com.google.gson.Gson;
import core.Level;
import core.Planet;
import core.LevelGen;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import physics.RigidBody;
import physics.Vector2D;

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
    private IntegerProperty cursorX = new SimpleIntegerProperty(0);
    private IntegerProperty cursorY = new SimpleIntegerProperty(0);
    private ObjectProperty<Level> currentLevel = new SimpleObjectProperty<>(null);
    private ObjectProperty<Planet> currentPlanet = new SimpleObjectProperty<>(null);

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
        //TODO add listeners for drag and drop stuff and things
        //choice box init
        planetType.getItems().addAll("rock", "gas", "black_hole", "star");
        //Initializing the coordinates
        xCoordinate.setText(Integer.toString(0));
        yCoordinate.setText(Integer.toString(0));
        //Setting the text filters
        xCoordinate.setTextFormatter(new TextFormatter<>(FxUtils.filter));
        yCoordinate.setTextFormatter(new TextFormatter<>(FxUtils.filter));
        planetRadius.setTextFormatter(new TextFormatter<>(FxUtils.filter));
        planetMass.setTextFormatter(new TextFormatter<>(FxUtils.doubleFilter));
        //Set the bindings for the coordinates and the cursor
        Bindings.bindBidirectional(xCoordinate.textProperty(), cursorX, FxUtils.converter);
        Bindings.bindBidirectional(yCoordinate.textProperty(), cursorY, FxUtils.converter);
        //set listeners to move the cursor if the property is modified
        cursorX.addListener(observable -> drawLevel(graph));
        cursorY.addListener(observable -> drawLevel(graph));
        //Dynamic adjustments to planets
        currentLevel.addListener((observable, oldValue, newValue) -> {
            if(newValue == null)
                currentPlanet.set(null);
        });
        currentPlanet.addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                isSpawnBox.setSelected(newValue.isSpawn());
                planetType.setValue(currentPlanet.get().getType());
                planetTexture.setText(currentPlanet.get().getTexture());
                planetRadius.setText(String.valueOf((int)currentPlanet.get().getRigidBody().getRadius()));
                planetMass.setText(Double.toString(currentPlanet.get().getRigidBody().getMass()));
            }else{
                isSpawnBox.setSelected(false);
                planetType.setValue(null);
                planetMass.setText(null);
                planetRadius.setText(null);
                planetTexture.setText(null);
            }
        });
        isSpawnBox.selectedProperty().addListener(observable -> {
            if(currentPlanet.get() != null){
                if(currentPlanet.get().isSpawn() != isSpawnBox.isSelected()){
                    currentPlanet.get().setSpawn(isSpawnBox.isSelected());
                    drawLevel(graph);
                }
            }
        });
        planetTexture.textProperty().addListener(observable -> {
            if(currentPlanet.get() != null)
                currentPlanet.get().setTexture(planetTexture.getText());
        });
        planetRadius.textProperty().addListener(observable -> {
            if(currentPlanet.get() != null && !planetRadius.getText().equals("")){
                currentPlanet.get().getRigidBody().setRadius(Integer.parseInt(planetRadius.getText()));
                drawLevel(graph);
            }
        });
        planetMass.textProperty().addListener(observable -> {
            if(currentPlanet.get() != null)
                currentPlanet.get().getRigidBody().setMass(Double.parseDouble(planetMass.getText()));
        });
        planetType.valueProperty().addListener(observable -> {
            if(currentPlanet.get() != null)
                currentPlanet.get().setType(planetType.getValue());
        });
        drawLevel(graph);
    }

    private void mouseHandler(MouseEvent event){
        if(currentLevel.get() != null) {
            cursorX.setValue((int) (event.getX() / (graph.getWidth() / currentLevel.get().getMapSize()[0])));
            cursorY.setValue((int) (event.getY() / (graph.getHeight() / currentLevel.get().getMapSize()[1])));
            Planet nearest = nearestToCursor();
            if (nearest != null && isCursorOn(nearest)){
                currentPlanet.set(nearest);
            }else
                currentPlanet.set(null);
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
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Level File", "*.lvl", "*.json"));
        File toOpen = fc.showOpenDialog(new Stage());
        Gson gson = new Gson();
        String content = "";
        try {
            content = new Scanner(toOpen).useDelimiter("\\Z").next();
        } catch (Exception e) {
            System.out.println("Error opening file !");
        }
        try{
            currentLevel.set(gson.fromJson(content, Level.class));
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
                fw.write(gson.toJson(currentLevel.get()));
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
            currentLevel.set(null);

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
            currentLevel.set(gen.getLevel());
        });
        drawLevel(graph);
    }

    private void drawLevel(Canvas canvas){
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.clearRect(0, 0, width, height);

        if(currentLevel.get() != null) {
            double widthRatio = width / currentLevel.get().getMapSize()[0];
            double heightRatio = height / currentLevel.get().getMapSize()[1];

            currentLevel.get().getPlanets().forEach(planet -> {
                RigidBody temp = planet.getRigidBody();
                if (planet.isSpawn())
                    gc.setFill(Color.RED);
                else
                    gc.setFill(Color.GREEN);
                gc.fillOval(
                        (temp.getPosition().getX() * widthRatio) - widthRatio * temp.getRadius(),
                        (temp.getPosition().getY() * heightRatio) - heightRatio * temp.getRadius(),
                        widthRatio * temp.getRadius() * 2,
                        heightRatio * temp.getRadius() * 2);


            });
            FxUtils.drawCursor(gc, cursorX.getValue()*widthRatio, cursorY.getValue()*heightRatio);
        }else
            FxUtils.drawCursor(gc, cursorX.getValue(), cursorY.getValue());

    }

    private Planet nearestToCursor(){
        Planet nearest = null;
        if (currentLevel.get() != null && currentLevel.get().getPlanets().size() != 0){
            double min = Double.MAX_VALUE;
            Vector2D cursor = new Vector2D(cursorX.getValue(), cursorY.getValue());
            for (Planet p :
                    currentLevel.get().getPlanets()) {
                double pToCursor = p.getRigidBody().getPosition().distanceTo(cursor);
                if(pToCursor < min){
                       nearest = p;
                       min = pToCursor;
                   }
            }
        }
        return nearest;
    }

    private boolean isCursorOn(Planet p){
        Vector2D cursor = new Vector2D(cursorX.getValue(), cursorY.getValue());
        return cursor.distanceTo(p.getRigidBody().getPosition()) <= p.getRigidBody().getRadius();
    }

    @FXML
    private void deletePlanet(){
        if(currentPlanet.get() != null){
            currentLevel.get().getPlanets().remove(currentPlanet.get());
            currentPlanet.set(null);
            drawLevel(graph);
        }
    }

    @FXML
    private void addPlanet(){
        if (currentLevel.get() != null && planetRadius.getText() != null && planetTexture.getText() != null && planetMass.getText() != null && planetType.getValue() != null){
            if(cursorX.get() >= 0 && cursorX.get() <= currentLevel.get().getMapSize()[0] && cursorY.get() >= 0 && cursorY.get() <= currentLevel.get().getMapSize()[1]){
                boolean overlap = false;
                Vector2D cursorPos = new Vector2D(cursorX.get(), cursorY.get());
                for (Planet planet : currentLevel.get().getPlanets()) {
                    if (planet.getRigidBody().getRadius() > cursorPos.distanceTo(currentPlanet.get().getRigidBody().getPosition()))
                        overlap = true;
                }
                if(!overlap){
                    try {
                        double size = Double.parseDouble(planetRadius.getText());
                        double mass = Double.parseDouble(planetMass.getText());
                        Planet p = new Planet(new RigidBody(new Vector2D(cursorX.get(), cursorY.get()), size, mass), planetTexture.getText(), planetType.getValue());
                        currentLevel.get().getPlanets().add(p);
                        drawLevel(graph);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
