package chess.Pieces;

import java.util.HashSet;
import java.util.Set;
import chess.Board;
import chess.Color;
import chess.Move;
public class Pawn extends DefaultPiece {
    private boolean justMovedTwo;
    public Pawn(Color color, int row, int col){
        super(color,Piece.PAWN,row,col);
        justMovedTwo = false;
    }
    public boolean canPromote(){
        return row == (color == Color.WHITE ? 0 : 7); // end of the board for white pawns is row 0, for black pawns row 7
    }
    public void promote(Board board, Piece newPiece){ // return true if pawn replaced with promoted piece, false if not
        DefaultPiece replacement = null;
        switch(newPiece){
            case KNIGHT: replacement = new Knight(color,row,col); break;
            case BISHOP: replacement = new Bishop(color,row,col); break;
            case ROOK: replacement = new Rook(color,row,col); replacement.hasMoved = true;break;
            case QUEEN: replacement = new Queen(color,row,col);
            default: return; // newPiece is a pawn or king, neither are valid to promote to
        }
        board.setPiece(replacement,row,col); // replace the pawn with the new piece
        if (color == Color.WHITE){
            board.whitePieces.remove(this);
            board.whitePieces.add(replacement);
        }
        else{
            board.blackPieces.remove(this);
            board.blackPieces.add(replacement);
        }
    }
    public void checkEnPassant(Board board){
        DefaultPiece piece1 = board.getPiece(this.row,this.col+1); // check the spaces to the left and right of the pawn for other pawns
        DefaultPiece piece2 = board.getPiece(this.row,this.col-1);
        if (piece1 instanceof Pawn){
            Pawn pawn1 = (Pawn) piece1;
            int row = this.row + (color == Color.WHITE ? -1 : 1);
            if (board.getLastMove() != null && board.getLastMove().getPiece().equals(pawn1) && board.checkAvailable(color, row, col+1)){ 
                possibleMoves.add(new Move(row,col+1,this,pawn1,board));
            }
        }
        if (piece2 instanceof Pawn){
            Pawn pawn2 = (Pawn) piece2;
            int row = this.row + (color == Color.WHITE ? -1 : 1);
            if (board.getLastMove() != null && board.getLastMove().getPiece().equals(pawn2) && board.checkAvailable(color, row, col-1)){
                possibleMoves.add(new Move(row,col-1,this,pawn2,board));
            }
        }
    }
    @Override
    public Set<Move> getPossibleMoves(Board board) {
        // forward for a pawn is adding 1 to the row for black, subtracting 1 for white
        possibleMoves = new HashSet<>();
        Move forwardOne = new Move(row + (color == Color.WHITE ? -1 : 1), col,this,board);
        Move forwardTwo = new Move(row + (color == Color.WHITE ? -2 : 2),col,this,board);
        Move captureLeft = new Move(row + (color == Color.WHITE ? -1 : 1),col+1,this,board);
        Move captureRight = new Move(row + (color == Color.WHITE ? -1 : 1),col-1,this,board);
        if (board.getPiece(forwardOne.getRow(),forwardOne.getCol()) == null && board.checkAvailable(color, forwardOne.getRow(), forwardOne.getCol())){ // check space in front
            possibleMoves.add(forwardOne);
            if (board.getPiece(forwardTwo.getRow(),forwardTwo.getCol()) == null && !hasMoved && board.checkAvailable(color, forwardTwo.getRow(), forwardTwo.getCol())){ // only if space in front is available, check forward two
                possibleMoves.add(forwardTwo);
            }
        }
        if (board.checkAvailable(color, captureLeft.getRow(), captureLeft.getCol()) && board.getPiece(captureLeft.getRow(),captureLeft.getCol()) != null){
            possibleMoves.add(captureLeft);
        }
        if (board.checkAvailable(color, captureRight.getRow(), captureRight.getCol()) && board.getPiece(captureRight.getRow(),captureRight.getCol()) != null){
            possibleMoves.add(captureLeft);
        }
        // check for en passant
        checkEnPassant(board);
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
    // public boolean justMovedTwo(){
    //     return justMovedTwo;
    // }
    // public void move(Move move){
    //     if (hasMoved){
    //         justMovedTwo = false;
    //     }
    //     else{
    //         hasMoved = true;
    //         if (Math.abs(move.getRow()-row) == 2){
    //             justMovedTwo = true;
    //         }
    //     }
    //     setRow(move.getRow()); 
    //     setCol(move.getCol());
    // }
}
