package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import chess.Pieces.*;

public class Board {
    
    protected DefaultPiece[][] board;
    public boolean[][] blackKingAvailable;
    public boolean[][] whiteKingAvailable;
    protected King blackKing;
    protected King whiteKing;
    public static final int ROWS = 8;
    public static final int COLS = 8;
    protected List<DefaultPiece> whitePieces;
    protected List<DefaultPiece> blackPieces;
    protected Set<Move> currentWhiteMoves;
    protected Set<Move> currentBlackMoves;
    public Color turn;
    public Move whiteShort;
    public Move whiteLong;
    public Move blackShort;
    public Move blackLong;
    public Board(){
        turn = Color.WHITE;
        init_available();
        init_Board();
        init_pieces();
        init_castles();
        updatePossibleMoves();
    }
    public void init_castles(){
        whiteShort = new Move(whiteKing.getRow(),whiteKing.getCol()+2,whiteKing,getPiece(7,7));
        whiteLong = new Move(whiteKing.getRow(),whiteKing.getCol()-2,whiteKing,getPiece(7,0));
        blackShort = new Move(blackKing.getRow(),blackKing.getCol()+2,blackKing,getPiece(0,7));
        blackLong = new Move(blackKing.getRow(),blackKing.getCol()-2,blackKing,getPiece(0,0));
    }
    public void init_available(){
        this.blackKingAvailable = new boolean[][]{
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true}
        };
        this.whiteKingAvailable = new boolean[][]{
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true},
            {true,true,true,true,true,true,true,true}
        };
    }
    public void init_Board(){
        board = new DefaultPiece[][]{{new Rook(Color.BLACK,0,0),new Knight(Color.BLACK,0,1),new Bishop(Color.BLACK,0,2),new Queen(Color.BLACK,0,3),new King(Color.BLACK,0,4),new Bishop(Color.BLACK,0,5),new Knight(Color.BLACK,0,6),new Rook(Color.BLACK,0,7)},
                {new Pawn(Color.BLACK,1,0),new Pawn(Color.BLACK,1,1),new Pawn(Color.BLACK,1,2),new Pawn(Color.BLACK,1,3),new Pawn(Color.BLACK,1,4),new Pawn(Color.BLACK,1,5),new Pawn(Color.BLACK,1,6),new Pawn(Color.BLACK,1,7)},
                {null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null},
                {null,null,null,null,null,null,null,null},
                {new Pawn(Color.WHITE,6,0),new Pawn(Color.WHITE,6,1),new Pawn(Color.WHITE,6,2),new Pawn(Color.WHITE,6,3),new Pawn(Color.WHITE,6,4),new Pawn(Color.WHITE,6,5),new Pawn(Color.WHITE,6,6),new Pawn(Color.WHITE,6,7)},   
                {new Rook(Color.WHITE,7,0),new Knight(Color.WHITE,7,1),new Bishop(Color.WHITE,7,2),new Queen(Color.WHITE,7,3),new King(Color.WHITE,7,4),new Bishop(Color.WHITE,7,5),new Knight(Color.WHITE,7,6),new Rook(Color.WHITE,7,7)}};
        whiteKing = (King)board[7][4];
        blackKing = (King)board[0][4];
    }
    public void init_moves(){
        this.currentBlackMoves = new HashSet<>();
        this.currentWhiteMoves = new HashSet<>();
    }
    public void init_pieces(){
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        for (int row = 0; row < 2; row++){ // black
            for (int col = 0; col < COLS; col++){
                blackPieces.add(board[row][col]);
            }
        } 
        for (int row = 6; row < ROWS; row++){ // white
            for (int col = 0; col < COLS; col++){
                whitePieces.add(board[row][col]);
            }
        } 
    }
    public static int indexToRank(int index){ // index 0 = rank 8, index 1 = rank 7 ect
        return Math.abs(index - 8);
    }
    public static char indexToFile(int index){ // index 0 = file a, index 1 = file b ect
        return (char)(97 + index); 
    }
    public static int rankToIndex(int rank){ // rank = 8 index = 0, rank = 7 index = 1 ect
        return 8-rank;
    }
    public static int fileToIndex(char file){ // file = a index = 0, file = b index = 1 ect
        return (file-97);
    }
    public boolean inBounds(int row, int col){
        return row < ROWS && col < COLS && row >= 0 && col >= 0;
    }
    public DefaultPiece getPiece(int row, int col){
        return inBounds(row,col) ? board[row][col] : null;
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
    public void updatePossibleMoves(){
        init_available();
        init_moves();
        for (DefaultPiece piece : blackPieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                if (willPreventCheck(move)){
                    currentBlackMoves.add(move);
                    whiteKingAvailable[move.getRow()][move.getCol()] = false;
                }
            }
        }
        for (DefaultPiece piece : whitePieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                if (willPreventCheck(move)){
                    currentWhiteMoves.add(move);
                    blackKingAvailable[move.getRow()][move.getCol()] = false;
                }
            }
        }
    }
    public void movePiece(Move move){ 
        DefaultPiece piece = move.getPiece();
        int oldRow = piece.getRow();
        int oldCol = piece.getCol();
        int newRow = move.getRow(); 
        int newCol = move.getCol();
        if (getPiece(newRow,newCol) != null){ // if the new position has a piece, it must be removed from the appropriate set
            removePiece(piece.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE,newRow,newCol);
        }
        setPiece(piece,newRow,newCol); // update new position on board
        setPiece(null,oldRow,oldCol); // update old position on board
        piece.move(move);
        ChessGUI.updateChessBoard(oldRow,oldCol); // update old position
        ChessGUI.updateChessBoard(newRow,newCol); // update new position
        updatePossibleMoves();
    }
    public boolean makeMove(Move move, Color color){
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
        if (!move.isCastle()){
            movePiece(move);
        } 
        else{
            DefaultPiece rook = move.getOther();
            int row = piece.getRow();
            int oldCol = piece.getCol();
            int newKingCol = move.getCol();
            int newRookCol = newKingCol > oldCol ? newKingCol-1 : newKingCol + 1; // if the new position is to the right of its previous position, the rook should be one space to the left of the king
            movePiece(new Move(row,newKingCol,piece)); // move the king
            movePiece(new Move(row,newRookCol,rook)); // move the rook
        }
        turn = turn == Color.WHITE ? Color.BLACK : Color.WHITE; // change turn
        init_available();
        return true;
    }
    public String toString(){
        String string = "";
        for (int rank = 0; rank < board.length; rank++){
            for (int file = 0; file < board[0].length; file++){
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
