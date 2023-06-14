package chess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import chess.Pieces.*;

public class Board {
    
    protected DefaultPiece[][] board;
    public static final int ROWS = 8;
    public static final int COLS = 8;
    protected List<DefaultPiece> whitePieces;
    protected List<DefaultPiece> blackPieces;
    protected Set<Move> currentWhiteMoves;
    protected Set<Move> currentBlackMoves;

    public Board(){
        this.whitePieces = new ArrayList<>();
        this.blackPieces = new ArrayList<>();
        this.currentBlackMoves = new HashSet<>();
        this.currentWhiteMoves = new HashSet<>();
        init_Board();
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
    }
    public static int indexToRank(int index){ // index 0 = rank 8, index 1 = rank 7 ect
        return Math.abs(index - 8);
    }
    public static char indexToFile(int index){ // index 0 = file a, index 1 = file b ect
        return (char)(97 + index);
    }
    private boolean checkInBounds(int row, int col){
        return row <= 7 && col <= 7 && row >= 0 && col >= 0;
    }
    public DefaultPiece getPiece(int row, int col){
        return board[row][col];
    }
    public void setPiece(DefaultPiece piece, int row, int col){
        board[row][col] = piece;
    }
    public void removePiece(int row, int col){
        board[row][col] = null;
    }
    public boolean checkAvailable(Color color,int row, int col){
        if (!this.checkInBounds(row, col)){
            return false;
        }
        if (this.getPiece(row,col) == null){ // if the square is empty or is a piece of a different color it is a valid move
            return true;
        }
        else{
            if (color != this.getPiece(row,col).getColor()){
                return true;
            }
        }
        return false;
    }
    public void updatePossibleMoves(){
        for (DefaultPiece piece : blackPieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                currentBlackMoves.add(move);
            }
        }
        for (DefaultPiece piece : whitePieces){
            Set<Move> pieceMoves = piece.getPossibleMoves(this);
            for (Move move : pieceMoves){
                currentWhiteMoves.add(move);
            }
        }
    }
    public boolean makeMove(Move move, Color color){
        if (color == Color.BLACK){
            if (currentBlackMoves.contains(move)){
                if (!move.isCastle()){
                    board[move.getRow()][move.getCol()] = move.getPiece(); // make the move 
                    board[move.getPiece().getRow()][move.getPiece().getCol()] = null; // update the old position
                    move.getPiece().setRow(move.getRow()); // update the piece's row
                    move.getPiece().setCol(move.getCol()); // update the piece's row
                    return true;
                }
            }
        }
        else{
            if (currentWhiteMoves.contains(move)){
                if (!move.isCastle()){
                    board[move.getRow()][move.getCol()] = move.getPiece(); // make the move 
                    board[move.getPiece().getRow()][move.getPiece().getCol()] = null; // update the old position
                    move.getPiece().setRow(move.getRow()); // update the piece's row
                    move.getPiece().setCol(move.getCol()); // update the piece's row
                    return true;
                }
            }
        }
        return false;
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
