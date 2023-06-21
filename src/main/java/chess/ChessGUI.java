package chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ChessGUI extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(new Label()));
        primaryStage.setTitle("Chess");
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
}
