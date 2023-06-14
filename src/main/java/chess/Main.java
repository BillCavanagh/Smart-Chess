package chess;

import java.util.Map;
import java.util.Scanner;

import chess.Pieces.*;

public class Main {
    public void gameLoop(Board board){
        int moveCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println(board);
            board.updatePossibleMoves();
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
                Move move = new Move((int)inputAsArray[0],(int)inputAsArray[1],);

            }
        }
        scanner.close();
    }
    public static void main(String[] args){
        Board board = new Board();
        System.out.println(board);
    }
}
