package com.chess.Pieces;

import java.util.HashSet;
import java.util.Set;

import com.chess.Board;
import com.chess.Color;
import com.chess.Move;

public class King extends DefaultPiece{
    public King(Color color, int row, int col){
        super(color,Piece.KING,row,col);
    }
    public void checkCastleAvailable(Board board){
        // castling can either be short or long:
        // short and long castling has the king move 2 spaces towards whichever side
        // the rook then moves to the space opposite to the side of the king it was previously on
        // I.E rook position = king position +- 1 col
        if (!this.hasMoved){
            DefaultPiece Piece1 = board.getPiece(this.row,board.cols-1); 
            DefaultPiece Piece2 = board.getPiece(this.row,0);
            if (Piece1 instanceof Rook){
                Rook Rook1 = (Rook) Piece1;
                if (Rook1.color == this.color && !Rook1.hasMoved() && Rook1.getPossibleMoves(board).contains(new Move(this.row,this.col+1,Rook1,board))){ // short castle
                    possibleMoves.add(color == Color.WHITE ? board.whiteShort : board.blackShort);
                }
            }
            if (Piece2 instanceof Rook){
                Rook Rook2 = (Rook) Piece2;
                if (Rook2.color == this.color && !Rook2.hasMoved()  && Rook2.getPossibleMoves(board).contains(new Move(this.row,this.col-1,Rook2,board))){ // long castle
                    possibleMoves.add(color == Color.WHITE ? board.whiteLong : board.blackLong);
                }
            }
        }
    }
    @Override
    public Set<Move> getPossibleMoves(Board board){
        possibleMoves = new HashSet<>();
        for (int col = this.col-1; col <= this.col +1; col ++){ // check upper
            if (board.checkAvailable(this.color,this.row-1, col,true)){
                possibleMoves.add(new Move(this.row-1,col,this,board));
            }
        }
        for (int col = this.col-1; col <= this.col +1; col+=2){ // check horizontal
            if (board.checkAvailable(this.color,this.row, col,true)){
                possibleMoves.add(new Move(this.row,col,this,board));
            }
        }
        for (int col = this.col-1; col <= this.col +1; col ++){ // check lower
            if (board.checkAvailable(this.color,this.row+1, col,true)){
                possibleMoves.add(new Move(this.row+1,col,this,board));
            }
        }
        checkCastleAvailable(board);
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
    public static boolean canBlockCheck(Board board, Move move, DefaultPiece attacker, DefaultPiece king){ // kings can only move out of check
        return king.getColor() == Color.WHITE ? board.whiteKingAvailable[move.getRow()][move.getCol()] : board.blackKingAvailable[move.getRow()][move.getCol()];
    }
}
