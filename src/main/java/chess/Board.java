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
    protected boolean[][] blackKingAvailable;
    protected boolean[][] whiteKingAvailable;
    protected ArrayList<DefaultPiece> attackingBlackKing;
    protected ArrayList<DefaultPiece> attackingWhiteKing;
    protected King blackKing;
    protected King whiteKing;
    public static final int ROWS = 8;
    public static final int COLS = 8;
    protected List<DefaultPiece> whitePieces;
    protected List<DefaultPiece> blackPieces;
    protected Set<Move> currentWhiteMoves;
    protected Set<Move> currentBlackMoves;
    public boolean white;
    public Board(){
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.currentBlackMoves = new HashSet<>();
        this.currentWhiteMoves = new HashSet<>();
        this.blackKingAvailable = new boolean[ROWS][COLS];
        this.whiteKingAvailable = new boolean[ROWS][COLS];
        this.attackingBlackKing = new ArrayList<>();
        this.attackingWhiteKing = new ArrayList<>();
        this.white = true;
        init_Board();
        init_pieces();
        updatePossibleMoves();
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
    public void init_pieces(){
        for (int row = 0; row < 2; row++){ // black
            for (int col = 0; col < 8; col++){
                blackPieces.add(board[row][col]);
            }
        } 
        for (int row = 6; row < 8; row++){ // white
            for (int col = 0; col < 8; col++){
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
        return row <= 7 && col <= 7 && row >= 0 && col >= 0;
    }
    public DefaultPiece getPiece(int row, int col){
        return board[row][col];
    }
    public void setPiece(DefaultPiece piece, int row, int col){
        board[row][col] = piece;
    }
    public void removePiece(Color color, int row, int col){
        blackPieces.remove(getPiece(row,col));
        board[row][col] = null;
    }
    public boolean getTurn(){
        return white;
    }
    public boolean willPreventCheck(Move move, Color color){
        // things to check
        // is the piece moving the king? if so, check if the space it is moving to is available
        // if not, check which pieces are checking the king, if one is a knight it is not available
        // check if the move intercepts the path between the pieces that are checking the king and the king, if *any* piece can still check the king, it is not available
        // check if the move causes another piece to check the king, this can be done by checking if the piece is on the same diagonal/horizontal/vertical line as the king
        
    }
    public boolean checkAvailable(Color color,int row, int col){
        DefaultPiece piece = getPiece(row,col);
        if (!inBounds(row, col)){
            return false;
        }
        if (piece == null){ // if the square is empty it is a valid move
            return true;
        }
        if (color != piece.getColor()){ // if the square has a piece of the opposing color it is valid
            return true;
        }
        return false;
    }
    public boolean checkAvailable(Color color,int row, int col, boolean isKing){
        DefaultPiece piece = getPiece(row,col);
        if (!inBounds(row, col)){
            return false;
        }
        if (piece == null){ // if the square is empty it is a valid move or it is a king and it is available
            if ((isKing && checkAvailableKing(color,row,col)) || !isKing){
                return true;
            }
            return true; // should only return false when the piece is a king and cannot move to a checked space
        }
        if (color != piece.getColor()){ // if the square has a piece of the opposing color it is valid
            return true;
        }
        return false;
    }
    public boolean checkAvailableKing(Color color, int row, int col){
        return color == Color.BLACK ? blackKingAvailable[row][col] : whiteKingAvailable[row][col];
    }
    public void updatePossibleMoves(){
        currentBlackMoves = new HashSet<>();
        currentWhiteMoves = new HashSet<>();
        for (DefaultPiece piece : blackPieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                currentBlackMoves.add(move);
                whiteKingAvailable[move.getRow()][move.getCol()] = false;
                if (board[move.getRow()][move.getCol()] instanceof King){
                    attackingWhiteKing.add(move.getPiece());
                }
            }
        }
        for (DefaultPiece piece : whitePieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                currentWhiteMoves.add(move);
                blackKingAvailable[move.getRow()][move.getCol()] = false;
                if (board[move.getRow()][move.getCol()] instanceof King){
                    attackingBlackKing.add(move.getPiece());
                }
            }
        }
    }
    public void movePiece(int oldRow, int oldCol, int newRow, int newCol){ 
        DefaultPiece piece = getPiece(oldRow,oldCol);
        if (getPiece(newRow,newCol) != null){ // if the new position has a piece, it must be removed from the appropriate set
            removePiece(piece.getColor() == Color.WHITE ? Color.BLACK : Color.WHITE,newRow,newCol);
        }
        setPiece(piece,newRow,newCol); // update new position on board
        setPiece(null,oldRow,oldCol); // update old position on board
        piece.setRow(newRow); // update piece row
        piece.setCol(newCol); // update piece col
        ChessGUI.updateChessBoard(oldRow,oldCol); // update old position
        ChessGUI.updateChessBoard(newRow,newCol); // update new position
    }
    public boolean makeMove(Move move, Color color){
        if (color == Color.BLACK && !currentBlackMoves.contains(move)){
            return false;
        }
        if (color == Color.WHITE && !currentWhiteMoves.contains(move)){
            return false;
        }
        if (!move.isCastle()){
            int oldRow = move.getPiece().getRow();
            int oldCol = move.getPiece().getCol();
            int newRow = move.getRow(); 
            int newCol = move.getCol();
            if (getPiece(move.getRow(),move.getCol()) != null){ 
                removePiece(color == Color.WHITE ? Color.BLACK : Color.WHITE,newRow,newCol);
            }
            movePiece(oldRow,oldCol,newRow,newCol);
        } 
        else{
        // TODO Castle case
        }
        white = white ? false : true; // change turn
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
