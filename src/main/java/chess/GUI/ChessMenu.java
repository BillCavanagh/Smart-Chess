package chess.GUI;
import java.util.Map;
import chess.*;
import chess.Pieces.DefaultPiece;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class ChessMenu extends Application{
    public static Stage stage;
    public static Label title;
    public static final String titleName = "Smart Chess";
    public static VBox menu;
    public static ChessGame currentGame;
    public static Label createTitle(){
        Label title = new Label(titleName);
        title.setFont(new Font("Georgia",50));
        title.autosize();
        title.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,new Insets(20))));
        title.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        return title;
    }
    public static Button createGameTypeButton(String text, GameType gameType){
        Button button = new Button(text);
        button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                try {
                    currentGame = new ChessGame(gameType);
                    stage.setScene(new Scene(currentGame.getBoard()));
                } 
                catch (Exception e){System.out.println(e);}
            }
        });
        button.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,new Insets(20))));
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        button.autosize();
        return button;
    }
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        reset();
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle(titleName);
        stage.show();
    }
    public static void reset(){
        menu = new VBox();
        Label title = createTitle();
        menu.getChildren().add(title);
        for (GameType gameType : GameType.values()){
            menu.getChildren().add(createGameTypeButton(gameType.name(), gameType));
        }
        stage.setScene(new Scene(menu));
    }
    public static void main(String[] args) {
        launch(args);
    }
}
