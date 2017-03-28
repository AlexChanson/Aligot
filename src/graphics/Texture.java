package graphics;

import java.awt.image.BufferedImage;

public abstract class Texture {
    protected int id;

    public abstract int getWidth();

    public abstract int getHeight();

    public int getId() {
        return this.id;
    }
}