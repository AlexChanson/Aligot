package gamelauncher;

import editor.EditorRun;
import editor.FxUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GameLauncherController {
    @FXML
    private TextField screenWidthField;
    @FXML
    private TextField screenHeightField;
    @FXML
    private TextField player_1_Field;
    @FXML
    private TextField player_2_Field;
    @FXML
    private CheckBox fullscreenCheckBox;

    private GameStart game;
    private Thread gameThread;

    public void initialize(){
        screenWidthField.setTextFormatter(new TextFormatter<>(FxUtils.intFilter));
        game = new Game();
    }

    @FXML
    public void quit(){
        Platform.exit();
    }

    @FXML
    public void launchGame(){
        if (gameThread != null && gameThread.isAlive())
            return;
        gameThread = new Thread(() -> {
            boolean fullscreen = fullscreenCheckBox.isSelected();
            int width, height;
            if(!fullscreen) {
                width = Integer.parseInt(screenWidthField.getText());
                height = Integer.parseInt(screenHeightField.getText());
            }
            else {
                Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
                width = (int) primaryScreenBounds.getWidth();
                height = (int) primaryScreenBounds.getHeight();
            }
            String player1_name = player_1_Field.getText();
            String player2_name = player_2_Field.getText();

            game.start(height, width, fullscreen, player1_name, player2_name);
        });
        gameThread.start();

        Platform.exit();
    }

    @FXML
    public void launchGameEditor(){
        Stage primaryStage = new Stage();
        EditorRun editorRun = new EditorRun();
        try {
            editorRun.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void controlsWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("controls.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 400, 400);
        Stage primaryStage = new Stage();
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(400);

        primaryStage.setTitle("Controls");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    public void creditsWindow(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("credits.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, 300, 550);
        Stage primaryStage = new Stage();
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);

        primaryStage.setTitle("Game credits");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
