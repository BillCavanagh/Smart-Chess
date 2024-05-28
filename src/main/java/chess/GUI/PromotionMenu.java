package chess.GUI;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PromotionMenu extends Application{
    public ChessGame game;
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label promotionText = new Label("Promote to:");
        HBox pieceOptions = new HBox();
        promotionText.setBackground(new Background(new BackgroundFill(ChessGame.board.getTurn() == chess.Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK,CornerRadii.EMPTY,null)));
        promotionText.setTextFill(ChessGame.board.getTurn() == chess.Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        
    }
}
