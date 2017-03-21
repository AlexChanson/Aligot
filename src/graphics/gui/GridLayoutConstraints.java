package graphics.gui;

/**
 * Created by clement on 21/03/17.
 */
public class GridLayoutConstraints extends LayoutConstraints<GridLayout> {
    private int colOffset;
    private int rowOffset;
    private int col;
    private int row;

    GridLayoutConstraints(int colOffset, int rowOffset) {
        this.colOffset = colOffset;
        this.rowOffset = rowOffset;
        this.col = 1;
        this.row = 1;
    }

    GridLayoutConstraints(int colOffset, int rowOffset, int col, int row) {
        this.colOffset = colOffset;
        this.rowOffset = rowOffset;
        this.col = col;
        this.row = row;
    }

    public int getColOffset() {
        return colOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
