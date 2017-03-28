package graphics;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by clement on 27/03/17.
 */
public class SpriteTexture extends Texture {
    private static HashMap<String, SpriteTexture> generatedTexture = new HashMap<>();
    private String path;

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

    public boolean equals(SpriteTexture t) {
        return t.path.equals(this.path);
    }
}
