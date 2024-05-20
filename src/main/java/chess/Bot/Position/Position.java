package chess.Bot.Position;

import chess.Board;
import chess.Color;

/**
 * Class that contains information about a position
 */
public class Position {
    /**
     * The associated board state
     */
    public Board board;
    /**
     * The value of the position, negative indicates black advantage, positive indicates white advantage
     */
    public double advantage;
    /**
     * The algorithm that was used to determine the value of the position
     */
    public BoardValueType valueType;
    public Position(Board board, BoardValueType valueType){
        this.board = board;
        this.valueType = valueType;
    }
    public Position(Board board, BoardValueType valueType, double advantage){
        this.board = board;
        this.valueType = valueType;
        this.advantage = advantage;
    }
    public void setAdvantage(double advantage){
        this.advantage = advantage;
    }   
    public double getAdvantage(){
        return advantage;
    }

}
