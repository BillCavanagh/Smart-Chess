package chess.Pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.Color;
import chess.Move;

public class Bishop extends DefaultPiece{
    public Bishop(Color color, int row, int col){
        super(color,Piece.BISHOP,row,col);
        
    }
    @Override
    public Set<Move> getPossibleMoves(Board board){
        //top left = 0,0
        possibleMoves = new HashSet<>();
        int i = 0;
        while (this.row - i - 1 >= 0 && this.col - i - 1 >= 0){// top left diagonal rows down cols down
            i += 1;
            int row = this.row - i;
            int col = this.col - i;
            if (board.checkAvailable(this.color,row,col)){
                possibleMoves.add(new Move(row,col,this));
            }
            else{
                if(board.getPiece(row, col).getColor() != this.color){ 
                    possibleMoves.add(new Move(row,col,this));
                }
                break;
            }
            
        }
        i = 0;
        while (this.row - i - 1 >= 0 && this.col + i + 1 < Board.COLS){// top right diagonal rows down cols up
            i += 1;
            int row = this.row - i;
            int col = this.col + i;
            if (board.checkAvailable(this.color,row,col)){
                possibleMoves.add(new Move(row,col,this));
            }
            else{
                if(board.getPiece(row, col).getColor() != this.color){ 
                    possibleMoves.add(new Move(row,col,this));
                }
                break;
            }
            
        }
        i = 0;
        while (this.row + i + 1 < Board.ROWS && this.col - i - 1 >= 0){// bottom left diagonal rows up cols down
            i += 1;
            int row = this.row + i;
            int col = this.col - i;
            if (board.checkAvailable(this.color,row,col)){
                possibleMoves.add(new Move(row,col,this));
            }
            else{
                if(board.getPiece(row, col).getColor() != this.color){ 
                    possibleMoves.add(new Move(row,col,this));
                }
                break;
            }
            
        }
        i = 0;
        while (this.row + i + 1 < Board.ROWS && this.col + i + 1 < Board.COLS){// bottom right diagonal rows up cols up
            i += 1;
            int row = this.row + i;
            int col = this.col + i;
            if (board.checkAvailable(this.color,row,col)){
                possibleMoves.add(new Move(row,col,this));
            }
            else{
                if(board.getPiece(row, col).getColor() != this.color){ 
                    possibleMoves.add(new Move(row,col,this));
                }
                break;
            }
            
        }
        
        return possibleMoves;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
}
