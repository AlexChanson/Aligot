package graphics.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;

/**
 * Created by clement on 21/03/17.
 */
public class GridLayout extends Layout<GridLayoutConstraints> {
    private int col, row;

    GridLayout(int col, int row) throws GridLayoutSizingException {
        if (col <= 1 || row <= 1) {
            throw new GridLayoutSizingException();
        }

        this.col = col;
        this.row = row;
    }

    private boolean gridIsValidConstrains(GridLayoutConstraints constrains) {
        return constrains.getColOffset() >= 0 &&
                constrains.getRowOffset() >= 0 &&
                constrains.getCol() >= 1 &&
                constrains.getRow() >= 1 &&
                constrains.getColOffset() + constrains.getCol() <= this.col &&
                constrains.getRowOffset() + constrains.getRow() <= this.row;
    }

    private boolean[] doGridFilling(boolean[] gridFilling, GridLayoutConstraints constrains) throws GridLayoutSuperpositionException {
        int x, y, k;

        for (x = constrains.getColOffset(); x < constrains.getColOffset() + constrains.getCol(); x++) {
            for (y = constrains.getRowOffset(); y < constrains.getRowOffset() + constrains.getRow(); y++) {
                k = x + y * this.col;

                if (gridFilling[k]) {
                    throw new GridLayoutSuperpositionException();
                } else {
                    gridFilling[k] = true;
                }
            }
        }

        return gridFilling;
    }

    private int getColWidth() { return this.width / this.col; }

    private int getRowHeight() { return this.width / this.col; }

    public void addConponent(GUIComponent conponent, GridLayoutConstraints constrains) throws GridLayoutConstrainsException {
        if (gridIsValidConstrains(constrains)) {
            throw new GridLayoutConstrainsException();
        }

        int width = constrains.getCol() * this.getColWidth();
        int height = constrains.getRow() * this.getRowHeight();
        int x = constrains.getColOffset() * this.getColWidth() + this.x;
        int y = constrains.getRowOffset() * this.getColWidth() + this.y;

        conponent.setSizing(x, y, width, height);

        this.conponents.put(conponent, constrains);
    }

    public void addConponent(GUIComponent conponent, int colOffset, int rowOffset, int col, int row) throws GridLayoutConstrainsException {
        addConponent(conponent, new GridLayoutConstraints(colOffset, rowOffset, col, row));
    }

    public void addConponent(GUIComponent conponent, int colOffset, int rowOffset) throws GridLayoutConstrainsException {
        addConponent(conponent, colOffset, rowOffset, 1, 1);
    }

    public void render() throws GridLayoutSuperpositionException {

    }
}
