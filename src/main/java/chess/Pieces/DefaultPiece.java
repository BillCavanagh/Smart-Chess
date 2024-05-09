package chess.Pieces;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import chess.Board;
import chess.Color;
import chess.Move;
/**
 * Parent class for all pieces
 */
public abstract class DefaultPiece {
    /**
     * The color of the piece (WHITE or BLACK)
     */
    protected Color color;
    /**
     * The type of piece (PAWN, BISHOP, KNIGHT, ROOK, QUEEN, KING)
     */
    protected Piece piece;
    /**
     * The row position of the piece (index in 2D array, not chess notation)
     */
    protected int row;
    /**
     * The column position of the piece (index in 2D array, not chess notation)
     */
    protected int col;
    /**
     * The set of possible moves the piece can make in its current position 
     * <p>
     * Contains only legal moves
     * <p>
     * The set being empty implies the piece cannot move
     */
    protected Set<Move> possibleMoves;
    /**
     * Whether the piece has moved or not
     */
    public boolean hasMoved;
    public DefaultPiece(Color color, Piece piece, int row, int col){
        this.color = color;
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.possibleMoves = new HashSet<>();
        this.hasMoved = false;
    }
    public boolean hasMoved(){
        return hasMoved;
    }
    public Color getColor(){
        return color;
    }
    public double getValue(){
        return piece.value;
    }
    public char getShorthand(){
        return piece.shorthand;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public Piece getPiece(){
        return piece;
    }
   @Override
   public boolean equals(Object obj) {
        if (obj instanceof DefaultPiece){
            DefaultPiece other = (DefaultPiece) obj;
            if (this.row == other.row && this.col == other.col && this.color.equals(other.color) && this.piece.equals(other.piece)){
                return true;
            }
        }       
        return false;
   }
   @Override
   public int hashCode() {
       return Objects.hash(row,col,piece,color);
   }
    /**
     * Used to generate and get all possible moves for the piece on a given board
     */
    public abstract Set<Move> getPossibleMoves(Board board);
    public abstract String toString();
    /**
     * Makes a move assuming the move is valid 
     */
    public void move(Move move){
        setRow(move.getRow()); 
        setCol(move.getCol());
        hasMoved = true;
    }
}   
