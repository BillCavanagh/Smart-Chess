package chess.Pieces;

import java.util.HashSet;
import java.util.Set;
import chess.Board;
import chess.Color;
import chess.Move;

public class Knight extends DefaultPiece{
    public Knight(Color color, int row, int col){
        super(color,Piece.KNIGHT,row,col);
    }
    @Override
    public Set<Move> getPossibleMoves(Board board) {
        // Knight possible moves are away by 2 in one direction and away by 1 in the other direction, 2 rows/1 col for example
        possibleMoves = new HashSet<>();
        int[][] positions = {{-2,-1},{-2,1},{-1,2},{1,2},{2,1},{2,-1},{1,-2},{-1,-2}};
        for (int[] pos : positions){
            int row = this.row + pos[0];
            int col = this.col + pos[1];
            if (board.checkAvailable(this.color, row, col)){
                possibleMoves.add(new Move(row,col,this,board));
            }
        }
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
}
