package chess.Pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.Color;
import chess.Move;
public abstract class DefaultPiece {
    protected Color color;
    protected Piece piece;
    protected int row;
    protected int col;
    protected Set<Move> possibleMoves;
    public DefaultPiece(Color color, Piece piece, int row, int col){
        this.color = color;
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.possibleMoves = new HashSet<>();
    }
    public Color getColor(){
        return color;
    }
    public int getValue(){
        return piece.value;
    }
    public char getShorthand(){
        return piece.shorthand;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }
   
    public abstract Set<Move> getPossibleMoves(Board board);
    public abstract String toString();
}   
