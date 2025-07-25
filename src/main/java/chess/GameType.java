package chess;
import chess.Pieces.*;
public enum GameType {
    /**
    * Normal chess, with an 8x8 board size and a standard piece layout
    */
    CLASSIC(8,8,new DefaultPiece[][]{
    {new Rook(Color.BLACK,0,0),new Knight(Color.BLACK,0,1),new Bishop(Color.BLACK,0,2),new Queen(Color.BLACK,0,3),new King(Color.BLACK,0,4),new Bishop(Color.BLACK,0,5),new Knight(Color.BLACK,0,6),new Rook(Color.BLACK,0,7)},
    {new Pawn(Color.BLACK,1,0),new Pawn(Color.BLACK,1,1),new Pawn(Color.BLACK,1,2),new Pawn(Color.BLACK,1,3),new Pawn(Color.BLACK,1,4),new Pawn(Color.BLACK,1,5),new Pawn(Color.BLACK,1,6),new Pawn(Color.BLACK,1,7)},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {new Pawn(Color.WHITE,6,0),new Pawn(Color.WHITE,6,1),new Pawn(Color.WHITE,6,2),new Pawn(Color.WHITE,6,3),new Pawn(Color.WHITE,6,4),new Pawn(Color.WHITE,6,5),new Pawn(Color.WHITE,6,6),new Pawn(Color.WHITE,6,7)},   
    {new Rook(Color.WHITE,7,0),new Knight(Color.WHITE,7,1),new Bishop(Color.WHITE,7,2),new Queen(Color.WHITE,7,3),new King(Color.WHITE,7,4),new Bishop(Color.WHITE,7,5),new Knight(Color.WHITE,7,6),new Rook(Color.WHITE,7,7)}
}),
    /**
     * Gardner mini chess, with a 5x5 board size and a relatively normal piece layout
     */
    MINI(5,5,new DefaultPiece[][]{
    {new Rook(Color.BLACK,0,0), new Knight(Color.BLACK,0,1),new Bishop(Color.BLACK,0,2),new Queen(Color.BLACK,0,3),new King(Color.BLACK,0,4)},
    {new Pawn(Color.BLACK,1,0),new Pawn(Color.BLACK,1,1),new Pawn(Color.BLACK,1,2),new Pawn(Color.BLACK,1,3),new Pawn(Color.BLACK,1,4)},
    {null,null,null,null,null},
    {new Pawn(Color.WHITE,3,0),new Pawn(Color.WHITE,3,1),new Pawn(Color.WHITE,3,2),new Pawn(Color.WHITE,3,3),new Pawn(Color.WHITE,3,4)},
    {new Rook(Color.WHITE,4,0),new Knight(Color.WHITE,4,1),new Bishop(Color.WHITE,4,2),new Queen(Color.WHITE,4,3),new King(Color.WHITE,4,4)}
}),
    
