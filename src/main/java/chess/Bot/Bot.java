package chess.Bot;

import chess.*;
import chess.Pieces.Piece;

/**
 * Parent class for all difficulties of bot
 */
public abstract class Bot {
    /**
     * The color the bot is playing as
     */
    protected Color color;
    protected Board board;
    public Bot(Board board, Color color) {
        this.color = color;
        this.board = board;
    }
    public Color getColor(){
        return color;
    }
    public Board getBoard(){
        return board;
    }
    public abstract Move getNextMove();
    public abstract Piece getPromotion();
}
