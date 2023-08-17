package chess.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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

public class ChessGame {
    // images/color stuff
    public static Map<Character,String> whiteImages = Map.of(
        'b',"images/White_Bishop.png",
        'k',"images/White_King.png",
        'n',"images/White_Knight.png",
        'p',"images/White_Pawn.png",
        'q',"images/White_Queen.png",
        'r',"images/White_Rook.png");
    public static Map<Character,String> blackImages = Map.of(
        'b',"images/Black_Bishop.png",
        'k',"images/Black_King.png",
        'n',"images/Black_Knight.png",
        'p',"images/Black_Pawn.png",
        'q',"images/Black_Queen.png",
        'r',"images/Black_Rook.png");
    public static String blankImage = "images/Blank.png";
    public static Color DARK = new Color((double)209/255,(double)139/255,(double)71/255,1);
    public static Color LIGHT = new Color((double)255/255,(double)206/255,(double)158/255,1);
    public static Color HIGHLIGHT = new Color(1,0,0,1);
    // model (board)
    public static Board board;
    // GUI stuff
    public static int TILE_SIZE = 100;
    public static GridPane chessBoard;
    public static TextField textField;
    public static Button submitButton;
    public static Button resetButton;
    public static Label turn;
    public static Label check;
    public static HBox bottomPanel;
    public static VBox game;
    //public static Label moves = new Label("");
    public static VBox moves;
    public static HBox fullGame;
    public static Move selectedMove;
    public static Image getPieceImage(DefaultPiece piece) throws Exception{
        String path;
        if (piece != null){
            if (piece.getColor() == chess.Color.WHITE){
                path = whiteImages.get(piece.getPiece().getShorthand());
            } 
            else{
                path = blackImages.get(piece.getPiece().getShorthand());
            }
        }
        else{
            path = blankImage;
        }
        return new Image(new File(path).toURI().toURL().toString(),true);
    }
    public static Color getSpaceColor(int row,int col){
        if (row % 2 == 0){ // even row
            return col % 2 == 0 ? LIGHT : DARK; // even row and col = light, even row and odd col = dark
        }
        else{ // odd row
            return col % 2 == 0 ? DARK : LIGHT; // odd row and even col = dark, odd row and odd col = light
        }      
    }
    public static StackPane getSpace(DefaultPiece piece, Color color,int row, int col) throws Exception{
        Image image = getPieceImage(piece);
        int rank = board.indexToRank(row);
        char file = board.indexToFile(col);
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
    public static void assembleChessBoard() throws Exception{
        for (int row = 0; row < board.rows; row++){
            for (int col = 0; col < board.cols; col++){
                chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
            }
        }
        chessBoard.setAlignment(Pos.CENTER);
        chessBoard.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,BorderWidths.FULL)));
    }
    public static void updateChessBoard(int row, int col) throws Exception{
        chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
    }
    public static void updateLabels() throws Exception{
        turn.setText(board.getTurn() == chess.Color.WHITE ? "White to Move" : "Black to Move");
        check.setText(board.kingIsInCheck(board.getTurn()) ? "Check" : "");
        moves = MoveUtils.makeInputMoveList(board);
        fullGame.getChildren().remove(1);
        fullGame.getChildren().add(moves);
    }
    public static void updateSelectedMove(Move move) throws Exception{
        // remove all old highlighted squares if there is a selected move
            if (selectedMove != null){
            int row1 = selectedMove.getPiece().getRow();
            int row2 = selectedMove.getRow();
            int col1 = selectedMove.getPiece().getCol();
            int col2 = selectedMove.getCol();
            chessBoard.add(getSpace(board.getPiece(row1,col1),getSpaceColor(row1, col1),row1,col1),col1,row1);
            chessBoard.add(getSpace(board.getPiece(row2,col2),getSpaceColor(row2, col2),row2,col2),col2,row2);
        }
        if (!move.equals(selectedMove)){ // selected move changed, update highlighted squares
            int row1 = move.getPiece().getRow();
            int row2 = move.getRow();
            int col1 = move.getPiece().getCol();
            int col2 = move.getCol();
            chessBoard.add(getSpace(board.getPiece(row1,col1),HIGHLIGHT,row1,col1),col1,row1);
            chessBoard.add(getSpace(board.getPiece(row2,col2),HIGHLIGHT,row2,col2),col2,row2);
            selectedMove = move;
        }
        else{ // otherwise, there is no selected move as a move has been made
            selectedMove = null;
        }
    }
    public static void reset(){
        chessBoard = new GridPane();
        textField = new TextField("");
        submitButton = new Button("Submit move");
        resetButton = new Button("Reset");
        turn = new Label();
        check = new Label("");
        bottomPanel = new HBox(textField,submitButton,resetButton,turn,check);
        game = new VBox(chessBoard,bottomPanel);
        moves = new VBox();
        fullGame = new HBox(game,moves);
        selectedMove = null;
    }
    public static HBox getBoard(GameType gameType) throws Exception {
        reset();
        board = new Board(gameType);
        turn.setText(board.getTurn() == chess.Color.WHITE ? "White to Move" : "Black to Move");
        assembleChessBoard();
        textField.setMaxSize(TILE_SIZE*8,TILE_SIZE*8);
        textField.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,BorderWidths.FULL)));
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // note: this manual input code is outdated, still present in program, going to be removed once other input method works 
                String text = textField.getText(); // get input
                Move move = MoveUtils.parseMove(text,board); // parse input
                try{
                if (board.makeMove(move,board.getTurn())){ // make move
                    textField.setText("");
                }
                
                else{ // invalid move
                    textField.setText("Invalid Move");
                }
                updateLabels();
                }
                catch (Exception e){}
                if (board.isCheckmate()){
                    turn.setText("");
                    check.setText("Checkmate, " + (board.getTurn() == chess.Color.WHITE ? "Black" : "White") + " wins!");
                }
                if (board.isStalemate()){
                    turn.setText("");
                    check.setText("Stalemate, " + "neither player wins");
                }
            }
        };
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChessMenu.reset();
            }
        };
        resetButton.setOnAction(event2);
        updateLabels();
        return fullGame;
    }
}
