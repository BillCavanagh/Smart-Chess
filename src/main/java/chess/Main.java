package chess;

import java.util.Map;
import java.util.Scanner;

import chess.Pieces.DefaultPiece;

public class Main {
    public void gameLoop(Board board){
        int moveCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println(board);
            Map<DefaultPiece,Move> whiteMoves = board.determineCurrentPossibleMoves(Color.WHITE);
            Map<DefaultPiece,Move> blackMoves = board.determineCurrentPossibleMoves(Color.BLACK);
            if(moveCount % 2 == 0){
                System.out.println("White to move");
            }
            else{
                System.out.println("Black to move");
            }
            while (true){
                System.out.println("Enter move: ");
                String input = scanner.nextLine();
                if (input.length() != 3){
                    System.out.println("Invalid input try again");
                    continue;
                }
                char[] inputAsArray = input.toCharArray();

            }
        }
        scanner.close();
    }
    public static void main(String[] args){
        Board board = new Board();
        System.out.println(board);
    }
}
