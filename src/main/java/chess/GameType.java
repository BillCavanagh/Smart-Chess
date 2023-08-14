package chess;
import chess.Pieces.*;
public enum GameType {
    // normal chess 
    CLASSIC(8,8,new DefaultPiece[][]{{new Rook(Color.BLACK,0,0),new Knight(Color.BLACK,0,1),new Bishop(Color.BLACK,0,2),new Queen(Color.BLACK,0,3),new King(Color.BLACK,0,4),new Bishop(Color.BLACK,0,5),new Knight(Color.BLACK,0,6),new Rook(Color.BLACK,0,7)},
    {new Pawn(Color.BLACK,1,0),new Pawn(Color.BLACK,1,1),new Pawn(Color.BLACK,1,2),new Pawn(Color.BLACK,1,3),new Pawn(Color.BLACK,1,4),new Pawn(Color.BLACK,1,5),new Pawn(Color.BLACK,1,6),new Pawn(Color.BLACK,1,7)},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {null,null,null,null,null,null,null,null},
    {new Pawn(Color.WHITE,6,0),new Pawn(Color.WHITE,6,1),new Pawn(Color.WHITE,6,2),new Pawn(Color.WHITE,6,3),new Pawn(Color.WHITE,6,4),new Pawn(Color.WHITE,6,5),new Pawn(Color.WHITE,6,6),new Pawn(Color.WHITE,6,7)},   
    {new Rook(Color.WHITE,7,0),new Knight(Color.WHITE,7,1),new Bishop(Color.WHITE,7,2),new Queen(Color.WHITE,7,3),new King(Color.WHITE,7,4),new Bishop(Color.WHITE,7,5),new Knight(Color.WHITE,7,6),new Rook(Color.WHITE,7,7)}}),
    // gardner mini chess 
    MINI(5,5,new DefaultPiece[][]{{new Rook(Color.BLACK,0,0), new Knight(Color.BLACK,0,1),new Bishop(Color.BLACK,0,2),new Queen(Color.BLACK,0,3),new King(Color.BLACK,0,4)},
    {new Pawn(Color.BLACK,1,0),new Pawn(Color.BLACK,1,1),new Pawn(Color.BLACK,1,2),new Pawn(Color.BLACK,1,3),new Pawn(Color.BLACK,1,4)},
    {null,null,null,null,null},
    {new Pawn(Color.WHITE,3,0),new Pawn(Color.WHITE,3,1),new Pawn(Color.WHITE,3,2),new Pawn(Color.WHITE,3,3),new Pawn(Color.WHITE,3,4)},
    {new Rook(Color.WHITE,4,0),new Knight(Color.WHITE,4,1),new Bishop(Color.WHITE,4,2),new Queen(Color.WHITE,4,3),new King(Color.WHITE,4,4)}}),
    // horde chess 
    HORDE(8,8,new DefaultPiece[][]{});
    private int rows;
    private int cols;
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
        return layout;
    }
}
