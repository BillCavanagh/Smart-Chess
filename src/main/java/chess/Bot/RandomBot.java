package chess.Bot;

import java.util.Set;
import java.util.Random;
import chess.*;

public class RandomBot extends Bot {
    private Random rand;
    public RandomBot(Board board, Color color) {
        super(board,color);
        rand = new Random();
    }
    public Move getNextMove(){
        Set<Move> moves = color == Color.WHITE ? board.currentWhiteMoves : board.currentBlackMoves;
        int randomI = rand.nextInt(moves.size());
        int i = 0;
        for (Move move : moves){
            if (i == randomI){
                return move;
            }
            i++;
        }
        return null;
    }   
}