    /**
     * Horde chess, with a 8x8 board size 
     * <p>
     * Black has a standard piece layout, while white has only pawns with equivilent value to black's pieces
     */
    HORDE(8,8,new DefaultPiece[][]{
    {new Rook(Color.BLACK,0,0),new Knight(Color.BLACK,0,1),new Bishop(Color.BLACK,0,2),new Queen(Color.BLACK,0,3),new King(Color.BLACK,0,4),new Bishop(Color.BLACK,0,5),new Knight(Color.BLACK,0,6),new Rook(Color.BLACK,0,7)},
    {new Pawn(Color.BLACK,1,0),new Pawn(Color.BLACK,1,1),new Pawn(Color.BLACK,1,2),new Pawn(Color.BLACK,1,3),new Pawn(Color.BLACK,1,4),new Pawn(Color.BLACK,1,5),new Pawn(Color.BLACK,1,6),new Pawn(Color.BLACK,1,7)},
    {null,null,null,null,null,null,null,null},
    {null,new Pawn(Color.WHITE,3,1),new Pawn(Color.WHITE,3,2), new Pawn(Color.WHITE,3,3),null, new Pawn(Color.WHITE,3,5),new Pawn(Color.WHITE,3,6),null},
    {new Pawn(Color.WHITE,4,0),new Pawn(Color.WHITE,4,1),new Pawn(Color.WHITE,4,2),new Pawn(Color.WHITE,4,3),new Pawn(Color.WHITE,4,4),new Pawn(Color.WHITE,4,5),new Pawn(Color.WHITE,4,6),new Pawn(Color.WHITE,4,7)},
    {new Pawn(Color.WHITE,5,0),new Pawn(Color.WHITE,5,1),new Pawn(Color.WHITE,5,2),new Pawn(Color.WHITE,5,3),new Pawn(Color.WHITE,5,4),new Pawn(Color.WHITE,4,5),new Pawn(Color.WHITE,5,6),new Pawn(Color.WHITE,5,7)},
    {new Pawn(Color.WHITE,6,0),new Pawn(Color.WHITE,6,1),new Pawn(Color.WHITE,6,2),new Pawn(Color.WHITE,6,3),new Pawn(Color.WHITE,6,4),new Pawn(Color.WHITE,6,5),new Pawn(Color.WHITE,6,6),new Pawn(Color.WHITE,6,7)},
    {new Pawn(Color.WHITE,7,0),new Pawn(Color.WHITE,7,1),new Pawn(Color.WHITE,7,2),new Pawn(Color.WHITE,7,3),new King(Color.WHITE,7,4),new Pawn(Color.WHITE,7,5),new Pawn(Color.WHITE,7,6),new Pawn(Color.WHITE,7,7)}
}),
    
    /**
     * Queens chess, with a 8x8 board size and all non-pawn pieces being queens
     */
    QUEENS(8,8,new DefaultPiece[][]{
    {new Queen(Color.BLACK,0,0),new Queen(Color.BLACK,0,1),new Queen(Color.BLACK,0,2),new Queen(Color.BLACK,0,3),new King(Color.BLACK,0,4),new Queen(Color.BLACK,0,5),new Queen(Color.BLACK,0,6),new Queen(Color.BLACK,0,7)},
    {new Pawn(Color.BLACK,1,0),new Pawn(Color.BLACK,1,1),new Pawn(Color.BLACK,1,2),new Pawn(Color.BLACK,1,3),new Pawn(Color.BLACK,1,4),new Pawn(Color.BLACK,1,5),new Pawn(Color.BLACK,1,6),new Pawn(Color.BLACK,1,7)},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {new Pawn(Color.WHITE,6,0),new Pawn(Color.WHITE,6,1),new Pawn(Color.WHITE,6,2),new Pawn(Color.WHITE,6,3),new Pawn(Color.WHITE,6,4),new Pawn(Color.WHITE,6,5),new Pawn(Color.WHITE,6,6),new Pawn(Color.WHITE,6,7)},   
    {new Queen(Color.WHITE,7,0),new Queen(Color.WHITE,7,1),new Queen(Color.WHITE,7,2),new Queen(Color.WHITE,7,3),new King(Color.WHITE,7,4),new Queen(Color.WHITE,7,5),new Queen(Color.WHITE,7,6),new Queen(Color.WHITE,7,7)}
}),
    
