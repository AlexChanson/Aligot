package particles;

import java.util.ArrayList;

/**
 * Created by ben on 14/04/17.
 */
public class ParticleSystemManager {
    private ArrayList<ParticleSystem> particleSystems;

    public void updateAll(double dt){

        for( ParticleSystem particleSystem : particleSystems ){
            particleSystem.draw();
        }

    }

    public void drawAll(){
        for( ParticleSystem particleSystem : particleSystems ){
            particleSystem.draw();
        }
    }
}
