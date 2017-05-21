package particles;

/**
 * Created by ben on 14/04/17.
 * Enable drawing of particules independently from the graphics library used
 */
public interface GraphicComponent {

    /**
     *
     * @param particle the particle to be drawn
     */
    public void draw(Particle particle);
}
