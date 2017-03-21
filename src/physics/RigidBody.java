package physics;

/**
 * Created by ben on 18/03/17.
 */
public class RigidBody {

    Vector2D position;       // in meter
    Vector2D velocity;       // in meter/second
    Vector2D acceleration;   // in meter/second²
    double size;             // radius in meter
    double mass;             // in kg
    double restitution;      // no unit, between 0 and 1 where 1 is perfect restitution
    boolean attractive;      // is this body attracting the others?
    boolean staticObject;    // is this body moving?

    public RigidBody(Vector2D position, double size, double masse){
        this.position = position;
        this.size = size;
        this.mass = masse;
        velocity = new Vector2D(0, 0);
        acceleration = new Vector2D(0 , 0);
        restitution = 0.5;
        attractive = false;
    }

    public void updatePosition(double dt){
        position =  position.add(velocity.multiply(dt));
    }

    public void updateVelocity(double dt){
        velocity = velocity.add(acceleration.multiply(dt));
    }

    public void setAttractive(boolean attractive){
        this.attractive = attractive;
    }

    public boolean getAttractive(){
        return this.attractive;
    }

    public void setStaticObject( boolean staticObject ){
        this.staticObject = staticObject;
    }

    public boolean getStaticObject(){
        return this.staticObject;
    }

    public void updateAcceleration(Vector2D sommeForce, double dt){
        acceleration = sommeForce.Divide(mass);
    }

    public void update(Vector2D sommeForce, double dt){
        this.updateAcceleration(sommeForce, dt);
        this.updateVelocity(dt);
        this.updatePosition(dt);
    }

    public String toString(){
        return String.format("Rigidbody %d\nPosition: %s\nVelocity: %s", hashCode(), this.position.toString(), this.velocity.toString());
    }

}
