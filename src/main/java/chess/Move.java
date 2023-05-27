package chess;

import chess.Pieces.DefaultPiece;

public class Move {
    public int row;
    public int col;
    public DefaultPiece piece;
    public boolean isCastle;
    public Move(int row, int col, DefaultPiece piece){
        this.row = row;
        this.col = col;
        this.piece = piece;
    }
    public Move(int row, int col,boolean isCastle){
        this.row = row;
        this.col = col;
        this.isCastle = isCastle;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
    public boolean isCastle(){
        return isCastle;
    }
    public String toString(){
        return piece.getShorthand() + Board.indexToFile(col) + String.valueOf(Board.indexToRank(row));
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move){
            Move other = (Move) obj;
            if (this.row == other.row && this.col == other.col){
                return true;
            }
        }
        return false;
    }
}
