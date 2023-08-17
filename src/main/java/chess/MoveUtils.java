package chess;

import java.util.ArrayList;
import java.util.Collections;

import chess.GUI.ChessGame;
import chess.Pieces.King;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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
     public static VBox makeInputMoveList(Board board, ChessGame game){
        VBox box = new VBox();
        ArrayList<Move> temp = new ArrayList<>(board.getTurn() == Color.WHITE ? board.currentWhiteMoves : board.currentBlackMoves);
        Collections.sort(temp); 
        for (Move move : temp){
            Button button = new Button(move.toString());
            button.autosize();
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (game.selectedMove == move){
                        try{board.makeMove(move,move.getPiece().getColor());}catch(Exception e){}
                        game.updateLabels();
                    }
                    game.updateSelectedMove(move);
                    if (board.isCheckmate()){
                        game.turn.setText("");
                        game.check.setText("Checkmate, " + (board.getTurn() == chess.Color.WHITE ? "Black" : "White") + " wins!");
                    }
                    if (board.isStalemate()){
                        game.turn.setText("");
                        game.check.setText("Stalemate, " + "neither player wins");
                    }
                }
            };
            button.setOnAction(event);
            box.getChildren().add(button);
        }
        return box;
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
        int rowFrom = board.rankToIndex(input.charAt(2)-48);
        int colFrom = board.fileToIndex(input.charAt(1));
        if (board.getPiece(rowFrom,colFrom).getShorthand() != pieceShorthand){
            return null;
        }
        int rowTo = board.rankToIndex(input.charAt(5)-48); 
        int colTo = board.fileToIndex(input.charAt(4));
        Move move = new Move(rowTo,colTo,board.getPiece(rowFrom,colFrom),board);
        return move;
    }
}
