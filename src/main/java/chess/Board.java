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
        Arrays.fill(blackKingAvailable,true);
        this.whiteKingAvailable = new boolean[ROWS][COLS];
        Arrays.fill(whiteKingAvailable,true);
        this.white = true;
        init_Board();
        init_pieces();
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
        if (color == Color.WHITE){ // check if the king is in check, if so dont allow the move UNLESS it stops the check
            if (whiteKing.getIsInCheck()){
                // TODO Determine if the move will block the check
            }
        } 
        else{
            if (blackKing.getIsInCheck()){
                // TODO Determine if the move will block the check
            }
        }
        if (piece == null){ // if the square is empty it is a valid move or it is a king and it is available
            if ((isKing && checkAvailableKing(color,row,col)) || !isKing){
                return true;
            }
            return false; // should only return false when the piece is a king and cannot move to a checked space
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
            }
        }
        for (DefaultPiece piece : whitePieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                currentWhiteMoves.add(move);
                blackKingAvailable[move.getRow()][move.getCol()] = false;
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
            white = white ? false : true; // change turn
            blackKing.isInCheck(this); // update king pieces
            whiteKing.isInCheck(this);
            return true;
        } 
        else{
        // TODO Castle case
            return true;
        }
    }
    // public void determineStartingPiece(int row, int col){ Literal 2D array method makes more sense instead of computing something that will be the same every time
    //     switch (row){
    //         case 0: // black pieces
    //         switch(col){
    //             case 0: case 7: // black rooks
    //             board[row][col] = new Rook(Color.BLACK,row,col); blackPieces.add(board[row][col]); break;
    //             case 1: case 6: // black knights
    //             board[row][col] = new Knight(Color.BLACK,row,col); blackPieces.add(board[row][col]);break;
    //             case 2: case 5: // black bishops
    //             board[row][col] = new Bishop(Color.BLACK,row,col); blackPieces.add(board[row][col]);break;
    //             case 3: // black queen
    //             board[row][col] = new Queen(Color.BLACK,row,col); blackPieces.add(board[row][col]);break;
    //             case 4: // black king
    //             board[row][col] = new King(Color.BLACK,row,col); blackPieces.add(board[row][col]); break;
    //         }
    //         break;
    //         case 1: // black pawns
    //         board[row][col] = new Pawn(Color.BLACK,row,col); blackPieces.add(board[row][col]);break;
    //         case 2: case 3: case 4: case 5: // if position starts empty
    //         board[row][col] = null; break;
    //         case 6: // white pawns
    //         board[row][col] = new Pawn(Color.WHITE,row,col); whitePieces.add(board[row][col]);break;
    //         case 7: // white piecess
    //         switch(col){
    //             case 0: case 7: // white rooks
    //             board[row][col] = new Rook(Color.WHITE,row,col); whitePieces.add(board[row][col]); break;
    //             case 1: case 6: // white knights
    //             board[row][col] = new Knight(Color.WHITE,row,col); whitePieces.add(board[row][col]);break;
    //             case 2: case 5: // white bishops
    //             board[row][col] = new Bishop(Color.WHITE,row,col); whitePieces.add(board[row][col]); break;
    //             case 3: // white queen
    //             board[row][col] = new Queen(Color.WHITE,row,col); whitePieces.add(board[row][col]);break;
    //             case 4: // white king
    //             board[row][col] = new King(Color.WHITE,row,col); whitePieces.add(board[row][col]);break;
    //         }
    //         break;
    //     }
    // }
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
