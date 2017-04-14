package gamelauncher;/**
 * Created by ben on 14/04/17.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameLauncher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("launcher.fxml"));
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 450, 400);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(400);

        primaryStage.setTitle("Launcher");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
