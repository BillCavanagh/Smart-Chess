package com;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.chess.*;
import com.chess.GUI.ChessGame;
import com.chess.Pieces.*;
import javafx.*;
public class PiecesTest {
    @Test
    public void testPieceEquality(){
        DefaultPiece piece1 = new Pawn(Color.WHITE, 2, 3);
        DefaultPiece piece2 = new Pawn(Color.WHITE, 2, 3); // same
        DefaultPiece piece3 = new Pawn(Color.BLACK, 2, 3); // different color
        DefaultPiece piece4 = new Pawn(Color.WHITE, 2, 4); // different coordinates
        DefaultPiece piece5 = new Rook(Color.WHITE, 2, 3); // different piece

        assertTrue(piece1.equals(piece2)); // same
        assertFalse(piece1.equals(piece3)); // different color
        assertFalse(piece1.equals(piece4)); // different coordinates
        assertFalse(piece1.equals(piece5)); // different piece
        assertFalse(piece1.equals(null));
    }
    @Test 
    public void testHash(){
        DefaultPiece piece1 = new Pawn(Color.WHITE, 2, 3);
        DefaultPiece piece2 = new Pawn(Color.WHITE, 2, 3);
        DefaultPiece piece3 = new Rook(Color.WHITE, 2, 3);
        assertTrue(piece1.hashCode() == piece2.hashCode());
        assertFalse(piece1.hashCode() == piece3.hashCode());
    }
}
