package com;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.chess.*;
import com.chess.GUI.ChessGame;
import com.chess.Pieces.*;
import javafx.*;
public class MoveTest {
    public Board board = new Board(GameType.CLASSIC,null);
    @Test
    public void testEquality() {
        DefaultPiece piece1 = new Pawn(Color.WHITE, 2, 3); 
        DefaultPiece piece2 = new Pawn(Color.BLACK, 2, 3); // different color
        DefaultPiece piece3 = new Rook(Color.WHITE, 2, 3); // different piece
        DefaultPiece piece4 = new Pawn(Color.WHITE, 2, 3); // same
        DefaultPiece piece5 = new Pawn(Color.WHITE, 2, 4); // different coordinates
        Move move1 = new Move(2, 4, piece1, board);
        Move move2 = new Move(2, 4, piece2, board);
        Move move3 = new Move(2, 4, piece3, board);
        Move move4 = new Move(2, 4, piece4, board);
        Move move5 = new Move(2, 4, piece5, board);
        Move move6 = new Move(2, 2, piece1, board);
        assertTrue(move1.equals(move4));  // same piece
        assertFalse(move1.equals(move2)); // diffent colors
        assertFalse(move1.equals(move3)); // different pieces
        assertFalse(move1.equals(null));  // piece with null
        assertFalse(move1.equals(move5)); // different coordinates
        assertFalse(move1.equals(move6)); // same piece, different coordinates
    }
    @Test
    public void testHash() {
        DefaultPiece piece1 = new Pawn(Color.WHITE, 2, 3);
        DefaultPiece piece2 = new Pawn(Color.WHITE, 3, 3);
        Move move1 = new Move(2, 3, piece1, board);
        Move move2 = new Move(2, 3, piece1, board);
        Move move3 = new Move(3, 3, piece2, board);
        assertTrue(move1.hashCode() == move2.hashCode());
        assertFalse(move1.hashCode() == move3.hashCode());
    }
    @Test
    public void testCompareTo() {
        DefaultPiece pawn = new Pawn(Color.WHITE, 1, 2);
        DefaultPiece knight = new Knight(Color.WHITE, 1, 2);
        DefaultPiece bishop = new Bishop(Color.WHITE, 1, 2);
        DefaultPiece rook = new Rook(Color.WHITE, 1, 2);
        DefaultPiece queen = new Queen(Color.WHITE, 1, 2);
        DefaultPiece king = new King(Color.WHITE, 1, 2);
        Move move1 = new Move(2, 2, pawn, board);
        Move move2 = new Move(2, 2, knight, board);
        Move move3 = new Move(2, 2, bishop, board);
        Move move4 = new Move(2, 2, rook, board);
        Move move5 = new Move(2, 2, queen, board);
        Move move6 = new Move(2, 2, king, board);
        assertTrue(move1.compareTo(move2) < 0); // Pawn < Knight
        assertTrue(move2.compareTo(move3) < 0); // Knight < Bishop
        assertTrue(move3.compareTo(move4) < 0); // Bishop < Rook
        assertTrue(move4.compareTo(move5) < 0); // Rook < Queen
        assertTrue(move5.compareTo(move6) < 0); // Queen < King
    }
}
