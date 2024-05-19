package chess.Bot;

import chess.Board;
import chess.Color;
import chess.Pieces.*;

/**
 * Class that contains varying algorithms for determining the "value" of a board state for each player
 */
public class BoardValue {
    public Position getPosition(Board board, BoardValueType valueType){
        Position position = new Position(board, valueType);
        int[] temp = new int[2];
        switch(valueType){
            case PIECES_VALUE:
                position.setAdvantage(piecesValue(board));
            default:
                position.setAdvantage(0);
        }
        return position;
    }
    /**
     * Determines the value for a position based on raw value of pieces
     * <p> iterates through all pieces 
     */
    public double piecesValue(Board board){
        double value = 0;
        for(DefaultPiece piece : board.whitePieces){
            if (!(piece instanceof King)){
                value += piece.getValue();
            }
        }
        for(DefaultPiece piece : board.blackPieces){
            if (!(piece instanceof King)){
                value -= piece.getValue();
            }
        }
        return value;
    }
}
