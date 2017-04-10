package graphics;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;

/**
 * SpriteTexture is used to load a sprite who will be drawn in the window
 * We used a HashMap in which textures loaded the first time they are requested
 */
public class SpriteTexture extends Texture {
    private static HashMap<String, SpriteTexture> generatedTexture = new HashMap<>();
    private String path;
    private BufferedImage img;

    /**
     * @return the width of the sprite
     */
    public int getWidth() {
        return this.img.getWidth();
    }

    /**
     * @return the height of the sprite
     */
    public int getHeight() {
        return this.img.getHeight();
    }

    /**
     * Loads a sprite from a path
     * @param path the path of the sprite
     */
    SpriteTexture(String path) {
        this.path = path;

        if (generatedTexture.containsKey(path)) {
            SpriteTexture t = generatedTexture.get(path);
            this.id = t.id;
            this.img = t.img;
        }
        else {
            try {
                File f = new File(path);
                this.img = ImageIO.read(f);

                this.generate();

                generatedTexture.put(path, this);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the pixels from the file and binds them to a texture
     */
    private void generate() {
        int[] pixels_raw = this.img.getRGB(0, 0, this.getWidth(), this.getHeight(), null, 0, this.getWidth());
        ByteBuffer pixels = BufferUtils.createByteBuffer(this.getHeight() * this.getWidth() * 4);

        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                int pixel = pixels_raw[y * this.getWidth() + x];
                pixels.put((byte) ((pixel >> 16) & 0xFF));
                pixels.put((byte) ((pixel >> 8) & 0xFF));
                pixels.put((byte) (pixel & 0xFF));
                pixels.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        pixels.flip();

        this.id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.getWidth(), this.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Checks if SpriteTexture contains the path
     * @param t the SpriteTexture
     * @return boolean
     * If boolean is true SpriteTexture contains the path
     */
    public boolean equals(SpriteTexture t) {
        return t.path.equals(this.path);
    }
}