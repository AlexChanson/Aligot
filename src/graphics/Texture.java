
import org.lwjgl.BufferUtils;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Texture {
    private int id;
    private int width;
    private int height;

    public Texture (String fileName){
        BufferedImage bufferImage;
        try {
            File f = new File(fileName);
            bufferImage = ImageIO.read(f);
            width = bufferImage.getWidth();
            height = bufferImage.getHeight();

            int [] pixels_raw = bufferImage.getRGB(0, 0, width, height,null,0,width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);

            for (int y=0; y< height; y++){
                for(int x = 0; x < width; x++){
                    int pixel = pixels_raw[y*width+x];
                    pixels.put((byte)((pixel >>16) & 0xFF));
                    pixels.put((byte)((pixel >> 8) & 0xFF));
                    pixels.put((byte)(pixel & 0xFF));
                    pixels.put((byte)((pixel >> 24) & 0xFF));
                }
            }
            pixels.flip();

            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D,0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}