package physics;

/**
 * Created by ben on 18/03/17.
 */
public class RigidBody {

    Vector2D position;
    Vector2D velocity;
    Vector2D acceleration;
    double size;
    double mass;
    double friction;

    public RigidBody(Vector2D position, double size, double masse){
        this.position = position;
        this.size = size;
        this.mass = masse;
        velocity = new Vector2D(0, 0);
        acceleration = new Vector2D(0 , 0);
        friction = 1;
    }

    public void updatePosition(double dt){
        position =  position.add(velocity.multiply(dt));
    }

    public void updateVelocity(double dt){
        velocity = velocity.add(acceleration.multiply(dt));
    }

    public void updateAcceleration(Vector2D sommeForce, double dt){
        acceleration = sommeForce.Divide(mass);
    }

}
