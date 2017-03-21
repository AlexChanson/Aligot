package graphics.gui;

/**
 * Created by clement on 21/03/17.
 */
public class AbsoluteLayoutConstraints extends LayoutConstraints<AbsoluteLayout> {
    private int x;
    private int y;
    private int width;
    private int height;

    AbsoluteLayoutConstraints(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
