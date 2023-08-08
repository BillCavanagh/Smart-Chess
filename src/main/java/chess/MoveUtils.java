package chess;

import java.util.ArrayList;
import java.util.Collections;

import chess.Pieces.King;

public class MoveUtils {
    public static String makeMoveList(Board board){
        String newText = "";
        if (board.getTurn() == chess.Color.WHITE){
            newText = newText + "White moves: \n";
            ArrayList<Move> temp = new ArrayList<>(board.currentWhiteMoves);
            Collections.sort(temp); 
            for (Move move : temp){
                newText += move.toString() + "\n";
            }
        }
        else{
            newText = newText + "Black moves: \n";
            ArrayList<Move> temp = new ArrayList<>(board.currentBlackMoves);
            Collections.sort(temp); 
            for (Move move : temp){
                newText += move.toString() + "\n";
            }
        }
        return newText;
     }
     public static Move parseMove(String input, Board board){ // index 0 = piece, index 1 = fromFile, index 2 = fromRank, index 3 = space, index 4 = toFile, index 5 = toRank
        // if (input.equals("test")){
        //     board.makeMove(move,board.getTurn())
        // }
        if (input.equals("O-O") || input.equals("0-0")){ // short castle
            chess.Color color = board.getTurn();
            King king;
            if (color == chess.Color.WHITE){
                king = board.whiteKing;
                if (king.getPossibleMoves(board).contains(board.whiteShort)){
                    return board.whiteShort;
                }
            }
            else{
                king = board.blackKing;
                if (king.getPossibleMoves(board).contains(board.blackShort)){
                    return board.blackShort;
                }
            }
            return null;
        }
        if (input.equals("O-O-O") || input.equals("0-0-0")){ // long castle
            chess.Color color = board.getTurn();
            King king;
            if (color == chess.Color.WHITE){
                king = board.whiteKing;
                if (king.getPossibleMoves(board).contains(board.whiteLong)){
                    return board.whiteShort;
                }
            }
            else{
                king = board.blackKing;
                if (king.getPossibleMoves(board).contains(board.blackLong)){
                    return board.blackShort;
                }
            }
            return null;
        }
        if (input.length() != 6){
            return null;
        }
        char pieceShorthand = input.charAt(0);
        int rowFrom = Board.rankToIndex(input.charAt(2)-48);
        int colFrom = Board.fileToIndex(input.charAt(1));
        if (board.getPiece(rowFrom,colFrom).getShorthand() != pieceShorthand){
            return null;
        }
        int rowTo = Board.rankToIndex(input.charAt(5)-48); 
        int colTo = Board.fileToIndex(input.charAt(4));
        Move move = new Move(rowTo,colTo,board.getPiece(rowFrom,colFrom));
        return move;
    }
}
