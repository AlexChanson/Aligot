package particles;

import core.Engine;
import core.GraphicsEngine;
import gamelauncher.Game;
import graphics.Animation;
import graphics.Texture;
import graphics.Window;
import physics.RigidBody;
import physics.Vector2D;

public class ExplosionEmitter implements Emitter {
    boolean done;
    Engine engine;
    GraphicsEngine graphicsEngine;
    Vector2D location;
    float size;

    public ExplosionEmitter(Vector2D location, float size){
        this.engine = Game.getEngine();
        this.graphicsEngine = Game.getGraphicsEngine();
        this.location = location;
        this.size = size;
        done = false;
    }

    @Override
    public boolean doneEmitting() {
        return done;
    }

    @Override
    public void emitParticles(ParticleSystem particleSystem, double dt) {
        Animation animation = Animation.getAnimation("boom");
        if (animation != null) {
            animation.start();
            Particle particle = new Particle(
                    location.getX(),
                    location.getY(),
                    0,0,
                    Math.random()*50,
                    size,
                    0
            );
            particle.setGraphicComponent(new ExplosionComponent(
                    graphicsEngine.calculateWorldRatio(engine.getLevel()),
                    animation));
            particle.setLifetime(animation.getTotalDuration());
            particleSystem.addParticles(particle);
        }
        done = true;
    }
}
