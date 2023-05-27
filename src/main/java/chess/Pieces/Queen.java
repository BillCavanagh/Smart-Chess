package chess.Pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.Color;
import chess.Move;

public class Queen extends DefaultPiece{
    public Queen(Color color, int row, int col){
        super(color,Piece.QUEEN,row,col);
    }
    @Override
    public Set<Move> getPossibleMoves(Board board){
        possibleMoves = new HashSet<>();
        Rook rook = new Rook(this.color,this.row,this.col);
        Bishop bishop = new Bishop(this.color,this.row,this.col);
        possibleMoves.addAll(rook.getPossibleMoves(board));
        possibleMoves.addAll(bishop.getPossibleMoves(board));
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }

}
