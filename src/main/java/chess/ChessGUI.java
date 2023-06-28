package chess;

import java.util.Map;

import chess.Pieces.DefaultPiece;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ChessGUI extends Application{
    public static Map<Character,String> whiteImages = Map.of('b',"file:images/White_Bishop.png",'k',"file:images/White_King.png",
    'n',"file:images/White_Knight.png",'p',"file:images/White_Pawn.png",'q',"file:images/White_Queen.png",'r',"file:images/White_Rook.png");
    public static Map<Character,String> blackImages = Map.of('b',"file:images/Black_Bishop.png",'k',"file:images/Black_King.png",
    'n',"file:images/Black_Knight.png",'p',"file:images/Black_Pawn.png",'q',"file:images/Black_Queen.png",'r',"file:images/Black_Rook.png");
    public static String blankImage = "file:images/Blank.png";
    public static int TILE_SIZE = 100;
    public static Color DARK = new Color((double)209/255,(double)139/255,(double)71/255,1);
    public static Color LIGHT = new Color((double)255/255,(double)206/255,(double)158/255,1);
    public static Board board = new Board();
    public static GridPane chessBoard = new GridPane();
    public static TextField textField = new TextField("");
    public static VBox game = new VBox(chessBoard,textField);
    public static Image getPieceImage(DefaultPiece piece){
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
    public static Color getSpaceColor(int row,int col){
        if (row % 2 == 0){
            if (col % 2 == 0){ // even row and col = light
                return LIGHT;
            }
            else{ // even row and odd col = dark
                return DARK;
            }
        }
        else{
            if (col % 2 == 0){ // odd row and even col = dark
                return DARK;
            }
            else{ // odd row and odd col = light
                return LIGHT;
            }
        }      
    }
    public static StackPane getSpace(DefaultPiece piece, Color color,int row, int col){
        Image image = getPieceImage(piece);
        int rank = Board.indexToRank(row);
        char file = Board.indexToFile(col);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(TILE_SIZE);
        imageView.setFitWidth(TILE_SIZE);
        imageView.setVisible(image.getUrl().equals(new Image(blankImage).getUrl()) ? false : true);
        Label text = new Label(file + "" + rank);
        text.setFont(new Font("Times New Roman",20));
        text.setMinSize(TILE_SIZE, TILE_SIZE);
        text.setAlignment(Pos.BOTTOM_LEFT);
        StackPane space = new StackPane(imageView,text);
        space.setBackground(Background.fill(color));
        space.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
        return space;
    }
    public static void assembleChessBoard(){
        for (int row = 0; row < 8; row++){
            for (int col = 0; col < 8; col++){
                chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
            }
        }
        chessBoard.setAlignment(Pos.CENTER);
        chessBoard.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,BorderWidths.FULL)));
    }
    public static void updateChessBoard(int row, int col){
        chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        assembleChessBoard();
        textField.setMaxSize(TILE_SIZE*8,TILE_SIZE*8);
        textField.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,BorderWidths.FULL)));
        primaryStage.setScene(new Scene(game));
        primaryStage.setTitle("Chess");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
