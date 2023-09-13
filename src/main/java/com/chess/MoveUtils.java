package com.chess;

import java.util.ArrayList;
import java.util.Collections;

import com.chess.GUI.ChessGame;
import com.chess.Pieces.King;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
public class MoveUtils {
    public static String makeMoveList(Board board){
        String newText = "";
        if (board.getTurn() == com.chess.Color.WHITE){
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
     public static void processMove(Board board, ChessGame game, Move move){
        int row1 = move.getPiece().getRow();
        int col1 = move.getPiece().getCol();
        int row2 = move.getRow();
        int col2 = move.getCol();
        if (game.selectedMove == move){
            game.selectedMove = null;
            game.selectedPiece = null;
            try{board.makeMove(move,move.getPiece().getColor());}catch(Exception e){}
            game.chessBoard.add(game.getSpace(board.getPiece(row1, col1),game.getSpaceColor(row1, col1),row1,col1),col1,row1);
            game.chessBoard.add(game.getSpace(board.getPiece(row2, col2),game.getSpaceColor(row2, col2),row2,col2),col2,row2);
            game.updateLabels();
        }
        else{
            if (game.selectedMove != null){ // other move was selected previously, update highlights
                int rmRow1 = game.selectedMove.getRow();
                int rmCol1 = game.selectedMove.getCol();
                int rmRow2 = game.selectedPiece.getRow();
                int rmCol2 = game.selectedPiece.getCol();
                game.chessBoard.add(game.getSpace(board.getPiece(rmRow1, rmCol1),game.getSpaceColor(rmRow1, rmCol1),rmRow1,rmCol1),rmCol1,rmRow1);
                game.chessBoard.add(game.getSpace(board.getPiece(rmRow2, rmCol2),game.getSpaceColor(rmRow2, rmCol2),rmRow2,rmCol2),rmCol2,rmRow2);
            }
            game.selectedMove = move;
            game.selectedPiece = move.getPiece();
            game.chessBoard.add(game.getSpace(board.getPiece(row1, col1),ChessGame.HIGHLIGHT,row1,col1),col1,row1);
            game.chessBoard.add(game.getSpace(board.getPiece(row2, col2),ChessGame.HIGHLIGHT,row2,col2),col2,row2);
        }
        if (board.isCheckmate()){
            game.turn.setText("");
            game.check.setText("Checkmate, " + (board.getTurn() == com.chess.Color.WHITE ? "Black" : "White") + " wins!");
        }
        if (board.isStalemate()){
            game.turn.setText("");
            game.check.setText("Stalemate, " + "neither player wins");
        }
     }
     public static VBox makeInputMoveList(Board board, ChessGame game){
        VBox box = new VBox();
        ArrayList<Move> temp = new ArrayList<>(board.getTurn() == com.chess.Color.WHITE ? board.currentWhiteMoves : board.currentBlackMoves);
        Collections.sort(temp); 
        Color background = (board.getTurn() == com.chess.Color.WHITE ? ChessGame.LIGHT : ChessGame.DARK);
        Color text = (board.getTurn() == com.chess.Color.WHITE ? ChessGame.DARK : ChessGame.LIGHT);
        for (Move move : temp){
            Button button = new Button(move.toString());
            button.autosize();
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    processMove(board, game, move);
                }
            };
            button.setOnAction(event);
            button.setBackground(new Background(new BackgroundFill(background,CornerRadii.EMPTY,null)));
            button.setTextFill(text);
            button.setFont(new Font("Century",game.fontSize/2));
            button.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
            button.setPrefSize(ChessGame.BOARD_SIZE/6, 100/(temp.size()/4));
            box.getChildren().add(button);
        }
        return box;
     }
     public static Move parseMove(String input, Board board){ // index 0 = piece, index 1 = fromFile, index 2 = fromRank, index 3 = space, index 4 = toFile, index 5 = toRank
        // if (input.equals("test")){
        //     board.makeMove(move,board.getTurn())
        // }
        if (input.equals("O-O") || input.equals("0-0")){ // short castle
            com.chess.Color color = board.getTurn();
            King king;
            if (color == com.chess.Color.WHITE){
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
            com.chess.Color color = board.getTurn();
            King king;
            if (color == com.chess.Color.WHITE){
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
