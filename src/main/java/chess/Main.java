package chess;

import java.util.Map;
import java.util.Scanner;

import chess.Pieces.*;

public class Main {
    public static void gameLoop(Board board){
        int moveCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (true){
            boolean white = false;
            System.out.println(board);
            board.updatePossibleMoves();
            if(moveCount % 2 == 0){
                System.out.println("White to move");
                white = true;
            }
            else{
                System.out.println("Black to move");
                white = false;
            }
            while (true){
                System.out.print("Enter move: "); 
                String input = scanner.nextLine();
                if (input.length() != 6){
                    System.out.println("Invalid input try again (wrong length)");
                    continue;
                }
                DefaultPiece movePiece = board.getPiece(Board.fileToIndex(input.charAt(1)),Board.rankToIndex(input.charAt(2)-48));
                boolean pieceCorrect = false;
                for (Piece piece : Piece.values()){
                    if (piece.getShorthand() == input.charAt(0)){
                        pieceCorrect = true;
                        break;
                    }
                }
                if (!pieceCorrect){
                    System.out.println("Invalid input try again (wrong piece)");
                    continue;
                }
                Move move = new Move(Board.fileToIndex(input.charAt(3)),Board.rankToIndex((int)input.charAt(4)-48),movePiece);
                for (Move move1 : board.currentWhiteMoves){
                    System.out.println(move);
                }
                if (board.makeMove(move,white ? Color.WHITE : Color.BLACK)){
                    break;
                }
                else{
                    System.out.println("Invalid input try again (move not possible)");
                }
            }
        }
        //scanner.close();
    }
    public static void main(String[] args){
        Board board = new Board();
        gameLoop(board);
    }
}
