package chess.Pieces;

import java.util.HashSet;
import java.util.Set;
import chess.Board;
import chess.Color;
import chess.Move;

public class Bishop extends DefaultPiece{
    public Bishop(Color color, int row, int col){
        super(color,Piece.BISHOP,row,col);
    }
    @Override
    public Set<Move> getPossibleMoves(Board board){
        //top left = 0,0
        possibleMoves = new HashSet<>();
        int[][] directions = {{1, 1},{-1, 1},{1, -1},{-1, -1}};
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
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
}
