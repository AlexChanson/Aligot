package editor;

import com.google.gson.Gson;
import core.Challenge;
import core.Level;
import core.Planet;
import core.LevelGen;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private TextField xCoordinate, yCoordinate, planetMass, planetRadius, planetTexture, bgTextureField;
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
        graph.widthProperty().addListener(event -> drawLevel());
        graph.heightProperty().addListener(event -> drawLevel());
        graph.setOnMouseClicked(this::mouseHandler);
        //choice box init
        planetType.getItems().addAll("rock", "gas", "black_hole", "star");
        //Initializing the coordinates
        xCoordinate.setText(Integer.toString(0));
        yCoordinate.setText(Integer.toString(0));
        //Setting the text filters
        xCoordinate.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));
        yCoordinate.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));
        planetRadius.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));
        planetMass.setTextFormatter(new TextFormatter<>(FxUtils.doubleFilter));
        //Set the bindings for the coordinates and the cursor
        Bindings.bindBidirectional(xCoordinate.textProperty(), cursorX, FxUtils.converter);
        Bindings.bindBidirectional(yCoordinate.textProperty(), cursorY, FxUtils.converter);
        //set listeners to move the cursor if the property is modified
        cursorX.addListener(observable -> drawLevel());
        cursorY.addListener(observable -> drawLevel());
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
                    drawLevel();
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
                drawLevel();
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
        bgTextureField.textProperty().addListener(observable -> {
            if (currentLevel.get() != null)
                currentLevel.get().setBgTexture(bgTextureField.getText());
        });
        borderPane.addEventHandler(KeyEvent.KEY_PRESSED, this::keyboardHandler);
        drawLevel();
    }

    private void mouseHandler(MouseEvent event){
        graph.requestFocus();
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
        drawLevel();
    }

    @FXML
    private void newFileDialog(){
        Dialog<String[]> dialog = FxUtils.getNewLevelDialog();
        Optional<String[]> result = dialog.showAndWait();
        result.ifPresent(infos -> currentLevel.set(new Level(infos[0], infos[1], "", new HashSet<>(), new int[]{Integer.parseInt(infos[2]), Integer.parseInt(infos[3])})));
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
        drawLevel();
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

        if (Objects.equals(result.get(), ButtonType.OK)) {
            currentPlanet.set(null);
            currentLevel.set(null);
        }

        drawLevel();
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
        drawLevel();
    }

    private void drawLevel(){
        double width = graph.getWidth();
        double height = graph.getHeight();
        GraphicsContext gc = graph.getGraphicsContext2D();

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
            if(currentLevel.get().getChallenge() != null){
                currentLevel.get().getChallenge().getTargets().forEach(target -> FxUtils.drawTarget(graph.getGraphicsContext2D(), target[0]*widthRatio, target[1]*heightRatio));
            }
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
            drawLevel();
        }
    }

    @FXML
    private void addPlanet(){
        if (currentLevel.get() != null && planetRadius.getText() != null && planetTexture.getText() != null && planetMass.getText() != null && planetType.getValue() != null){
            if(cursorX.get() >= 0 && cursorX.get() <= currentLevel.get().getMapSize()[0] && cursorY.get() >= 0 && cursorY.get() <= currentLevel.get().getMapSize()[1]){
                boolean overlap = false;
                Vector2D cursorPos = new Vector2D(cursorX.get(), cursorY.get());
                double size = Double.parseDouble(planetRadius.getText());
                for (Planet planet : currentLevel.get().getPlanets()) {
                    if (planet.getRigidBody().getRadius() + size > cursorPos.distanceTo(planet.getRigidBody().getPosition()))
                        overlap = true;
                }
                if(!overlap){
                        double mass = Double.parseDouble(planetMass.getText());
                        Planet p = new Planet(new RigidBody(new Vector2D(cursorX.get(), cursorY.get()), size, mass), planetTexture.getText(), planetType.getValue());
                        if(isSpawnBox.isSelected())
                            p.setAsSpawn();
                        currentLevel.get().getPlanets().add(p);
                        drawLevel();
                }
            }
        }
    }

    @FXML
    public void addTarget(){
        if(currentLevel != null){
            if(currentLevel.get().getChallenge() == null)
                defineChallenge();
            if(currentLevel.get() != null){
                double[] temp = {cursorX.get(), cursorY.get()};
                currentLevel.get().getChallenge().getTargets().add(temp);
                drawLevel();
            }
        }
    }

    @FXML
    public void defineChallenge(){
        Dialog<int[]> dialog = new Dialog<>();
        dialog.setTitle("Define Challenge");
        dialog.setHeaderText("Enter the estimated difficulty and the maximum number of shots");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField difficulty = new TextField();
        difficulty.setPromptText("Difficulty");
        difficulty.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));
        TextField shotsNb = new TextField();
        shotsNb.setPromptText("Nb. of Shots");
        shotsNb.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));

        grid.add(new Label("Difficulty:"), 0, 0);
        grid.add(difficulty, 1, 0);
        grid.add(new Label("Nb. of Shots:"), 0, 1);
        grid.add(shotsNb, 1, 1);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> difficulty.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType)
                return new int[]{Integer.parseInt(difficulty.getText()), Integer.parseInt(shotsNb.getText())};
            return null;
        });

        Optional<int[]> result = dialog.showAndWait();

        result.ifPresent(diffShots -> {
            if (currentLevel.get() != null)
                currentLevel.get().setChallenge(new Challenge(diffShots[0], diffShots[1], new HashSet<>()));
        });
    }

    @FXML
    public void deleteTarget(){
        if(currentLevel != null){
            if(currentLevel.get().getChallenge() != null){
                double[] selected = {0,0};
                Vector2D curseur = new Vector2D(cursorX.get(), cursorY.get());
                for (double[] target : currentLevel.get().getChallenge().getTargets()) {
                    Vector2D temp = new Vector2D(target[0], target[1]);
                    if (temp.distanceTo(curseur) < 25) {
                        selected = target;
                        break;
                    }
                }
                currentLevel.get().getChallenge().getTargets().remove(selected);
            }
            drawLevel();
        }
    }

   private void keyboardHandler(KeyEvent event){
        if (currentPlanet.get() != null){
            Vector2D original = currentPlanet.get().getRigidBody().getPosition();
            switch (event.getCode()){
                case Z:
                    currentPlanet.get().getRigidBody().setPosition(original.add(0,-10));
                    cursorY.set(cursorY.get() - 10);
                    break;
                case Q:
                    currentPlanet.get().getRigidBody().setPosition(original.add(-10,0));
                    cursorX.set(cursorX.get() - 10);
                    break;
                case S:
                    currentPlanet.get().getRigidBody().setPosition(original.add(0,10));
                    cursorY.set(cursorY.get() + 10);
                    break;
                case D:
                    currentPlanet.get().getRigidBody().setPosition(original.add(10,0));
                    cursorX.set(cursorX.get() + 10);
                    break;
            }
            drawLevel();
        }
    }
}
