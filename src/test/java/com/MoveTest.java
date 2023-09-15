package com;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.chess.*;
import com.chess.GUI.ChessGame;
import com.chess.Pieces.*;
public class MoveTest {
    public Board board = new Board(GameType.CLASSIC,null);
    @Test
    public void testEquality(){
        DefaultPiece pawn = new Pawn(Color.WHITE, 2, 3); 
        DefaultPiece pawnDifferentColor = new Pawn(Color.BLACK, 2, 3); // different color
        DefaultPiece rook = new Rook(Color.WHITE, 2, 3); // different piece
        DefaultPiece pawnSame = new Pawn(Color.WHITE, 2, 3); // same
        DefaultPiece pawnDifferentCol = new Pawn(Color.WHITE, 2, 4); // different coordinates
        Move pawnMove = new Move(2, 4, pawn, board);
        Move pawnMoveDifferentColor = new Move(2, 4, pawnDifferentColor, board);
        Move pawnMoveDifferentPieceType = new Move(2, 4, rook, board);
        Move pawnMoveSame = new Move(2, 4, pawnSame, board);
        Move pawnMoveDifferentPieceCol = new Move(2, 4, pawnDifferentCol, board);
        Move pawnMoveDifferentMove = new Move(2, 2, pawn, board);
        assertTrue(pawnMove.equals(pawnMoveSame));  // same piece
        assertFalse(pawnMove.equals(pawnMoveDifferentColor)); // diffent colors
        assertFalse(pawnMove.equals(pawnMoveDifferentPieceType)); // different pieces
        assertFalse(pawnMove.equals(null));  // piece with null
        assertFalse(pawnMove.equals(pawnMoveDifferentPieceCol)); // different coordinates
        assertFalse(pawnMove.equals(pawnMoveDifferentMove)); // same piece, different coordinates
    }
    @Test
    public void testHash(){
        DefaultPiece pawn = new Pawn(Color.WHITE, 2, 3);
        Move pawnMove = new Move(2, 3, pawn, board);
        Move pawnMoveSame = new Move(2, 3, pawn, board);
        Move pawnMoveDifferent = new Move(3, 3, pawn, board);
        assertTrue(pawnMove.hashCode() == pawnMoveSame.hashCode());
        assertFalse(pawnMove.hashCode() == pawnMoveDifferent.hashCode());
    }
    @Test
    public void testCompareTo(){
        DefaultPiece pawn = new Pawn(Color.WHITE, 1, 2);
        DefaultPiece knight = new Knight(Color.WHITE, 1, 2);
        DefaultPiece bishop = new Bishop(Color.WHITE, 1, 2);
        DefaultPiece rook = new Rook(Color.WHITE, 1, 2);
        DefaultPiece queen = new Queen(Color.WHITE, 1, 2);
        DefaultPiece king = new King(Color.WHITE, 1, 2);
        Move pawnMove = new Move(2, 2, pawn, board);
        Move knightMove = new Move(2, 2, knight, board);
        Move bishopMove = new Move(2, 2, bishop, board);
        Move rookMove = new Move(2, 2, rook, board);
        Move queenMove = new Move(2, 2, queen, board);
        Move kingMove = new Move(2, 2, king, board);
        Move pawnMoveGreaterCol = new Move(3, 3, pawn, board);
        Move pawnMoveGreaterRow = new Move(3, 2, pawn, board);
        assertTrue(pawnMove.compareTo(knightMove) < 0); // Pawn < Knight
        assertTrue(knightMove.compareTo(bishopMove) < 0); // Knight < Bishop
        assertTrue(bishopMove.compareTo(rookMove) < 0); // Bishop < Rook
        assertTrue(rookMove.compareTo(queenMove) < 0); // Rook < Queen
        assertTrue(queenMove.compareTo(kingMove) < 0); // Queen < King
        assertTrue(pawnMove.compareTo(pawnMoveGreaterCol) > 0); // if same piece, lowest col priority
        assertTrue(pawnMove.compareTo(pawnMoveGreaterRow) > 0); // if same piece and same col, lowest row priority
    }
    @Test
    public void testCastle(){
        // TODO test castle
        DefaultPiece whiteKing = new King(Color.WHITE,7,4);
        DefaultPiece blackKing = new King(Color.BLACK,0,4);
        
    }
}
