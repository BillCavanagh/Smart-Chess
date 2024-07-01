package chess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chess.Bot.Bot;
import chess.Bot.Player;
import chess.GUI.ChessGame;
import chess.Pieces.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Board {
    /**
     * The 2d Array of pieces that represents the board
     */
    public DefaultPiece[][] board;
    /**
     * The 2d array that represents spaces the black king can move to legally
     */
    public boolean[][] blackKingAvailable;
    /**
     * The 2d array that represents spaces the white king can move to legally
     */
    public boolean[][] whiteKingAvailable;
    /**
     * The black king piece
     */
    public King blackKing;
    /**
     * The white king piece
     */
    public King whiteKing;
    /**
     * A list of all white pieces not captured
     */
    public List<DefaultPiece> whitePieces;
    /**
     * A list of all black pieces not captured
     */
    public List<DefaultPiece> blackPieces;
    /**
     * The set of all available white moves
     */
    public Set<Move> currentWhiteMoves;
    /**
     * The set of all available black moves
     */
    public Set<Move> currentBlackMoves;
    /**
     * The current color's turn
     */
    public Color turn;
    /**
     * The last move made on the board
     */
    public Move lastMove;
    /**
     * The move representing a short castle for white
     */
    public Move whiteShort;
    /**
     * The move representing a long castle for white
     */
    public Move whiteLong;
    /**
     * The move representing a short castle for black
     */
    public Move blackShort;
    /**
     * The move representing a long castle for black
     */
    public Move blackLong;
    /**
     * The number of rows on the board
     */
    public int rows;
    /**
     * The number of columns on the board
     */
    public int cols;
    /**
     * The corresponding javaFX GUI for the board
     */
    public ChessGame game;
    // public Board(){
    //     rows = 8; 
    //     cols = 8;
    //     turn = Color.WHITE;
    //     init_available();
    //     init_Board(GameType.CLASSIC);
    //     init_pieces();
    //     init_castles();
    //     updatePossibleMoves();
    //     this.game = new ChessGame(GameType.CLASSIC);
    // }
    public Board(GameType gameType, ChessGame game){
        this.rows = gameType.getRows();
        this.cols = gameType.getCols();
        turn = Color.WHITE;
        init_available();
        init_Board(gameType);
        init_pieces();
        init_castles();
        updatePossibleMoves();
        this.game = game;
    }
    public void init_castles(){
        whiteShort = new Move(whiteKing.getRow(),whiteKing.getCol()+2,whiteKing,getPiece(rows-1,cols-1),this);
        whiteLong = new Move(whiteKing.getRow(),whiteKing.getCol()-2,whiteKing,getPiece(rows-1,0),this);
        blackShort = new Move(blackKing.getRow(),blackKing.getCol()+2,blackKing,getPiece(0,cols-1),this);
        blackLong = new Move(blackKing.getRow(),blackKing.getCol()-2,blackKing,getPiece(0,0),this);
    }
    public void init_available(){
        this.blackKingAvailable = new boolean[rows][cols];
        this.whiteKingAvailable = new boolean[rows][cols];
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                blackKingAvailable[row][col] = true;
                whiteKingAvailable[row][col] = true;
            }
        }
    }
    public void init_Board(GameType gameType){
        board = gameType.getLayout();
    }
    public void init_moves(){
        this.currentBlackMoves = new HashSet<>();
        this.currentWhiteMoves = new HashSet<>();
    }
    public void init_pieces(){
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        for (int row = 0; row < rows; row++){ // 
            for (int col = 0; col < cols; col++){
                DefaultPiece piece = getPiece(row,col);
                if (piece != null){
                    if (piece.getColor() == Color.WHITE){
                        if (piece instanceof King){
                            whiteKing = (King)piece;
                        }
                        whitePieces.add(piece);
                    }
                    else {
                        if (piece instanceof King){
                            blackKing = (King)piece;
                        }
                        blackPieces.add(piece);
                    }
                }
            }
        } 
    }
    public int indexToRank(int index){ // index 0 = rank 8, index 1 = rank 7 ect
        return Math.abs(index - rows);
    }
    public char indexToFile(int index){ // index 0 = file a, index 1 = file b ect
        return (char)(97 + index); 
    }
    public int rankToIndex(int rank){ // rank = 8 index = 0, rank = 7 index = 1 ect
        return rows-rank;
    }
    public int fileToIndex(char file){ // file = a index = 0, file = b index = 1 ect
        return (file-97);
    }
    public boolean inBounds(int row, int col){
        return (row >= 0 && row < rows) && (col >= 0 && col < cols); // inBounds = row is in between 0 and rows-1, col is in between 0 and cols-1 
    }
    public DefaultPiece getPiece(int row, int col){
        return inBounds(row,col) ? board[row][col] : null;
    }
    public void addPiece(DefaultPiece piece){
        int row = piece.getRow();
        int col = piece.getCol();
        if (inBounds(row,col)){
            DefaultPiece replaced = getPiece(row,col);
            if (replaced != null){
                removePiece(replaced.getColor(),row,col);
            }
            board[row][col] = piece;
            if (piece.getColor() == Color.WHITE){
                whitePieces.add(piece);
            }
            else{
                blackPieces.add(piece);
            }
        }
    }
    public void setPiece(DefaultPiece piece, int row, int col){
        if (inBounds(row,col)) board[row][col] = piece;
    }
    public void removePiece(Color color, int row, int col){
        if (inBounds(row,col)){
            if (color == Color.WHITE){
                whitePieces.remove(getPiece(row,col));
            }
            else{
                blackPieces.remove(getPiece(row,col));
            }
            board[row][col] = null;
        }
    }
    public Color getTurn(){
        return turn;
    }
    public Move getLastMove(){
        return lastMove;
    }
    public DefaultPiece makeTempMove(Move move){ // assumes the move is valid
        DefaultPiece piece = move.getPiece();
        int newRow = move.getRow();
        int newCol = move.getCol();
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();
        DefaultPiece captured = getPiece(newRow,newCol);
        setPiece(piece,newRow,newCol); // update new position on board
        setPiece(null,oldRow,oldCol); // update old position on board
        piece.setRow(newRow); // update piece row
        piece.setCol(newCol); // update piece col
        return captured;
    }
    public void undoTempMove(int oldRow, int oldCol, DefaultPiece mover, DefaultPiece captured){
        int newRow = mover.getRow();
        int newCol = mover.getCol();
        setPiece(mover,oldRow,oldCol);
        setPiece(captured,newRow,newCol);
        mover.setRow(oldRow);
        mover.setCol(oldCol);
    }
    public boolean noChecks(Color color, DefaultPiece excluded){
        for (DefaultPiece piece : color == Color.WHITE ? blackPieces : whitePieces){ // go through pieces of the opposite color
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            if (piece.equals(excluded) || piece instanceof King){ // ignore the piece that was just captured or if its a king
                continue;
            }
            for (Move move : pieceMoves){
                DefaultPiece attacked = getPiece(move.getRow(),move.getCol());
                if (attacked instanceof King && attacked.getColor() == color){ // if a piece of the opposite color is checking the king, prevent the move
                    return false;
                }
            }
        }
        return true;
    }
    public boolean willPreventCheck(Move move){ // assumes the move would be valid if check wasnt a factor
        // make the move, check if the opposite color can capture the king, if so, it is not allowed, undo the move 
        DefaultPiece piece = move.getPiece();
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();
        DefaultPiece captured = makeTempMove(move); // temporarily make the move
        boolean toReturn = false;
        if (noChecks(piece.getColor(),captured)){ // see if the move results in no checks for the color that moved
            toReturn = true;
        }
        undoTempMove(oldRow,oldCol,piece,captured); 
        return toReturn; 
    }
    public boolean checkAvailable(Color color,int row, int col){
        if (!inBounds(row, col)){ // if not in bounds it is not valid
            return false;
        }
        DefaultPiece toPiece = getPiece(row,col);
        if (toPiece == null){ // if the square is empty it is a valid move
            return true;
        }
        if (color != toPiece.getColor()){ // if the square has a piece of the opposing color it is valid
            return true;
        }
        return false;
    }
    public boolean checkAvailable(Color color,int row, int col, boolean isKingMove){
        if (!inBounds(row, col)){ // if not in bounds it is not valid
            return false;
        }
        DefaultPiece toPiece = getPiece(row,col);
        if (toPiece == null){ // if the square is empty it is a valid move or it is a king and it is available
            if ((isKingMove && checkAvailableKing(color,row,col)) || !isKingMove){
                return true;
            }
            return true; // should only return false when the piece is a king and cannot move to a checked space
        }
        if (color != toPiece.getColor()){ // if the square has a piece of the opposing color it is valid
            return true;
        }
        return false;
    }
    public boolean checkAvailableKing(Color color, int row, int col){
        return color == Color.BLACK ? blackKingAvailable[row][col] : whiteKingAvailable[row][col];
    }
    public boolean kingIsInCheck(Color color){
        return color == Color.WHITE ? !whiteKingAvailable[whiteKing.getRow()][whiteKing.getCol()] : !blackKingAvailable[blackKing.getRow()][blackKing.getCol()];
    }
    public boolean isCheckmate(){
        if (turn == Color.WHITE){
            return currentWhiteMoves.size() == 0 && kingIsInCheck(turn);
        }
        else{
            return currentBlackMoves.size() == 0 && kingIsInCheck(turn);
        }
    }
    public boolean isStalemate(){
        if (turn == Color.WHITE){
            return currentWhiteMoves.size() == 0 && !kingIsInCheck(turn);
        }
        else{
            return currentBlackMoves.size() == 0 && !kingIsInCheck(turn);
        }
    }
    public void updatePossibleMoves(){
        init_available();
        init_moves();
        for (DefaultPiece piece : blackPieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                if (willPreventCheck(move)){
                    currentBlackMoves.add(move);
                    if (!(move.getPiece() instanceof King)) whiteKingAvailable[move.getRow()][move.getCol()] = false;
                }
            }
        }
        for (DefaultPiece piece : whitePieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                if (willPreventCheck(move)){
                    currentWhiteMoves.add(move);
                    if (!(move.getPiece() instanceof King)) blackKingAvailable[move.getRow()][move.getCol()] = false;
                }
            }
        }
    }
    public void movePiece(Move move, Player player, Bot bot) { 
        DefaultPiece piece = move.getPiece();
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();
        int newRow = move.getRow(); 
        int newCol = move.getCol();
        if (getPiece(newRow,newCol) != null){ // if the new position has a piece, it must be removed from the appropriate set
            removePiece(piece.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE,newRow,newCol);
        }
        piece.move(move);
        setPiece(piece,newRow,newCol); // update new position on board
        setPiece(null,oldRow,oldCol); // update old position on board
        if (piece instanceof Pawn){ // potential promotion
            Pawn pawn = (Pawn)(piece);
            if (pawn.canPromote(this)){
                // open a second window for choosing promotion if human player
                if (player == Player.HUMAN){
                    createPromotionWindow(pawn);
                }
                // otherwise, prompt the bot to choose a promotion
                else{
                    Piece promotion = bot.getPromotion();
                    pawn.promote(this, promotion);
                    game.updateChessBoard(newRow,newCol);
                    game.updateLabels(game.getNextPlayer(),game.getNextBot());
                }
            }
        }
        if (game != null){
            game.updateChessBoard(oldRow,oldCol); // update old position
            game.updateChessBoard(newRow,newCol); // update new position
        }
    }
    public boolean makeMove(Move move, Color color, Player player, Bot bot) {
        if (move == null){
            return false;
        }
        if (color == Color.BLACK && !currentBlackMoves.contains(move)){
            return false;
        }
        if (color == Color.WHITE && !currentWhiteMoves.contains(move)){
            return false;
        }
        DefaultPiece piece = move.getPiece();
        if (!move.isCastle()){ // normal move or en passant
            movePiece(move, player, bot);
            DefaultPiece piece2 = move.getPiece2();
            if (piece2 != null){
                removePiece(piece2.getColor(), piece2.getRow(),piece2.getCol()); 
                if (game != null){
                    game.updateChessBoard(piece2.getRow(),piece2.getCol());
                }
            }
        } 
        else{ // castle move
            DefaultPiece rook = move.getPiece2();
            int row = piece.getRow();
            int oldCol = piece.getCol();
            int newKingCol = move.getCol();
            int newRookCol = newKingCol > oldCol ? newKingCol-1 : newKingCol + 1; // if the new position is to the right of its previous position, the rook should be one space to the left of the king
            movePiece(new Move(row,newKingCol,piece,this),player,bot); // move the king
            movePiece(new Move(row,newRookCol,rook,this),player,bot); // move the rook
        }
        turn = turn == Color.WHITE ? Color.BLACK : Color.WHITE; // change turn
        lastMove = move;
        init_available();
        updatePossibleMoves();
        return true;
    }
    public void createPromotionWindow(Pawn pawn){
        Board board = this;
        Stage stage = new Stage();
        HBox options = new HBox();
        javafx.scene.paint.Color spaceColor = this.game.getSpaceColor(pawn.getRow(),pawn.getCol());
        for (Piece piece : Piece.values()){
            Image image = null;
            switch (piece){
                case QUEEN:
                    image = game.getPieceImage(new Queen(pawn.getColor(), pawn.getRow(),pawn.getCol()));
                    break;
                case ROOK:
                    image = game.getPieceImage(new Rook(pawn.getColor(), pawn.getRow(),pawn.getCol()));
                    break;
                case BISHOP:
                    image = game.getPieceImage(new Bishop(pawn.getColor(), pawn.getRow(),pawn.getCol()));
                    break;
                case KNIGHT:
                    image = game.getPieceImage(new Knight(pawn.getColor(), pawn.getRow(),pawn.getCol()));
                    break;
                // cant promote to a pawn/king
                default:
                    continue;
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(game.tileSize);
            imageView.setFitWidth(game.tileSize);
            Button button = new Button("",imageView);
            button.setMinSize(game.tileSize, game.tileSize);
            button.setMaxSize(game.tileSize, game.tileSize);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // being clicked indicates the corresponding piece is chosen
                    int newRow = pawn.getRow();
                    int newCol = pawn.getCol();
                    pawn.promote(board, piece);
                    game.updateChessBoard(newRow,newCol);
                    game.updateLabels(game.getNextPlayer(),game.getNextBot());
                    stage.close();
                }
            });
            button.setBackground(Background.fill(spaceColor));
            button.setBorder(new Border(new BorderStroke(javafx.scene.paint.Color.BLACK, BorderStrokeStyle.SOLID, null, null)));
            options.getChildren().add(button);
        }
        stage.setTitle("Promote to:");        
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(new Scene(options));
        stage.show();
    }
    public String toString(){
        String string = "";
        for (int rank = 0; rank < rows; rank++){
            for (int file = 0; file < cols; file++){
                if (board[rank][file] != null){
                    string += board[rank][file].toString()+ " " +indexToFile(file) + indexToRank(rank) + " ";
                }
                else{
                    string += "_ " + " " +indexToFile(file) + indexToRank(rank) + " ";
                }
            }
            string += "\n";
        }
        return string;
    }
}
