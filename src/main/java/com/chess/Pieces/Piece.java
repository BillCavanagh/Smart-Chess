package com.chess.Pieces;

public enum Piece {
    PAWN(1,'p'),BISHOP(4,'b'),KNIGHT(3,'n'),ROOK(5,'r'),QUEEN(9,'q'),KING(Integer.MAX_VALUE,'k');
    protected int value;
    protected char shorthand;
    private Piece(int value, char shorthand){
        this.value = value;
        this.shorthand = shorthand;
    }
    private Piece(char shorthand){
        this.shorthand = shorthand;
    }
    public char getShorthand(){
        return shorthand;
    }
    public int getValue(){
        return value;
    }
}
