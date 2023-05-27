package chess.Pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.Color;
import chess.Move;

public class Pawn extends DefaultPiece {
    private boolean hasMoved;
    public Pawn(Color color, int row, int col){
        super(color,Piece.PAWN,row,col);
        this.hasMoved = false;
    }
    public Move moveMaker(int spacesHorizontal, int spacesVertical){
        if (color == Color.WHITE){
            if (row-spacesVertical >= 0 && row-spacesVertical < 8 && col+spacesHorizontal >= 0 && col+spacesHorizontal < 8){
                return new Move(this.row-spacesVertical,this.col+spacesHorizontal,this);
            }
            else{
                return null;
            }
        }
        else{
            if (row+spacesVertical >= 0 && row+spacesVertical < 8 && col+spacesHorizontal >= 0 && col+spacesHorizontal < 8){
                return new Move(this.row+spacesVertical,this.col+spacesHorizontal,this);
            }
            else{
                return null;
            }
        }
    }
    @Override
    public Set<Move> getPossibleMoves(Board board) {
        // TODO implement en passant
        // forward for a pawn is adding 1 to the row for black, subtracting 1 for white
        possibleMoves = new HashSet<>();
        Move forwardOne = moveMaker(0,1);
        Move forwardTwo = moveMaker(0,2);
        Move captureLeft = moveMaker(-1,1);
        Move captureRight = moveMaker(1,1);

        if (forwardOne != null){
            if(board.checkAvailable(this.color,forwardOne.row,col) && board.getPiece(forwardOne.row,col) == null){ // check if the space ahead of the pawn is free and not a piece
                possibleMoves.add(forwardOne);
                if (!hasMoved){ // if the pawn hasnt moved
                    if(board.checkAvailable(this.color,forwardTwo.row, col) && board.getPiece(forwardTwo.row,col) == null){ // check two spaces ahead
                        possibleMoves.add(forwardTwo);
                    }
                }
            }
        }
        if (captureLeft != null){
            if (board.getPiece(captureLeft.row,captureLeft.col) != null && board.getPiece(captureLeft.row, col).getColor() != this.color){ // check for captures
                possibleMoves.add(captureLeft);
            }
        }
        if (captureRight != null){
            if (board.getPiece(captureRight.row,captureRight.col) != null && board.getPiece(captureRight.row, col).getColor() != this.color){ // check for captures
                possibleMoves.add(captureRight);
            }
        }
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
}
