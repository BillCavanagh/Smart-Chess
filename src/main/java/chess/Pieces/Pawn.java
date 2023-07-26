package chess.Pieces;

import java.util.HashSet;
import java.util.Set;
import chess.Board;
import chess.Color;
import chess.Move;

public class Pawn extends DefaultPiece {
    public Pawn(Color color, int row, int col){
        super(color,Piece.PAWN,row,col);
    }
    @Override
    public Set<Move> getPossibleMoves(Board board) {
        // TODO implement en passant
        // forward for a pawn is adding 1 to the row for black, subtracting 1 for white
        possibleMoves = new HashSet<>();
        Move forwardOne = new Move(row + (color == Color.WHITE ? -1 : 1), col,this);
        Move forwardTwo = new Move(row + (color == Color.WHITE ? -2 : 2),col,this);
        Move captureLeft = new Move(row + (color == Color.WHITE ? -1 : 1),col+1,this);
        Move captureRight = new Move(row + (color == Color.WHITE ? -1 : 1),col-1,this);
        if (board.checkAvailable(color, forwardOne.getRow(), forwardOne.getCol())){ // check space in front
            possibleMoves.add(forwardOne);
            if (!hasMoved && board.checkAvailable(color, forwardTwo.getRow(), forwardTwo.getCol())){ // only if space in front is available, check forward two
                possibleMoves.add(forwardTwo);
            }
        }
        if (board.checkAvailable(color, captureLeft.getRow(), captureLeft.getCol()) && board.getPiece(captureLeft.getRow(),captureLeft.getCol()) != null){
            possibleMoves.add(captureLeft);
        }
        if (board.checkAvailable(color, captureRight.getRow(), captureRight.getCol()) && board.getPiece(captureRight.getRow(),captureRight.getCol()) != null){
            possibleMoves.add(captureLeft);
        }
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
}
