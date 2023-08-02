package chess;

import java.util.HashSet;
import java.util.Objects;

import chess.Pieces.*;

public class Move {
    public int row;
    public int col;
    public DefaultPiece piece;
    public DefaultPiece other;
    public Move(int row, int col, DefaultPiece piece){
        this.row = row;
        this.col = col;
        this.piece = piece;
        this.other = null;
    }
    public Move(int row, int col, DefaultPiece piece, DefaultPiece other){ // castle move
        this.row = row;
        this.col = col;
        this.piece = piece;
        this.other = other;
    }
    public DefaultPiece getOther(){
        return other;
    }
    public DefaultPiece getPiece(){
        return piece;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public boolean isCastle(){
        return other instanceof Rook;
    }
    public boolean isEnPassant(){
        return other instanceof Pawn;
    }
    public boolean isLongCastle(){
        return other != null && this.col > other.getCol(); // long castle in when the rook is to the left of the king 
    }
    public String toString(){
        return "" + piece.getShorthand() + Board.indexToFile(piece.getCol()) + String.valueOf(Board.indexToRank(piece.getRow())) + " " + 
        Board.indexToFile(col) + String.valueOf(Board.indexToRank(row));
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public void setPiece(DefaultPiece piece){
        this.piece = piece;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move){
            Move other = (Move) obj;
            if (this.row == other.row && this.col == other.col && this.piece.equals(other.piece)){
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(row,col,piece);
    }
    public static void main(String[] args) {
        Move move1 = new Move(1,1,new Pawn(Color.WHITE,1,1));
        Move move2 = new Move(1,1,new Pawn(Color.WHITE,1,1));
        HashSet<Move> moves = new HashSet<>();
        moves.add(move1);
        System.out.println(moves.contains(move2));
    }
}
