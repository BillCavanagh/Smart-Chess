package chess;
import chess.Pieces.*;
public enum GameType {
    CLASSIC(8,8,new DefaultPiece[][]{}), MINI(4,4,new DefaultPiece[][]{}), HORDE(8,8,new DefaultPiece[][]{});
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
