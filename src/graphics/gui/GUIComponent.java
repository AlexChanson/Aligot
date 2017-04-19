package graphics.gui;

public abstract class GUIComponent implements Comparable<GUIComponent>{
    protected int posX, posY, height, width, z = 0;
    protected String id;
    public abstract void draw();

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(GUIComponent other) {
        if(other == null)
            return 0;
        if(getZ() < other.getZ())
            return -1;
        if(getZ() > other.getZ())
            return 1;
        return 0;
    }
}
