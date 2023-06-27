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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChessGUI extends Application{
    Map<Character,String> whiteImages = Map.of('b',"file:images/White_Bishop.png",'k',"file:images/White_King.png",
    'n',"file:images/White_Knight.png",'p',"file:images/White_Pawn.png",'q',"file:images/White_Queen.png",'r',"file:images/White_Rook.png");
    Map<Character,String> blackImages = Map.of('b',"file:images/Black_Bishop.png",'k',"file:images/Black_King.png",
    'n',"file:images/Black_Knight.png",'p',"file:images/Black_Pawn.png",'q',"file:images/Black_Queen.png",'r',"file:images/Black_Rook.png");
    String blankImage = "file:images/Blank.png";
    Color DARK = new Color((double)209/255,(double)139/255,(double)71/255,1);
    Color LIGHT = new Color((double)255/255,(double)206/255,(double)158/255,1);
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
    public StackPane getSpace(DefaultPiece piece, Color color,int row, int col){
        Image image = getPieceImage(piece);
        int rank = Board.indexToRank(row);
        char file = Board.indexToFile(col);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setVisible(image.getUrl().equals(new Image(blankImage).getUrl()) ? false : true);
        Label text = new Label(file + "" + rank);
        text.setFont(new Font("Times New Roman",10));
        text.setMinSize(50, 50);
        text.setAlignment(Pos.BOTTOM_LEFT);
        StackPane space = new StackPane(imageView,text);
        space.setBackground(Background.fill(color));
        space.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
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
