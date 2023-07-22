package chess.Pieces;

import java.util.HashSet;
import java.util.Set;
import chess.Board;
import chess.Color;
import chess.Move;

public class Rook extends DefaultPiece{
    private boolean hasMoved;
    public Rook(Color color, int row, int col){
        super(color,Piece.ROOK,row,col);
        this.hasMoved = false;
        this.possibleMoves = new HashSet<>();
    }
    @Override
    public Set<Move> getPossibleMoves(Board board){
        // TODO castling case, king discovery check case
        // top left = 0,0 
        possibleMoves = new HashSet<>();
        int[][] directions = {{-1, 0},{1,0},{0,-1},{0,1}}; // up, down, left, right
        for (int[] direction : directions){
            int row = this.row + direction[0];
            int col = this.col + direction[1];
            while (board.inBounds(row, col)){
                if (board.checkAvailable(this.color, row, col)){
                    possibleMoves.add(new Move(row,col,this));
                }
                else{
                    break;
                }
                row += direction[0];
                col += direction[1];
            }
        }
        return possibleMoves;
    }
    public void addPossibleMove(Move move){
        possibleMoves.add(move);
    }
    public boolean getHasMoved(){
        return hasMoved;
    }
    @Override
    public String toString() {
        return String.valueOf(piece.shorthand) + color.name().charAt(0);
    }
    public static boolean canBlockCheck(Board board, Move move, DefaultPiece attacker, DefaultPiece king){ // assumes the attacker is a rook
        int defRow = move.getRow();
        int defCol = move.getCol();
        int attRow = attacker.getRow();
        int attCol = attacker.getCol();
        int kingRow = king.getRow();
        int kingCol = king.getCol();
        if ((defRow == attRow && defCol == attCol)){ // is the move a capture? if so it can prevent check
            return true;
        } 
        if (attCol == defCol){ // if in the same col
            if (kingRow > attRow){ // if the king's row is greater than I.E lower than the attacking piece
                return defRow < kingRow && attRow < defRow; // the defRow must be between the kingRow and the attRow I.E less than kingRow but greater than att
            }
            else { // if it is less, it still must be inbetween but greater than kingRow but less than attRow
                return defRow > kingRow && attRow > defRow;
            }
        }  
        else if(attRow == defRow){ // if in the same row
            if (kingCol > attCol){
                return attCol < defCol && defCol < kingCol;
            }
            else{
                return attCol > defCol && defCol > kingCol;
            }
        }
        return false; // if neither it cannot block the check;
    }
}
