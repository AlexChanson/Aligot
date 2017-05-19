package physics;

/**
 * Created by ben on 18/03/17.
 */
public class RigidBody {

    Vector2D position;       // in meter
    Vector2D velocity;       // in meter/second
    Vector2D acceleration;   // in meter/second²
    Vector2D appliedForce;
    double radius;           // radius in meter
    double mass;             // in kg
    double restitution;      // no unit, between 0 and 1 where 1 is perfect restitution
    double friction;
    double staticFriction;   // friction at rest
    boolean attractive;      // is this body attracting the others?
    boolean staticObject;    // is this body moving?

    /**
     *
     * @param position is the place where the rigidbody is
     * @param radius is his radius in meters
     * @param mass is his mass in kg
     */
    public RigidBody(Vector2D position, double radius, double mass){
        this.position = position;
        this.radius = radius;
        this.mass = mass;
        this.friction = 0.5;
        this.staticFriction = 0.3;
        velocity = new Vector2D(0, 0);
        acceleration = new Vector2D(0 , 0);
        appliedForce = new Vector2D(0,0);
        restitution = 0.9;
        attractive = false;
        staticObject = false;
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

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getRestitution() {
        return restitution;
    }

    public void setRestitution(double restitution) {
        this.restitution = restitution;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public void updateAcceleration(){
        acceleration = this.appliedForce.Divide(mass);
    }

    public Vector2D getAppliedForce() {
        return appliedForce;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void resetAppliedForces(){
        this.appliedForce = Vector2D.getNull();
    }

    public void applyForce(Vector2D force) {
        this.appliedForce = this.appliedForce.add(force);
    }

    public double getStaticFriction() {
        return staticFriction;
    }

    public void setStaticFriction(double staticFriction) {
        this.staticFriction = staticFriction;
    }

    /**
     * calculate all properties in the right order in one method call
     * @param dt is the time elapsed since las method call
     */
    public void update(double dt){
        this.updateAcceleration();
        this.updateVelocity(dt);
        this.updatePosition(dt);
    }

    /**
     *
     * @return a formatted string describing the object
     */
    public String toString(){
        return String.format("Rigidbody %d\nPosition: %s\nVelocity: %s", hashCode(), this.position.toString(), this.velocity.toString());
    }

}
