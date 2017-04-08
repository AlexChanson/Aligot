package editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class EditorRun extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene mainStage = new Scene(root, 1480, 730);

        primaryStage.setTitle("Level Editor");
        primaryStage.setScene(mainStage);
        //primaryStage.getIcons().add(<an icon for the window>);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

