package login;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Login");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(790);
        primaryStage.setMinWidth(1344);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
