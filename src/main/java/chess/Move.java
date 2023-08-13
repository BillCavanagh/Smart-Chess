package chess;

import java.util.Objects;
import java.lang.Comparable;
import chess.Pieces.*;

public class Move implements Comparable<Move>{
    public int row;
    public int col;
    public DefaultPiece piece;
    public DefaultPiece piece2;
    public Board board;
    public Move(int row, int col, DefaultPiece piece, Board board){
        this.row = row;
        this.col = col;
        this.piece = piece;
        this.piece2 = null;
        this.board = board;
    }
    public Move(int row, int col, DefaultPiece piece, DefaultPiece other, Board board){ // castle move
        this.row = row;
        this.col = col;
        this.piece = piece;
        this.piece2 = other;
        this.board = board;
    }
    public DefaultPiece getPiece(){
        return piece;
    }
    public DefaultPiece getPiece2(){
        return piece2;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public boolean isCastle(){
        return piece2 instanceof Rook;
    }
    public boolean isEnPassant(){
        return piece2 instanceof Pawn;
    }
    public boolean isLongCastle(){
        return piece2 != null && this.col > piece2.getCol(); // long castle in when the rook is to the left of the king 
    }
    public String toString(){
        if (isCastle() && !isLongCastle()){
            return "O-O";
        }
        if (isLongCastle()){
            return "O-O-O";
        }
        return "" + piece.getShorthand() + board.indexToFile(piece.getCol()) + String.valueOf(board.indexToRank(piece.getRow())) + " " + 
        board.indexToFile(col) + String.valueOf(board.indexToRank(row));
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
                if (piece2 != null && other.piece2 != null){
                    return piece2.getRow() == other.piece2.getRow() && piece2.getCol() == other.piece2.getCol();
                }
                return piece2 == null && other.piece2 == null;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(row,col,piece);
    }
    @Override
    public int compareTo(Move o) {
        // a move should be less than an other move in accordance to these rules:
        // moves should be ordered by the piece that is moving, moves with the same piece will be treated as equal
        // piece priority: pawn < knight <= bishop < rook < queen < king
        // if the pieces are different, priority should be given to the piece in the lowest column, then the lowest row (closer to 0,0)
        DefaultPiece piece1 = this.piece;
        DefaultPiece piece2 = o.piece;
        if (piece1.equals(piece2)){ // if they equal return 0
            return 0;
        }
        if (piece1.getPiece().getShorthand() == piece2.getPiece().getShorthand()){ // if the pieces are the same type
            if (piece1.getCol() == piece2.getCol()){ // and if the pieces are in the same col
                return piece1.getRow() < piece2.getRow() ? -1 : 1; // check which one is closest to row 0
            }
            return piece1.getCol() < piece2.getCol() ? -1 : 1; // if they arent in the same col, check which one is closest to col 0
        }
        return piece1.getPiece().getValue() <= piece2.getPiece().getValue() ? -1 : 1; // if they arent the same type, check which one is the smallest value
    }
    // public static void main(String[] args) { old testing main
    //     Move move1 = new Move(1,1,new Pawn(Color.WHITE,1,1));
    //     Move move2 = new Move(1,1,new Pawn(Color.WHITE,1,1));
    //     HashSet<Move> moves = new HashSet<>();
    //     moves.add(move1);
    //     System.out.println(moves.contains(move2));
    // }
}
