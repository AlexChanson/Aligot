package particles;

/**
 * Created by ben on 14/04/17.
 */
public interface Emitter {

    public boolean doneEmitting();
    public void emitParticles(ParticleSystem particleSystem, double dt);

}