    /**
     * Big chess with a 10x10 board, relatively normal piece layout
      */
    BIG(10,10, new DefaultPiece[][]{
    {new Queen(Color.BLACK,0,0), new Rook(Color.BLACK,0,1), new Knight(Color.BLACK,0,2), new Bishop(Color.BLACK,0,3), new King(Color.BLACK,0,4), new Queen(Color.BLACK,0,5), new Bishop(Color.BLACK,0,6), new Knight(Color.BLACK,0,7), new Rook(Color.BLACK,0,8), new Queen(Color.BLACK,0,9)},
    {new Pawn(Color.BLACK,1,0), new Pawn(Color.BLACK,1,1), new Pawn(Color.BLACK,1,2), new Pawn(Color.BLACK,1,3), new Pawn(Color.BLACK,1,4), new Pawn(Color.BLACK,1,5), new Pawn(Color.BLACK,1,6), new Pawn(Color.BLACK,1,7), new Pawn(Color.BLACK,1,8), new Pawn(Color.BLACK,1,9)},
    {new Pawn(Color.BLACK,2,0), new Pawn(Color.BLACK,2,1), new Pawn(Color.BLACK,2,2), new Pawn(Color.BLACK,2,3), new Pawn(Color.BLACK,2,4), new Pawn(Color.BLACK,2,5), new Pawn(Color.BLACK,2,6), new Pawn(Color.BLACK,2,7), new Pawn(Color.BLACK,2,8), new Pawn(Color.BLACK,2,9)},
    {null,null,null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null,null,null},
    {new Pawn(Color.WHITE,7,0), new Pawn(Color.WHITE,7,1), new Pawn(Color.WHITE,7,2), new Pawn(Color.WHITE,7,3), new Pawn(Color.WHITE,7,4), new Pawn(Color.WHITE,7,5), new Pawn(Color.WHITE,7,6), new Pawn(Color.WHITE,7,7), new Pawn(Color.WHITE,7,8), new Pawn(Color.WHITE,7,9)},
    {new Pawn(Color.WHITE,8,0), new Pawn(Color.WHITE,8,1), new Pawn(Color.WHITE,8,2), new Pawn(Color.WHITE,8,3), new Pawn(Color.WHITE,8,4), new Pawn(Color.WHITE,8,5), new Pawn(Color.WHITE,8,6), new Pawn(Color.WHITE,8,7), new Pawn(Color.WHITE,8,8), new Pawn(Color.WHITE,8,9)},
    {new Queen(Color.WHITE,9,0), new Rook(Color.WHITE,9,1), new Knight(Color.WHITE,9,2), new Bishop(Color.WHITE,9,3), new King(Color.WHITE,9,4), new Queen(Color.WHITE,9,5), new Bishop(Color.WHITE,9,6), new Knight(Color.WHITE,9,7), new Rook(Color.WHITE,9,8), new Queen(Color.WHITE,9,9)}
});
    /**
     * The number of rows in the game type
     */ 
    private int rows;
    /**
     * The number of columns in the game type
     */ 
    private int cols;
    /**
     * The predetermined corresponding piece layout for the game type
     */
    private DefaultPiece[][] layout;
    private GameType(int rows, int cols, DefaultPiece[][] layout){
        this.rows = rows;
        this.cols = cols;
        this.layout = layout;
    }
    public int getRows(){
        return rows;
    }
    public int getCols(){
        return cols;
    }
    public DefaultPiece[][] getLayout(){
        DefaultPiece[][] copy = new DefaultPiece[layout.length][layout[0].length];
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                DefaultPiece piece = layout[row][col];
                if (piece == null){
                    copy[row][col] = null;
                }
                else{
                    int pRow = piece.getRow();
                    int pCol = piece.getCol();
                    Piece p = piece.getPiece();
                    Color pColor = piece.getColor();
                    switch (p){
                        case PAWN: copy[row][col] = new Pawn(pColor,pRow,pCol); break; 
                        case BISHOP: copy[row][col] = new Bishop(pColor,pRow,pCol); break; 
                        case KNIGHT: copy[row][col] = new Knight(pColor,pRow,pCol); break; 
                        case ROOK: copy[row][col] = new Rook(pColor,pRow,pCol); break; 
                        case QUEEN: copy[row][col] = new Queen(pColor,pRow,pCol); break; 
                        case KING: copy[row][col] = new King(pColor,pRow,pCol); break; 
                        default: break;
                    }
                }
            }
        }
        return copy;
    }
}
