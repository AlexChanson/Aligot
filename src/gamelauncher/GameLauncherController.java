package gamelauncher;

import core.Event;
import demo.MainFrame;
import editor.EditorRun;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
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

    public void initialize(){

    }

    @FXML
    public void quit(){
        Platform.exit();
    }

    @FXML
    public void launchGame(){
        Thread t = new Thread(() -> {
            String[] args = {};
            MainFrame.main(args);
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
