package particles;

import core.Engine;
import core.GraphicsEngine;
import core.model.Projectile;
import graphics.Texture;
import graphics.Window;
import physics.RigidBody;

/**
 * Created by ben on 20/05/17.
 */
public class ProjectileTrailEmitter implements Emitter {
    private static double emissionGap = 0.04;
    private double emissionTimer;

    Engine engine;
    GraphicsEngine graphicsEngine;
    public ProjectileTrailEmitter(Engine engine, GraphicsEngine graphicsEngine){
        this.engine = engine;
        this.graphicsEngine = graphicsEngine;
        emissionTimer = 0;
    }

    @Override
    public boolean doneEmitting() {
        return false;
    }

    @Override
    public void emitParticles(ParticleSystem particleSystem, double dt) {
        emissionTimer += dt;
        if (emissionTimer >= emissionGap){
            emissionTimer = emissionTimer % emissionGap;
            for (Projectile p: engine.getProjectiles()){
                RigidBody body = p.getRigidBody();
                Texture particleTexture = Window.getTexture("smokeparticle-01.png");

                if (body.getVelocity().normSquared() > 1000){
                    Particle particle = new Particle(
                            body.getPosition().getX(),
                            body.getPosition().getY(),
                            0,0,
                            Math.random()*50,
                            Math.random()*1.5+7,
                            (Math.random()-0.5)*8
                    );
                    particle.setGraphicComponent(new DiminishingSpriteComponent(particleTexture,
                            graphicsEngine.calculateWorldRatio(engine.getLevel())));
                    particle.setLifetime(Math.random()*0.2+1);
                    particleSystem.addParticles(particle);
                }

            }
        }
    }
}
