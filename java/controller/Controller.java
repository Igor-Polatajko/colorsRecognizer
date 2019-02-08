package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class Controller extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = FXMLLoader.load(new File("src/main/java/view/mainScene.fxml").toURL());
        Scene mainScene = new Scene(root);
        primaryStage.setTitle("WebCam");
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(ae -> System.exit(0));
    }
}
