package gamelauncher;

import core.Event;
import demo.MainFrame;
import editor.EditorRun;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
        Platform.exit();
        String[] args = {};
        MainFrame.main(args);
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
}
