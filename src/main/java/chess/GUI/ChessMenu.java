package chess.GUI;
import java.util.Map;
import chess.*;
import chess.Pieces.DefaultPiece;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class ChessMenu extends Application{
    public static Stage stage;
    public static Button classic = new Button("Classic");
    public static VBox menu = new VBox(classic);
    public ChessGame game = new ChessGame();
    public static Scene menuScene = new Scene(menu);
    public void start(Stage primaryStage) throws Exception {
        classic.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                try {stage.setScene(new Scene(game.getBoard(GameType.CLASSIC)));} catch (Exception e){}
            }
        });
        stage = primaryStage;
        stage.setScene(menuScene);
        stage.setTitle("Smart Chess");
        stage.show();
    }
    public static void reset(){
        stage.setScene(menuScene);
    }
    public static void main(String[] args) {
        launch(args);
    }
}
