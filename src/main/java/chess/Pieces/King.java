package chess.Pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.Color;
import chess.Move;

public class King extends DefaultPiece{
    private boolean hasMoved;
    private boolean isInCheck;
    public King(Color color, int row, int col){
        super(color,Piece.KING,row,col);
        this.hasMoved = false;
        this.isInCheck = false;
    }
    public void checkCastleAvailable(Board board){
        if (!this.hasMoved){
            DefaultPiece Piece1 = board.getPiece(this.row,this.col+3);
            DefaultPiece Piece2 = board.getPiece(this.row,this.col-4);
            if (Piece1 instanceof Rook){
                Rook Rook1 = (Rook) Piece1;
                if (Rook1.color == this.color && Rook1.getHasMoved() && Rook1.getPossibleMoves(board).contains(new Move(this.row,this.col,this))){
                    possibleMoves.add(new Move(this.row,this.col+2,true));
                    Rook1.addPossibleMove(new Move(this.row,this.col-2,true));
                }
            }
            if (Piece2 instanceof Rook){
                Rook Rook2 = (Rook) Piece2;
                if (Rook2.color == this.color && !Rook2.getHasMoved() && Rook2.getPossibleMoves(board).contains(new Move(this.row,this.col,this))){
                    possibleMoves.add(new Move(this.row,this.col-2,true));
                    Rook2.addPossibleMove(new Move(this.row,this.col+3,true));
                }
            }
        }
    }
    @Override
    public Set<Move> getPossibleMoves(Board board) {
        // TODO allow check if possible moves run into checks (might be tough to do efficiently, maybe mantain all squares that are covered by enemy pieces?)
        possibleMoves = new HashSet<>();
        for (int col = this.col-1; col <= this.col +1; col ++){ // check upper
            if (board.checkAvailable(this.color,this.row-1, col)){
                possibleMoves.add(new Move(this.row-1,col,this));
            }
        }
        for (int col = this.col-1; col <= this.col +1; col+=2){ // check horizontal
            if (board.checkAvailable(this.color,this.row, col)){
                possibleMoves.add(new Move(this.row,col,this));
            }
        }
        for (int col = this.col-1; col <= this.col +1; col ++){ // check lower
            if (board.checkAvailable(this.color,this.row+1, col)){
                possibleMoves.add(new Move(this.row+1,col,this));
            }
        }
        checkCastleAvailable(board);
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
}
