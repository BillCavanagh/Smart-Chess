package chess.Pieces;

public enum Piece {
    PAWN(1,'p'),BISHOP(3,'b'),KNIGHT(3,'n'),ROOK(3,'r'),QUEEN(3,'q'),KING(3,'k');
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
