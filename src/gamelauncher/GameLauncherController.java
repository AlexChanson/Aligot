package gamelauncher;

import editor.EditorRun;
import editor.FxUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by ben on 14/04/17.
 */
public class GameLauncherController {
    @FXML
    private javafx.scene.control.MenuItem quitMenuItem;

    @FXML
    private AnchorPane masterAnchor;
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

    public void initialize(){
        screenWidthField.setTextFormatter(new TextFormatter<>(FxUtils.filter));
        game = new Game();
    }

    @FXML
    public void quit(){
        Platform.exit();
    }

    @FXML
    public void launchGame(){
        Thread t = new Thread(() -> {
            int width = Integer.parseInt(screenWidthField.getText());
            int height = Integer.parseInt(screenHeightField.getText());
            boolean fullscreen = fullscreenCheckBox.isSelected();
            String player1_name = player_1_Field.getText();
            String player2_name = player_2_Field.getText();

            game.start(height, width, fullscreen, player1_name, player2_name);
        });
        t.start();

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

        Scene scene = new Scene(root, 300, 400);
        Stage primaryStage = new Stage();
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(400);

        primaryStage.setTitle("Game credits");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
