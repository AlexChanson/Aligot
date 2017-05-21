package particles;

import graphics.Animation;
import graphics.Texture;
import graphics.Window;

/**
 * Created by ben on 21/05/17.
 */
public class ExplosionComponent implements GraphicComponent {

    Animation animatedExplosion;
    float ratio;

    public ExplosionComponent(float ratio, Animation animation){
        this.animatedExplosion = animation;
        this.ratio = ratio;
    }

    @Override
    public void draw(Particle particle) {
        float scale = (float)(ratio*particle.getSize());
        float posX = (float)(particle.getPosition().getX()*ratio-scale/2);
        float posY = (float)(particle.getPosition().getY()*ratio-scale/2);

        /*Window.drawTexture(texture, posX,
                posY, 1, 1,
                (float) particle.getRotation(), scale, 0f, 0f, texture.getWidth(), texture.getHeight(),
                255,255,255);*/
        animatedExplosion.draw(posX, posY, 1f, 1f, (float)particle.getRotation(), scale);
    }
}
