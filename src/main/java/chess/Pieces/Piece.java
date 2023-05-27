package chess.Pieces;

public enum Piece {
    PAWN(1,'P'),BISHOP(3,'B'),KNIGHT(3,'N'),ROOK(3,'R'),QUEEN(3,'Q'),KING(3,'K');
    protected int value;
    protected char shorthand;
    private Piece(int value, char shorthand){
        this.value = value;
        this.shorthand = shorthand;
    }
}
