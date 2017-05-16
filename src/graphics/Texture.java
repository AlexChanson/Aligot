package graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import utility.Loader;

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

    public static boolean textureLoaded(String path){
        return generatedTexture.containsKey(path);
    }

    private void generate() {
        IntBuffer w, h, comp;
        w = BufferUtils.createIntBuffer(1);
        h = BufferUtils.createIntBuffer(1);
        comp = BufferUtils.createIntBuffer(1);

        ByteBuffer image = stbi_load(this.path, w, h, comp, 4);

        if (image == null) {
            image = stbi_load(Loader.getSpriteFolderPath()+this.path, w, h, comp, 4);
            if ( image == null){
                System.out.println("Error: " + stbi_failure_reason() + " , file:" + this.path);
            }
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Texture)
            return ((Texture)obj).path.equals(this.path);
        return false;
    }

    public int getId() {
        return id;
    }
}