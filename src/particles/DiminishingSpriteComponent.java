package particles;

import graphics.Texture;
import graphics.Window;

/**
 * Created by ben on 20/05/17.
 */
public class DiminishingSpriteComponent implements GraphicComponent {

    Texture texture;
    float ratio;

    public DiminishingSpriteComponent(Texture texture, float ratio){
        this.texture = texture;
        this.ratio = ratio;
    }

    @Override
    public void draw(Particle particle) {
        float scale = (float)(ratio*particle.getSize()*(1-(float)(particle.getLife()/particle.getLifetime())));
        float posX = (float)(particle.getPosition().getX()*ratio-scale/2);
        float posY = (float)(particle.getPosition().getY()*ratio-scale/2);

        Window.drawTexture(texture, posX,
                posY, 1, 1,
                (float) particle.getRotation(), scale, 0f, 0f, texture.getWidth(), texture.getHeight(),
                255,255,255);
    }
}
