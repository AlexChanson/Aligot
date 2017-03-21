package graphics.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Image;

/**
 * Created by clement on 21/03/17.
 */
public class GridLayout extends Layout<GridLayoutConstrains> {
    private int col, row;

    GridLayout(int col, int row) throws GridLayoutSizingException {
        if (col <= 1 || row <= 1) {
            throw new GridLayoutSizingException();
        }

        this.col = col;
        this.row = row;
    }

    private boolean gridIsValidConstrains(GridLayoutConstrains constrains) {
        return constrains.getColOffset() >= 0 &&
                constrains.getRowOffset() >= 0 &&
                constrains.getCol() >= 1 &&
                constrains.getRow() >= 1 &&
                constrains.getColOffset() + constrains.getCol() <= this.col &&
                constrains.getRowOffset() + constrains.getRow() <= this.row;
    }

    private boolean[] doGridFilling(boolean[] gridFilling, GridLayoutConstrains constrains) throws GridLayoutSuperpositionException {
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

    public void addConponent(GUIConponent conponent, GridLayoutConstrains constrains) throws GridLayoutConstrainsException {
        if (gridIsValidConstrains(constrains)) {
            throw new GridLayoutConstrainsException();
        }

        this.conponents.put(conponent, constrains);
    }

    public void addConponent(GUIConponent conponent, int colOffset, int rowOffset, int col, int row) throws GridLayoutConstrainsException {
        addConponent(conponent, new GridLayoutConstrains(colOffset, rowOffset, col, row));
    }

    public void addConponent(GUIConponent conponent, int colOffset, int rowOffset) throws GridLayoutConstrainsException {
        addConponent(conponent, colOffset, rowOffset, 1, 1);
    }

    public Image render(int width, int height) throws GridLayoutSuperpositionException {
        BufferedImage ret = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = ret.getGraphics();

        boolean[] gridFilling = new boolean[this.col * this.row];

        int colw = width / this.col;
        int rowh = height / this.row;

        GridLayoutConstrains constrains;
        int x, y, w, h, k;
        Image conponentRender;

        for (GUIConponent conponent : conponents.keySet()) {
            constrains = this.conponents.get(conponent);

            gridFilling = doGridFilling(gridFilling, constrains);

            x = constrains.getColOffset() * colw;
            y = constrains.getRowOffset() * rowh;
            w = colw * constrains.getCol();
            h = rowh * constrains.getCol();

            try {
                conponentRender = conponent.render(w, h);
                g.drawImage(conponentRender, x, y, null);
            } catch (Exception e) {

            }
        }

        return ret;
    }
}
