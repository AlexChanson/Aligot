package particles;

import physics.Vector2D;

/**
 * Created by ben on 14/04/17.
 */


enum ParticleStates{
    UNBORN,
    ALIVE,
    DEAD;
}

public class Particle {
    private Vector2D position;
    private double rotation;
    private Vector2D velocity;
    private double rotationSpeed;
    private double brownianAmount;
    private double lifetime;
    private double life;
    private double size;
    private ParticleStates state;

    public Particle(double x, double y, double vx, double vy, double rotation, double size, double rotationSpeed, double brownianAmount){
        position = new Vector2D(x,y);
        velocity = new Vector2D(vx, vy);
        this.rotation = rotation;
        this.size = size;
        this.rotationSpeed = rotationSpeed;
        this.brownianAmount = brownianAmount;
    }

    public void setAlive(){
        state = ParticleStates.ALIVE;
        life = 0;
    }

    public boolean isDead(){
        return state == ParticleStates.DEAD;
    }

    public void update(double dt){
        if ( state == ParticleStates.ALIVE ){
            life += dt;

            position = position.add(velocity.multiply(dt));
            rotation += rotationSpeed;

            if ( life > lifetime ){
                state = ParticleStates.DEAD;
            }
        }
    }
}
