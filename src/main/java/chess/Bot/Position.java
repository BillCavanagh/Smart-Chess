package chess.Bot;

import chess.Board;
import chess.Color;

/**
 * Class that contains information about a position with corresponding methods
 */
public class Position {s
    public Board board;
    public Color advantage;
    public double value;
    public BoardValueType valueType;
    public static BoardValueType valueTypes[] = BoardValueType.values();
    public Position(Board board, BoardValueType valueType){
        this.board = board;
        this.valueType = valueType;
    }

}
