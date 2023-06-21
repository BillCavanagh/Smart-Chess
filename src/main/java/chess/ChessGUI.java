package chess;

import java.util.HashMap;
import java.util.Map;

import chess.Pieces.DefaultPiece;
import chess.Pieces.Piece;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChessGUI extends Application{
    Map<Piece,String> whiteImages = Map.of(Piece.BISHOP,"images/White_Bishop.png",Piece.KING,"images/White_King.png",
    Piece.KNIGHT,"images/White_Knight.png",Piece.PAWN,"images/White_Pawn.png",Piece.QUEEN,"images/White_Queen.png",Piece.ROOK,"images/White_Rook.png");
    Map<Piece,String> blackImages = Map.of(Piece.BISHOP,"images/Black_Bishop.png",Piece.KING,"images/Black_King.png",
    Piece.KNIGHT,"images/Black_Knight.png",Piece.PAWN,"images/Black_Pawn.png",Piece.QUEEN,"images/Black_Queen.png",Piece.ROOK,"images/Black_Rook.png");
    
    public Image getPieceImage(DefaultPiece piece){
        if (piece != null){
            if (piece.getColor() == Color.WHITE){
                return new Image(whiteImages.get(piece.getPiece()));
            }
            else{
                return new Image(blackImages.get(piece.getPiece()));
            }
        }
        else{
            return new Image("");
        }
    }
    public Color getSpaceColor(int row, int col){
        if (row % 2 == 0 ^ col % 2 == 0){
            return new Color(col, col, row, col)
        }
    }
    public ImageView getSpace(DefaultPiece piece){
        Image image = getPieceImage(piece);
        int rank = Board.indexToRank(piece.getRow());
        char file = Board.indexToFile(piece.getCol());
        ImageView space = new ImageView(image);
        
    }
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
