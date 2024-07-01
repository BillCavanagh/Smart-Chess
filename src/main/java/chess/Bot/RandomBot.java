package chess.Bot;

import java.util.Set;
import java.util.Random;
import chess.*;
import chess.Pieces.Piece;

public class RandomBot extends Bot {
    private Random rand;
    public RandomBot(Board board, chess.Color color) {
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
    public Piece getPromotion(){
        int randomI = rand.nextInt(4);
        switch(randomI){
            case 0: return Piece.KNIGHT;
            case 1: return Piece.BISHOP;
            case 2: return Piece.ROOK;
            case 3: return Piece.QUEEN;
            default: return null;
        }
    }
}
