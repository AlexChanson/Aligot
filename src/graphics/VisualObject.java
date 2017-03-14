package graphics;

/**
 * Created by Christopher on 14/03/2017.
 */
public abstract class VisualObject {
    private float scale;
    private float rotate;
    private float offsetX;
    private float offsetY;

    public float getScale() {
        return scale;
    }

    public float getRotate() {
        return rotate;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setRotate(float rotate) {
        this.rotate = rotate;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public void drawSprite() {

    }
}
