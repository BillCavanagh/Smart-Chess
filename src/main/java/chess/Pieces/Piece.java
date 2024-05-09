package chess.Pieces;
/**
 * Enum that contains standard values and piece shorthands
 * <p>
 * Piece.value (double) = value of the piece
 * <p>
 * Piece.shorthand (char) = shorthand character for the piece
 */
public enum Piece {
    /**
     * Value: 1
     * <p>
     * Shorthand: p
     */
    PAWN(1,'p'),
    /**
     * Value: 3.5
     * <p>
     * Shorthand: b
     */
    BISHOP(3.5,'b'),
    /**
     * Value: 3
     * <p>
     * Shorthand: n
     */
    KNIGHT(3,'n'),
    /**
     * Value: 5
     * <p>
     * Shorthand: r
     */
    ROOK(5,'r'),
    /**
     * Value: 9
     * <p>
     * Shorthand: q
     */
    QUEEN(9,'q'),
    /**
     * Value: Integer.MAX_VALUE (effectively infinite)
     * <p>
     * Shorthand: k
     */
    KING(Integer.MAX_VALUE,'k');
    /**
     * The value of the piece according to standard chess rules
     * <p>
     * Pawn = 1,
     * Bishop = 3.5,
     * Knight = 3,
     * Rook = 5,
     * Queen = 9,
     * King = Integer.MAX_VALUE (effectively infinite),
     * <p>
     */
    protected double value;
    /**
     * The shorthand character for the piece
     * <p>
     * Pawn = p,
     * Bishop = b,
     * Knight = n,
     * Rook = r
     * Queen = q
     * King = k
     * <p>
     */
    protected char shorthand;
    private Piece(double value, char shorthand){
        this.value = value;
        this.shorthand = shorthand;
    }
    private Piece(char shorthand){
        this.shorthand = shorthand;
    }
    public char getShorthand(){
        return shorthand;
    }
    public double getValue(){
        return value;
    }
}
