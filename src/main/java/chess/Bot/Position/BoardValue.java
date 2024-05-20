package chess.Bot.Position;

import chess.Board;
import chess.Color;
import chess.Pieces.*;

/**
 * Class that contains varying algorithms for determining the "value" of a board state for each player
 */
public class BoardValue {
    public static Position getPosition(Board board, BoardValueType valueType){
        Position position = new Position(board, valueType);
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
    public static double piecesValue(Board board){
        double value = 0;
        for (int row = 0; row < board.rows; row++){
            for (int col = 0; col < board.cols; col++){
                DefaultPiece piece = board.getPiece(row, col);
                if (piece != null && piece.getColor() == Color.WHITE){
                    value += piece.getValue();
                }
                if (piece != null && piece.getColor() == Color.BLACK){
                    value -= piece.getValue();
                }
            }
        }
        return value;
    }
}
