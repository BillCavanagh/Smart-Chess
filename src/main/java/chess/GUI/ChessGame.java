package chess.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import chess.*;
import chess.Bot.*;
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
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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
        'b',"White_Bishop.png",
        'k',"White_King.png",
        'n',"White_Knight.png",
        'p',"White_Pawn.png",
        'q',"White_Queen.png",
        'r',"White_Rook.png");
    public static Map<Character,String> blackImages = Map.of(
        'b',"Black_Bishop.png",
        'k',"Black_King.png",
        'n',"Black_Knight.png",
        'p',"Black_Pawn.png",
        'q',"Black_Queen.png",
        'r',"Black_Rook.png");
    public static String blankImage = "Blank.png";
    public static Color DARK = new Color((double)209/255,(double)139/255,(double)71/255,1);
    public static Color LIGHT = new Color((double)255/255,(double)206/255,(double)158/255,1);
    public static Color HIGHLIGHT = Color.RED;
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
    // bot stuff
    public Player white;
    public Player black;
    public Bot whiteBot;
    public Bot blackBot;
    public BotTypes whiteBotType;
    public BotTypes blackBotType;
    public boolean started;
    public static final int SLEEP_TIME = 200;
    public Bot makeBot(BotTypes BotType, chess.Color color){
        switch (BotType){
            case RANDOM:
                return new RandomBot(board,color);
            case BEST_MATERIAL:
                return new BestMaterialBot(board,color);
            default:
                return null;
        }
    }
    public ChessGame(GameType gameType, Player white, BotTypes whiteBotType, Player black, BotTypes blackBotType){
        started = false;
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
        this.white = white;
        this.black = black;
        this.whiteBotType = whiteBotType;
        this.blackBotType = blackBotType;
        fullGame.setBackground(Background.fill(Color.SADDLEBROWN));
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
        Color background = (ChessGame.LIGHT);
        Color text = (ChessGame.DARK);
        resetButton.setBackground(new Background(new BackgroundFill(background,CornerRadii.EMPTY,null)));
        resetButton.setTextFill(text);
        resetButton.setFont(new Font("Century",fontSize));
        resetButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        resetButton.setPrefSize(BOARD_SIZE/4,50);
        turn.setBackground(new Background(new BackgroundFill(background,CornerRadii.EMPTY,null)));
        turn.setTextFill(text);
        turn.setFont(new Font("Century",(fontSize*2)/3));
        turn.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        turn.setPrefSize(BOARD_SIZE/4,50);
        check.setBackground(new Background(new BackgroundFill(background,CornerRadii.EMPTY,null)));
        check.setTextFill(text);
        check.setFont(new Font("Century",fontSize));
        check.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        check.setPrefSize(BOARD_SIZE/4,50);
        error.setBackground(new Background(new BackgroundFill(background,CornerRadii.EMPTY,null)));
        error.setTextFill(text);
        error.setFont(new Font("Century",fontSize));
        error.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        error.setPrefSize(BOARD_SIZE/4,50);
        updateLabels(getNextPlayer(),getNextBot());
    }
    public Player getNextPlayer(){
        return board.getTurn() == chess.Color.WHITE ? white : black;
    }
    public Bot getNextBot(){
        return board.getTurn() == chess.Color.WHITE ? whiteBot : blackBot;
    }
    public Image getPieceImage(DefaultPiece piece){
        String path = "";
        if (piece == null){
            path = blankImage;
        }
        else{
            path = piece.getColor() == chess.Color.WHITE ? whiteImages.get(piece.getShorthand()) : blackImages.get(piece.getShorthand());
        }
        InputStream stream = getClass().getClassLoader().getResourceAsStream(path); 
        return new Image(stream);
    }
    public Color getSpaceColor(int row,int col){
        if (row % 2 == 0){ // even row
            return col % 2 == 0 ? LIGHT : DARK; // even row and col = light, even row and odd col = dark
        }
        else{ // odd row
            return col % 2 == 0 ? DARK : LIGHT; // odd row and even col = dark, odd row and odd col = light
        }      
    }
    public void highlightSpace(int row, int col){
        chessBoard.add(getSpace(board.getPiece(row, col),HIGHLIGHT,row,col),col,row);
    }
    public void unhighlightSpace(int row, int col){
        chessBoard.add(getSpace(board.getPiece(row, col),getSpaceColor(row, col),row,col),col,row);
    }
    public void processClick(int row, int col){
        // logic: 
        // if no space was previously selected, make this the first selected space if it has a piece and is the right color
        // if a space was previously selected, make this the second selected space
        // construct a move using the piece from the first space, and the row and col position from the second space
        // check if the move is a castle/en passant and construct accordingly
        // check if the move is valid, if so highlight the two spaces, if not unselect both spaces and display "invalid move"
        // wait for a press of a "submit" button or a second click on the second space to make the move
        ChessGame game = this;
        if (selectedMove != null){ // if a move has been selected and the space is clicked again process the move
            if (selectedMove.getRow() == row && selectedMove.getCol() == col){
                MoveUtils.processMove(board, game, selectedMove);
                selectedPiece = null;
                selectedMove = null;
                error.setText("");
            }
            else{ // when move selected and wrong space chosen display to the user and reset move
                error.setText("Invalid move confirmation");
                int rmRow1 = game.selectedMove.getRow();
                int rmCol1 = selectedMove.getCol();
                int rmRow2 = selectedPiece.getRow();
                int rmCol2 = selectedPiece.getCol();
                unhighlightSpace(rmRow1, rmCol1);
                unhighlightSpace(rmRow2, rmCol2);
                selectedPiece = null;
                selectedMove = null;
            }
        }
        else {
            if (selectedPiece != null){  // check if a space was already clicked, if so, attempt to construct a move
                Move move = new Move(-1,-1,null,null);
                if (selectedPiece instanceof King){ // find the move if castle
                    DefaultPiece other = board.getPiece(row,col);
                    if (other instanceof Rook && other.getColor() == selectedPiece.getColor()){
                        move = new Move(row,col,other,board);
                    }
                    else{
                        move = new Move(row,col,selectedPiece,board);
                    }
                }
                else if (selectedPiece instanceof Pawn){ // find the move if en passant
                    for (Move temp : selectedPiece.getPossibleMoves(board)){
                        if (temp.getRow() == row && temp.getCol() == col){
                            move = temp;
                            break;
                        }
                    }
                }
                else{
                    move = new Move(row,col,selectedPiece,board);
                }
                if (selectedPiece.getColor() == chess.Color.WHITE ? board.currentWhiteMoves.contains(move) : board.currentBlackMoves.contains(move)){ // select the move if valid move
                    selectedMove = move;
                    highlightSpace(row, col);
                }
                else { // inform the user if an invalid move 
                    error.setText("Invalid Move");
                    int rmRow1 = selectedPiece.getRow();
                    int rmCol1 = selectedPiece.getCol();
                    selectedPiece = null;
                    unhighlightSpace(rmRow1, rmCol1);
                }
            }
            else{ // no space previously clicked
                DefaultPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() == board.getTurn()){ // check if the space being clicked in a piece of the correct color, if not tell the user its invalid
                    selectedPiece = piece;
                    highlightSpace(row, col);
                } 
                else {
                    error.setText("Invalid piece");
                }
            }
        }
        if (board.isCheckmate()){
            game.turn.setText("");
            game.check.setText("Checkmate, " + (board.getTurn() == chess.Color.WHITE ? "Black" : "White") + " wins!");
        }
        if (board.isStalemate()){
            game.turn.setText("");
            game.check.setText("Stalemate, " + "neither player wins");
        }
        game.updateLabels(getNextPlayer(),getNextBot());
    }
    public void processBot(Bot bot){
        try{Thread.sleep(SLEEP_TIME);}catch(InterruptedException e){System.out.println(e);}
        Move move = bot.getNextMove();
        MoveUtils.processBotMove(board,this,move,bot);
        selectedPiece = null;
        selectedMove = null;
        error.setText("");
        this.updateLabels(getNextPlayer(),getNextBot());
    }
    public StackPane getSpace(DefaultPiece piece, Color color,int row, int col){
        Image image = getPieceImage(piece);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(tileSize);
        imageView.setFitWidth(tileSize);
        imageView.setVisible(piece == null ? false : true);
        //int rank = board.indexToRank(row);
        //char file = board.indexToFile(col);
        // text for edges of the board: label files/ranks
        // Label text = new Label("");
        // if (row == board.rows-1 && col == 0){ // bottom left, should have both file and rank
        //     text.setText(file + "" + rank);
        //     text.setAlignment(Pos.BOTTOM_LEFT);
        // }
        // else if (row == board.rows-1){ // bottom of the board, include the file
        //     text.setText(file + "");
        //     text.setAlignment(Pos.BOTTOM_CENTER);
        // }
        // else if (col == 0){ // left of the baord, include the rank
        //     text.setText(rank + "");
        //     text.setAlignment(Pos.BASELINE_LEFT);
        // }
        // text.setFont(new Font("Century",fontSize));
        // text.setMinSize(tileSize, tileSize);
        // text.setMaxSize(tileSize, tileSize);
        // button for making moves
        Button button = new Button("",imageView);
        button.setMinSize(tileSize, tileSize);
        button.setMaxSize(tileSize, tileSize);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
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
    public void updateLabels(Player player, Bot bot){
        if (!started){
            started = true;
            if (white == Player.BOT){
                this.whiteBot = makeBot(this.whiteBotType,chess.Color.WHITE);
            }
            if (black == Player.BOT){
                this.blackBot = makeBot(this.blackBotType,chess.Color.BLACK);
            }
            if (player == Player.BOT){
                processBot(getNextBot());
            }
        }
        else{
            if (player == Player.BOT){
                processBot(bot);
            }
        }
        turn.setText(board.getTurn() == chess.Color.WHITE ? "White to Move" : "Black to Move");
        turn.setBackground(new Background(new BackgroundFill(board.getTurn() == chess.Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK,CornerRadii.EMPTY,null)));
        turn.setTextFill(board.getTurn() == chess.Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        check.setBackground(new Background(new BackgroundFill(board.getTurn() == chess.Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK,CornerRadii.EMPTY,null)));
        check.setTextFill(board.getTurn() == chess.Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        error.setBackground(new Background(new BackgroundFill(board.getTurn() == chess.Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK,CornerRadii.EMPTY,null)));
        error.setTextFill(board.getTurn() == chess.Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        resetButton.setBackground(new Background(new BackgroundFill(board.getTurn() == chess.Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK,CornerRadii.EMPTY,null)));
        resetButton.setTextFill(board.getTurn() == chess.Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        check.setText(board.kingIsInCheck(board.getTurn()) ? "Check" : "");
        moves = MoveUtils.makeInputMoveList(board,this);
        fullGame.getChildren().remove(1);
        fullGame.getChildren().add(moves);
    }
    public HBox getBoard() {
        return fullGame;
    }
}
