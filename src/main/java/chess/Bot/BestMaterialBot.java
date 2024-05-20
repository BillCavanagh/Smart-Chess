package chess.Bot;

import java.util.Set;

import chess.Board;
import chess.Color;
import chess.Move;
import chess.Bot.Position.BoardValue;
import chess.Bot.Position.BoardValueType;
import chess.Bot.Position.Position;
import chess.Pieces.DefaultPiece;

/**
 * Bot that chooses the move that leads to the best material advantage
 */
public class BestMaterialBot extends Bot{
    /**
     * The # of moves in the bot looks ahead, default is 1
     */
    int depth;
    public BestMaterialBot(Board board, Color color){
        super(board, color);
        depth = 1;
    }
    public BestMaterialBot(Board board, Color color, int depth){
        super(board, color);
        this.depth = depth;
    }
    public Move getNextMove(){
        // TODO implement depth
        Set<Move> moves = color == Color.WHITE ? board.currentWhiteMoves : board.currentBlackMoves;
        Move best = null;
        double max = -1;
        for (Move move : moves){
            int oldRow = move.getPiece().getRow();
            int oldCol = move.getPiece().getCol();
            DefaultPiece capturedPiece = board.getPiece(move.getRow(), move.getCol());
            board.makeTempMove(move);
            Position position = BoardValue.getPosition(board, BoardValueType.PIECES_VALUE);
            if (Math.abs(position.getAdvantage()) > max){
                max = Math.abs(position.getAdvantage());
                best = move;
            }
            board.undoTempMove(oldRow,oldCol,move.getPiece(),capturedPiece);
        }
        return best;
    }
}
