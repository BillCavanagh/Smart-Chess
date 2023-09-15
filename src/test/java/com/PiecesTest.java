package com;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.chess.*;
import com.chess.GUI.ChessGame;
import com.chess.Pieces.*;
public class PiecesTest {
    @Test
    public void testPieceEquality(){
        DefaultPiece pawn = new Pawn(Color.WHITE, 2, 3);
        DefaultPiece pawnSame = new Pawn(Color.WHITE, 2, 3); // same
        DefaultPiece pawnDifferentColor = new Pawn(Color.BLACK, 2, 3); // different color
        DefaultPiece pawnDifferentCoordinates = new Pawn(Color.WHITE, 2, 4); // different coordinates
        DefaultPiece rook = new Rook(Color.WHITE, 2, 3); // different piece

        assertTrue(pawn.equals(pawnSame)); // same
        assertFalse(pawn.equals(pawnDifferentColor)); // different color
        assertFalse(pawn.equals(pawnDifferentCoordinates)); // different coordinates
        assertFalse(pawn.equals(rook)); // different piece
        assertFalse(pawn.equals(null));
    }
    @Test 
    public void testHash(){
        DefaultPiece pawn = new Pawn(Color.WHITE, 2, 3);
        DefaultPiece pawnSame = new Pawn(Color.WHITE, 2, 3);
        DefaultPiece rook = new Rook(Color.WHITE, 2, 3);
        assertTrue(pawn.hashCode() == pawnSame.hashCode());
        assertFalse(pawn.hashCode() == rook.hashCode());
    }
}
