package particles;

import java.util.ArrayList;

/**
 * Created by ben on 14/04/17.
 *
 * allow to create, update and draw particles
 * contains emitter of particles
 */
public class ParticleSystem {
    private static int maxParticles = 1000;

    private ArrayList<Emitter> emitters;
    private ArrayList<Particle> particles;

    public ParticleSystem(){
        particles = new ArrayList<>();
        emitters = new ArrayList<>();
    }

    public void addEmitter(Emitter emitter){
        this.emitters.add(emitter);
    }

    public void addParticles(Particle particle){

        if (particles.size() > maxParticles){
            particles.remove(0);
        }
        this.particles.add(particle);
    }

    /**
     * updates all the live particles and remove dead ones
     * update the emitters to add new particles
     * @param dt
     */
    public void update(double dt){
        particles.removeIf((Particle par) -> par.isDead());

        for (Particle particle : particles){
            particle.update(dt);
        }

        emitters.removeIf(Emitter::doneEmitting);

        for (Emitter emitter : emitters){
            emitter.emitParticles(this, dt);
        }

    }

    /**
     * draw all particles
     */
    public void draw(){
        for (Particle particle : particles){
            particle.draw();
        }

    }
}
