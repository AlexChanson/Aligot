package particles;

import physics.Vector2D;

/**
 * Created by ben on 14/04/17.
 */
public class ContinuousEmitter implements Emitter {
    double emissionRate;
    Vector2D initialSpeed;

    public ContinuousEmitter(double rate, double normalVelocity, Vector2D initialSpeed){
        emissionRate = rate;
        this.initialSpeed = initialSpeed;
    }


    @Override
    public boolean doneEmitting() {
        return false;
    }

    @Override
    public void emitParticles(ParticleSystem particleSystem, double dt) {

    }
}
