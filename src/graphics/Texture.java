package graphics;

import org.lwjgl.opengl.GL11;

/**
 * Created by Christopher on 14/03/2017.
 */
public class Texture {
    private int target;
    private int textureID;
    private int height;
    private int width;
    private int textureWidth;
    private int textureHeight;
    private float widthRatio;
    private float heightRatio;

    public Texture(int target, int textureID) {
        this.target = target;
        this.textureID = textureID;
    }

    public void bind() {
        GL11.glBindTexture(target, textureID);
    }

    public int getTarget() {
        return target;
    }

    public int getTextureID() {
        return textureID;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getTextureWidth() {
        return textureWidth;
    }

    public int getTextureHeight() {
        return textureHeight;
    }

    public float getWidthRatio() {
        return widthRatio;
    }

    public float getHeightRatio() {
        return heightRatio;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public void setWidthRatio(float widthRatio) {
        this.widthRatio = widthRatio;
    }

    public void setHeightRatio(float heightRatio) {
        this.heightRatio = heightRatio;
    }
}