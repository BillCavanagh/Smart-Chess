package com;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.chess.*;
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
    @Test
    public void testBishop(){
        Board board = new Board(GameType.CLASSIC,null);
        Bishop bishop = new Bishop(Color.WHITE, 5, 2);
        board.addPiece(bishop);
        Set<Move> moves = bishop.getPossibleMoves(board);
        assertTrue(moves.size() == 6);
        // moves that are captures or empty space
        assertTrue(moves.contains(new Move(3,0,bishop,board)));
        assertTrue(moves.contains(new Move(4,1,bishop,board)));
        assertTrue(moves.contains(new Move(4,3,bishop,board)));
        assertTrue(moves.contains(new Move(3,4,bishop,board)));
        assertTrue(moves.contains(new Move(2,5,bishop,board)));
        assertTrue(moves.contains(new Move(1,6,bishop,board)));
        // moves that are blocked by friendly pieces
        assertFalse(moves.contains(new Move(6,1,bishop,board)));
        assertFalse(moves.contains(new Move(6,3,bishop,board)));
        // move out of bounds
        assertFalse(moves.contains(new Move(2,-1,bishop,board)));
    }
    @Test 
    public void testKnight(){
        Board board = new Board(GameType.CLASSIC,null);
        Knight knight = new Knight(Color.WHITE, 5, 2);
        board.addPiece(knight);
        Set<Move> moves = knight.getPossibleMoves(board);
        assertTrue(moves.size() == 4);
        // moves that are empty spaces
        assertTrue(moves.contains(new Move(3,1,knight,board)));
        assertTrue(moves.contains(new Move(3,3,knight,board)));
        assertTrue(moves.contains(new Move(4,0,knight,board)));
        assertTrue(moves.contains(new Move(4,4,knight,board)));
        // moves blocked by friendly pieces
        assertFalse(moves.contains(new Move(6,1,knight,board)));
        assertFalse(moves.contains(new Move(6,3,knight,board)));
        assertFalse(moves.contains(new Move(7,1,knight,board)));
        assertFalse(moves.contains(new Move(7,3,knight,board)));
    }
    @Test 
    public void testPawn(){
        Board board = new Board(GameType.CLASSIC,null);
        Pawn unMovedPawn = (Pawn)board.getPiece(6,2);
        Pawn movedPawn = new Pawn(Color.WHITE, 3, 3);
        board.addPiece(movedPawn);
        movedPawn.hasMoved = true;
        // check that the pawn can move 2 spaces on first move
        Set<Move> unMovedPawnMoves = unMovedPawn.getPossibleMoves(board);
        assertTrue(unMovedPawnMoves.size() == 2);
        assertTrue(unMovedPawnMoves.contains(new Move(4,2,unMovedPawn,board)));
        assertTrue(unMovedPawnMoves.contains(new Move(5,2,unMovedPawn,board)));
        // check that the pawn cant move 2 spaces on first move
        Set<Move> movedPawnMoves = movedPawn.getPossibleMoves(board);
        assertTrue(movedPawnMoves.size() == 1);
        // check pawn captures
        Pawn pawnCapture = new Pawn(Color.BLACK, 2, 2);
        board.addPiece(pawnCapture);
        movedPawnMoves = movedPawn.getPossibleMoves(board);
        assertTrue(movedPawnMoves.size() == 2);
        assertTrue(movedPawnMoves.contains(new Move(2,2,movedPawn,board)));
        // check en passant
        DefaultPiece pawnEnPassant = board.getPiece(1,4);
        // make random move so its blacks turn
        board.makeMove(new Move(5,2,unMovedPawn,board),Color.WHITE);
        board.makeMove(new Move(3,4,pawnEnPassant,board),Color.BLACK);
        // check if en passant recognize
        movedPawnMoves = movedPawn.getPossibleMoves(board);
        assertTrue(movedPawnMoves.size() == 3);
        assertTrue(movedPawnMoves.contains(new Move(2,4,movedPawn,pawnEnPassant,board)));
        // make random move to ensure en passant doesnt persist
        board.makeMove(new Move(4,2,unMovedPawn,board), Color.WHITE);
        // this move chosen to ensure en passant cant occur when the pawn only moved one at a time
        board.makeMove(new Move(3,2,pawnCapture,board), Color.BLACK);
        movedPawnMoves = movedPawn.getPossibleMoves(board);
        assertTrue(movedPawnMoves.size() == 1);
    }
}
