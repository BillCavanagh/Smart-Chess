package chess;

import java.util.Map;

import chess.Pieces.DefaultPiece;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChessGUI extends Application{
    Map<Character,String> whiteImages = Map.of('b',"file:images/White_Bishop.png",'k',"file:images/White_King.png",
    'n',"file:images/White_Knight.png",'p',"file:images/White_Pawn.png",'q',"file:images/White_Queen.png",'r',"file:images/White_Rook.png");
    Map<Character,String> blackImages = Map.of('b',"file:images/Black_Bishop.png",'k',"file:images/Black_King.png",
    'n',"file:images/Black_Knight.png",'p',"file:images/Black_Pawn.png",'q',"file:images/Black_Queen.png",'r',"file:images/Black_Rook.png");
    String blankImage = "file:images/Blank.png";
    Color DARK = Color.BROWN;
    Color LIGHT = Color.BEIGE;
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
        imageView.setVisible(image.getUrl().equals(new Image(blankImage).getUrl()) ? false : true);
        Label space = new Label(file + "" + rank,new ImageView(image));
        space.setBackground(Background.fill(color));
        space.setMaxSize(20.0,20.0);
        space.setBorder(new Border(new BorderStroke(Color.BLACK, null, null, null)));
        return space;
    }
    public void assembleChessBoard(){
        boolean light = true;
        //int col = 0;
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                if (col != 0){
                    light = light ? false : true;
                }
                chessBoard.add(getSpace(board.getPiece(row,col),light ? LIGHT : DARK,row,col),col,row);
            }
        }
        chessBoard.setAlignment(Pos.CENTER);
        chessBoard.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,BorderWidths.FULL)));
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
