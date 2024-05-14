package chess.GUI;
import java.util.Map;

import chess.*;
import chess.Bot.Bot;
import chess.Bot.BotTypes;
import chess.Bot.Player;
import chess.Bot.RandomBot;
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
    public static VBox gameSelect;
    public static int GAME_SELECT_TITLE_FONT_SIZE = 50;
    public static int GAME_SELECT_PADDING = 150;
    public static VBox playerSelect;
    public static int PLAYER_SELECT_WIDTH = ChessGame.BOARD_SIZE*2/3;
    public static int PLAYER_SELECT_TITLE_FONT_SIZE = 40;
    public static int PLAYER_SELECT_PADDING = 50;
    public static int BUTTON_FONT_SIZE = 25;
    public static HBox menu;
    public static ChessGame currentGame;
    // bot related stuf
    public static Player white = Player.HUMAN;
    public static Player black = Player.BOT;
    public static BotTypes whiteBot = BotTypes.RANDOM;
    public static BotTypes blackBot = BotTypes.RANDOM;
    public static BotTypes[] botTypes = BotTypes.values();
    public static Button createBotTypeButton(Color color){
        Button button = new Button(color == Color.WHITE ? "White Bot: " + botTypes[0].name() : "Black Bot: " + botTypes[0].name());
        button.setOnAction(new EventHandler<ActionEvent>(){
            public int currentIndex = 0;
            public static int length = botTypes.length;
            public void handle(ActionEvent event) {
                currentIndex = (currentIndex + 1) % length;
                if (color == Color.WHITE){
                    button.setText("White Bot: " + botTypes[currentIndex].name());
                    whiteBot = botTypes[currentIndex];
                }
                else{
                    button.setText("Black Bot: " + botTypes[currentIndex].name());
                    blackBot = botTypes[currentIndex];
                }
            }
        });
        button.setBackground(new Background(new BackgroundFill(color == Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK,CornerRadii.EMPTY,null)));
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        button.setPrefSize(PLAYER_SELECT_WIDTH,100);
        button.setTextFill(color == Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        button.setFont(new Font("Georgia",BUTTON_FONT_SIZE));
        return button;
    }
    public static Button createSelectButton(Color color){
        Button button = new Button(color == Color.WHITE ? "White: Human" : "Black: Bot");
        button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                if (color == Color.WHITE){
                    if (button.getText().equals("White: Human")){
                        button.setText("White: Bot");
                        white = Player.BOT;
                    }
                    else{
                        button.setText("White: Human");
                        white = Player.HUMAN;
                    }
                }
                else{
                    if (button.getText().equals("Black: Human")){
                        button.setText("Black: Bot");
                        black = Player.BOT;
                    }
                    else{
                        button.setText("Black: Human");
                        black = Player.HUMAN;
                    }
                }
            }
        });
        button.setBackground(new Background(new BackgroundFill(color == Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK,CornerRadii.EMPTY,null)));
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        button.setPrefSize(PLAYER_SELECT_WIDTH,100);
        button.setTextFill(color == Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        button.setFont(new Font("Georgia",BUTTON_FONT_SIZE));
        return button;
    }
    public static VBox createPlayerSelect(){
        VBox playerSelect = new VBox();
        Label title = createTitle("Player Select",PLAYER_SELECT_TITLE_FONT_SIZE,PLAYER_SELECT_PADDING);
        title.setPrefWidth(PLAYER_SELECT_WIDTH);
        playerSelect.getChildren().add(title);
        playerSelect.setPrefWidth(PLAYER_SELECT_WIDTH);
        return playerSelect;
    }
    public static Label createTitle(String titleString, int fontSize, int lRPadding){
        Label title = new Label(titleString);
        title.setFont(new Font("Georgia",fontSize));
        title.setAlignment(Pos.CENTER);
        //title.setPadding(new Insets(25, lRPadding, 25,lRPadding));
        title.setPrefWidth(ChessGame.BOARD_SIZE);
        title.setPrefHeight(100);
        title.setBackground(new Background(new BackgroundFill(ChessGame.DARK,CornerRadii.EMPTY,null)));
        title.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        title.setTextFill(ChessGame.LIGHT);
        return title;
    }
    public static Button createGameTypeButton(String text, GameType gameType){
        Button button = new Button(text);
        button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event) {
                currentGame = new ChessGame(gameType,white,whiteBot,black,blackBot);
                Scene scene = new Scene(currentGame.getBoard());
                stage.setScene(scene);
            }
        });
        button.setBackground(new Background(new BackgroundFill(ChessGame.LIGHT,CornerRadii.EMPTY,null)));
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        button.setPrefSize(ChessGame.BOARD_SIZE,100);
        button.setTextFill(ChessGame.DARK);
        button.setFont(new Font("Georgia",BUTTON_FONT_SIZE));
        return button;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        reset();
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setTitle(titleName);
        stage.show();
    }
    public static void reset(){
        gameSelect = new VBox();
        Label title = createTitle(titleName,GAME_SELECT_TITLE_FONT_SIZE,GAME_SELECT_PADDING);
        gameSelect.getChildren().add(title);
        for (GameType gameType : GameType.values()){
            gameSelect.getChildren().add(createGameTypeButton(gameType.name(), gameType));
        }
        gameSelect.setPrefSize(ChessGame.BOARD_SIZE,ChessGame.BOARD_SIZE);
        playerSelect = new VBox();
        playerSelect.getChildren().add(createPlayerSelect());
        white = Player.HUMAN;
        black = Player.BOT;
        whiteBot = BotTypes.RANDOM;
        blackBot = BotTypes.RANDOM;
        Button white = createSelectButton(Color.WHITE);
        Button black = createSelectButton(Color.BLACK);
        Button whiteBot = createBotTypeButton(Color.WHITE);
        Button blackBot = createBotTypeButton(Color.BLACK);
        playerSelect.getChildren().add(white);
        playerSelect.getChildren().add(whiteBot);
        playerSelect.getChildren().add(black);
        playerSelect.getChildren().add(blackBot);
        menu = new HBox();
        menu.getChildren().add(gameSelect);
        menu.getChildren().add(playerSelect);
        stage.setScene(new Scene(menu));
    }
    public static void main(String[] args) {
        launch(args);
    }
}
