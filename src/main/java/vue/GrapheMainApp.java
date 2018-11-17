package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GrapheMainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Scene.fxml"));

        Scene scene = new Scene(root, 1000, 600);

        stage.setTitle("Projet graphes");
        stage.setScene(scene);
        stage.show();
    }
}
