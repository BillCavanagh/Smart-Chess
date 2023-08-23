package chess.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import chess.*;
import chess.Pieces.*;
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
        'b',"file:images/White_Bishop.png",
        'k',"file:images/White_King.png",
        'n',"file:images/White_Knight.png",
        'p',"file:images/White_Pawn.png",
        'q',"file:images/White_Queen.png",
        'r',"file:images/White_Rook.png");
    public static Map<Character,String> blackImages = Map.of(
        'b',"file:images/Black_Bishop.png",
        'k',"file:images/Black_King.png",
        'n',"file:images/Black_Knight.png",
        'p',"file:images/Black_Pawn.png",
        'q',"file:images/Black_Queen.png",
        'r',"file:images/Black_Rook.png");
    public static String blankImage = "file:/images/Blank.png";
    public static Color DARK = new Color((double)209/255,(double)139/255,(double)71/255,1);
    public static Color LIGHT = new Color((double)255/255,(double)206/255,(double)158/255,1);
    public static Color HIGHLIGHT = new Color(1,0,0,1);
    // model (board)
    public static Board board;
    // GUI stuff
    public static final int BOARD_SIZE = 600;
    public int tileSize;
    public int fontSize;
    public GridPane chessBoard;
    //public TextField textField;
    //public Button submitButton;
    public Label error;
    public Button resetButton;
    public Label turn;
    public Label check;
    public HBox bottomPanel;
    public VBox game;
    //public static Label moves = new Label("");
    public VBox moves;
    public HBox fullGame;
    public DefaultPiece selectedPiece;
    public Move selectedMove;
    public ChessGame(GameType gameType){
        board = new Board(gameType,this);
        chessBoard = new GridPane();
        //textField = new TextField("");
        //submitButton = new Button("Submit move");
        error = new Label("");
        resetButton = new Button("Reset");
        turn = new Label();
        check = new Label("");
        bottomPanel = new HBox(resetButton,turn,check,error);
        game = new VBox(chessBoard,bottomPanel);
        moves = new VBox();
        fullGame = new HBox(game,moves);
        selectedMove = null;
        tileSize = BOARD_SIZE/board.rows;
        fontSize = BOARD_SIZE/board.rows/3;
        selectedMove = null;
        selectedPiece = null;
        assembleChessBoard();
        //textField.setMaxSize(tileSize*8,tileSize*8);
        //textField.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,BorderWidths.FULL)));
        // EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() { // old code for manual input 
        //     @Override
        //     public void handle(ActionEvent event) {
        //         // note: this manual input code is outdated, going to be removed once other input method works 
        //         String text = textField.getText(); // get input
        //         Move move = MoveUtils.parseMove(text,board); // parse input
        //         if (board.makeMove(move,board.getTurn())){ // make move
        //             textField.setText("");
        //         }
        //         else{ // invalid move
        //             textField.setText("Invalid Move");
        //         }
        //         updateLabels();
        //         if (board.isCheckmate()){
        //             turn.setText("");
        //             check.setText("Checkmate, " + (board.getTurn() == chess.Color.WHITE ? "Black" : "White") + " wins!");
        //         }
        //         if (board.isStalemate()){
        //             turn.setText("");
        //             check.setText("Stalemate, " + "neither player wins");
        //         }
        //     }
        // };
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ChessMenu.reset();
            }
        };
        resetButton.setOnAction(event2);
        updateLabels();
    }
    public Image getPieceImage(DefaultPiece piece){
        // TODO allow files to work inside JAR files
        if (piece != null){
            return piece.getColor() == chess.Color.WHITE ? new Image(whiteImages.get(piece.getPiece().getShorthand()),true) : new Image(blackImages.get(piece.getPiece().getShorthand()),true);
        } 
        else{
            return new Image(blankImage,true);
        }
    }
    public Color getSpaceColor(int row,int col){
        if (row % 2 == 0){ // even row
            return col % 2 == 0 ? LIGHT : DARK; // even row and col = light, even row and odd col = dark
        }
        else{ // odd row
            return col % 2 == 0 ? DARK : LIGHT; // odd row and even col = dark, odd row and odd col = light
        }      
    }
    public void processClick(int row, int col){
        ChessGame game = this;
        if (selectedMove != null){ // if a move has been selected and the space is clicked again process the move
            if (selectedMove.getRow() == row && selectedMove.getCol() == col){
                MoveUtils.processMove(board, game, selectedMove);
                selectedPiece = null;
                selectedMove = null;
                chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
                chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
                error.setText("");
            }
            else{ // when move selected and wrong space chosen display to the user
                error.setText("Click the new space to confirm move");
            }
        }
        else {
            if (selectedPiece != null){  // check if a space was already clicked, if so, attempt to construct a move
                Move move = new Move(-1,-1,null,null);
                if (selectedPiece instanceof King){
                    DefaultPiece other = board.getPiece(row,col);
                    if (other instanceof Rook && other.getColor() == selectedPiece.getColor()){
                        move = new Move(row,col,other,board);
                    }
                    else{
                        move = new Move(row,col,selectedPiece,board);
                    }
                }
                else if (selectedPiece instanceof Pawn){
                    
                }
                else{
                    move = new Move(row,col,selectedPiece,board);
                }
                if (selectedPiece.getColor() == chess.Color.WHITE ? board.currentWhiteMoves.contains(move) : board.currentBlackMoves.contains(move)){
                    selectedMove = move;
                    chessBoard.add(getSpace(board.getPiece(row, col),HIGHLIGHT,row,col),col,row);
                }
                else {
                    error.setText("Invalid Move");
                }
            }
            else{ // no space previously clicked
                DefaultPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() == board.getTurn()){ // check if the space being clicked in a piece of the correct color, if not tell the user its invalid
                    selectedPiece = piece;
                    chessBoard.add(getSpace(selectedPiece,HIGHLIGHT,row,col),col,row);
                } 
                else {
                    error.setText("Invalid piece");
                }
            }
        }
    }
    public StackPane getSpace(DefaultPiece piece, Color color,int row, int col){
        Image image = getPieceImage(piece);
        int rank = board.indexToRank(row);
        char file = board.indexToFile(col);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(tileSize);
        imageView.setFitWidth(tileSize);
        imageView.setVisible(image.getUrl().equals(new Image(blankImage).getUrl()) ? false : true);
        // text for edges of the board: label files/ranks
        Label text = new Label("");
        if (row == board.rows-1 && col == 0){ // bottom left, should have both file and rank
            text.setText(file + "" + rank);
            text.setAlignment(Pos.BOTTOM_LEFT);
        }
        else if (row == board.rows-1){ // bottom of the board, include the file
            text.setText(file + "");
            text.setAlignment(Pos.BOTTOM_CENTER);
        }
        else if (col == 0){ // left of the baord, include the rank
            text.setText(rank + "");
            text.setAlignment(Pos.BASELINE_LEFT);
        }
        text.setFont(new Font("Impact",fontSize));
        text.setMinSize(tileSize, tileSize);
        text.setMaxSize(tileSize, tileSize);
        // button for making moves
        Button button = new Button("",imageView);
        button.setMinSize(tileSize, tileSize);
        button.setMaxSize(tileSize, tileSize);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // logic: 
                // if no space was previously selected, make this the first selected space if it has a piece and is the right color
                // if a space was previously selected, make this the second selected space
                // construct a move using the piece from the first space, and the row and col position from the second space
                // check if the move is a castle/en passant and construct accordingly
                // check if the move is valid, if so highlight the two spaces, if not unselect both spaces and display "invalid move"
                // wait for a press of a "submit" button or a second click on the second space to make the move
                processClick(row, col);
            }
        });
        button.setBackground(Background.fill(color));
        button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
        StackPane space = new StackPane(button);
        // space.setBackground(Background.fill(color));
        // space.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
        return space;
    }
    public void assembleChessBoard(){
        for (int row = 0; row < board.rows; row++){
            for (int col = 0; col < board.cols; col++){
                chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
            }
        }
        chessBoard.setAlignment(Pos.CENTER);
        chessBoard.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,null,BorderWidths.FULL)));
    }
    public void updateChessBoard(int row, int col){
        chessBoard.add(getSpace(board.getPiece(row,col),getSpaceColor(row, col),row,col),col,row);
    }
    public void updateLabels(){
        turn.setText(board.getTurn() == chess.Color.WHITE ? "White to Move" : "Black to Move");
        check.setText(board.kingIsInCheck(board.getTurn()) ? "Check" : "");
        moves = MoveUtils.makeInputMoveList(board,this);
        fullGame.getChildren().remove(1);
        fullGame.getChildren().add(moves);
    }
    public HBox getBoard() {
        return fullGame;
    }
}
