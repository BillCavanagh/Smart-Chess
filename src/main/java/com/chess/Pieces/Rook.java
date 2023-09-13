package com.chess.Pieces;

import java.util.HashSet;
import java.util.Set;

import com.chess.Board;
import com.chess.Color;
import com.chess.Move;

public class Rook extends DefaultPiece{
    public Rook(Color color, int row, int col){
        super(color,Piece.ROOK,row,col);
    }
    @Override
    public Set<Move> getPossibleMoves(Board board){
        // castle moves are managed through king 
        // top left = 0,0 
        possibleMoves = new HashSet<>();
        int[][] directions = {{-1, 0},{1,0},{0,-1},{0,1}}; // up, down, left, right
        for (int[] direction : directions){
            int row = this.row + direction[0];
            int col = this.col + direction[1];
            while (board.inBounds(row, col)){
                if (board.checkAvailable(this.color, row, col)){
                    possibleMoves.add(new Move(row,col,this,board));
                }
                if (!board.checkAvailable(this.color, row, col) || board.getPiece(row,col) != null){
                    break;
                }
                row += direction[0];
                col += direction[1];
            }
        }
        return possibleMoves;
    }
    public void addPossibleMove(Move move){
        possibleMoves.add(move);
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
}
