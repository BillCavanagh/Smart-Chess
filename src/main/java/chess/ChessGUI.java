package chess;

import java.util.Map;

import chess.Pieces.DefaultPiece;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChessGUI extends Application{
    Map<Character,String> whiteImages = Map.of('b',"file:images/White_Bishop.png",'k',"file:images/White_King.png",
    'n',"file:images/White_Knight.png",'p',"file:images/White_Pawn.png",'q',"file:images/White_Queen.png",'r',"file:images/White_Rook.png");
    Map<Character,String> blackImages = Map.of('b',"file:images/Black_Bishop.png",'k',"file:images/Black_King.png",
    'n',"file:images/Black_Knight.png",'p',"file:images/Black_Pawn.png",'q',"file:images/Black_Queen.png",'r',"file:images/Black_Rook.png");
    String blankImage = "file:images/Blank.png";
    Color DARK = new Color(209/255,139/255,70/255,0);
    Color LIGHT = new Color(254/255,206/255,158/255,0);
    Board board = new Board();
    GridPane chessBoard = new GridPane();
    public Image getPieceImage(DefaultPiece piece){
        if (piece != null){
            if (piece.getColor() == chess.Color.WHITE){
                return new Image(whiteImages.get(piece.getPiece().getShorthand()),true);
            }
            else{
                return new Image(blackImages.get(piece.getPiece().getShorthand()),true);
            }
        }
        else{
            return new Image(blankImage);
        }
    }
    public Label getSpace(DefaultPiece piece, Color color,int row, int col){
        Image image = getPieceImage(piece);
        int rank = Board.indexToRank(row);
        char file = Board.indexToFile(col);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        Label space = new Label("a",new ImageView(image));
        space.setBackground(Background.fill(color));
        space.setMaxSize(20.0,20.0);
        return space;
    }
    public void assembleChessBoard(){
        boolean light = true;
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                if (col != 0){
                light = light ? false : true;
                }
                chessBoard.add(getSpace(board.getPiece(row,col),light ? LIGHT : DARK,row,col),col,row);
            }
        }
    }
    public void updateChessBoard(){

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        assembleChessBoard();
        primaryStage.setScene(new Scene(chessBoard));
        primaryStage.setTitle("Chess");
        primaryStage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
}
