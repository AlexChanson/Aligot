package graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;

/**
 * Texture is used to load a sprite who will be drawn in the window
 * We used a HashMap in which textures loaded the first time they are requested
 */
public class Texture {
    private static HashMap<String, Texture> generatedTexture = new HashMap<>();
    private String path;
    //private BufferedImage img;
    private int id, height, width;

    /**
     * @return the width of the sprite
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * @return the height of the sprite
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Loads a sprite from a path, and applies a tint to it
     * @param path the path of the sprite
     */
    /*public Texture(String path, Color tint) {
        this.path = path;

        if (generatedTexture.containsKey(path)) {
            Texture t = generatedTexture.get(path);
            this.id = t.id;
            this.img = t.img;
        }
        else {
            try {
                File f = new File(path);
                this.img = Colorizer.colorize(ImageIO.read(f), tint);

                this.generate();

                generatedTexture.put(path, this);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    public Texture(String path) {
        this.path = path;

        if (generatedTexture.containsKey(path)) {
            Texture t = generatedTexture.get(path);
            this.id = t.id;
            this.height = t.height;
            this.width = t.width;
        }
        else {
            generate();
            generatedTexture.put(path, this);
        }
    }

    public static Texture getTexture(String path){

        if( generatedTexture.containsKey(path) ){
            return generatedTexture.get(path);
        }
        else{
            Texture tex = new Texture(path);
            generatedTexture.put(path, tex);
            return tex;
        }
    }

    private void generate() {
        IntBuffer w, h, comp;
        w = BufferUtils.createIntBuffer(1);
        h = BufferUtils.createIntBuffer(1);
        comp = BufferUtils.createIntBuffer(1);

        ByteBuffer image = stbi_load(this.path, w, h, comp, 4);

        if (image == null) {
            System.out.println("error: " + stbi_failure_reason());
        }

        this.height = h.get();
        this.width = w.get();

        this.id = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, id);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
    }

    /**
     * Checks if Texture contains the path
     * @param t the Texture
     * @return boolean
     * If boolean is true Texture contains the path
     */
    public boolean equals(Texture t) {
        return t.path.equals(this.path);
    }

    public int getId() {
        return id;
    }
}