package chess.old;

//import java.util.Scanner;
//import chess.Pieces.*;

public class CLI {
    // public static Board board = new Board();
    // public static Move parseInput(String input){ // index 0 = piece, index 1 = fromFile, index 2 = fromRank, index 3 = space, index 4 = toFile, index 5 = toRank
    //     if (input.length() != 6){
    //         return null;
    //     }
    //     char pieceShorthand = input.charAt(0);
    //     int rowFrom = Board.rankToIndex(input.charAt(2)-48);
    //     int colFrom = Board.fileToIndex(input.charAt(1));
    //     if (board.getPiece(rowFrom,colFrom).getShorthand() != pieceShorthand){
    //         return null;
    //     }
    //     int rowTo = Board.rankToIndex(input.charAt(5)-48); 
    //     int colTo = Board.fileToIndex(input.charAt(4));
    //     Move move = new Move(rowTo,colTo,board.getPiece(rowFrom,colFrom));
    //     return move;
    // }
    // public static void gameLoop(){
    //     int moveCount = 0;
    //     Scanner scanner = new Scanner(System.in);
    //     while (true){
    //         boolean white = false;
    //         System.out.println(board);
    //         board.updatePossibleMoves();
    //         if(moveCount % 2 == 0){
    //             System.out.println("White to move");
    //             white = true;
    //         }
    //         else{
    //             System.out.println("Black to move");
    //             white = false;
    //         }
    //         Move move = null;
    //         while (move == null){
    //             System.out.print("Enter move: "); 
    //             String input = scanner.nextLine();
    //             move = parseInput(input);
    //         }
    //         if (board.makeMove(move,white ? Color.WHITE : Color.BLACK)){
    //             moveCount++;
    //         }
    //         else{
    //             System.out.println("Invalid input try again (move not possible)");
    //         }
    //     }
    //     //scanner.close();
    // }
    // public static void main(String[] args){
    //     board = new Board();
    //     gameLoop();
    // }
}
