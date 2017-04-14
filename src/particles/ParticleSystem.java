package particles;

import java.util.ArrayList;

/**
 * Created by ben on 14/04/17.
 */
public class ParticleSystem {
    private static int maxParticles = 1000;


    private ArrayList<Particle> particles;

    public ParticleSystem(){
        particles = new ArrayList<>();
    }

    public void addParticles(Particle particle){
        this.particles.add(particle);
    }

    public void update(double dt){
        particles.removeIf((Particle par) -> par.isDead());

        for (Particle particle : particles){
            particle.update(dt);
        }

    }
}
