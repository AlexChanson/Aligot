package particles;

/**
 * Created by ben on 14/04/17.
 * Interface for emitting particule
 * is automaticallly removed if doneEmitting() returns true
 */
public interface Emitter {

    /**
     *
     * @return true if emitter is to be removed
     */
    public boolean doneEmitting();
    public void emitParticles(ParticleSystem particleSystem, double dt);

}
