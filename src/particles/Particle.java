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
    private double lifetime = 100.0;
    private double life;
    private double size;
    private ParticleStates state;
    private GraphicComponent graphicComponent;

    public Particle(double x, double y, double vx, double vy, double rotation, double size, double rotationSpeed, double brownianAmount){
        position = new Vector2D(x,y);
        velocity = new Vector2D(vx, vy);
        this.rotation = rotation;
        this.size = size;
        this.rotationSpeed = rotationSpeed;
        this.brownianAmount = brownianAmount;
    }

    public Particle(double x, double y, double vx, double vy, double size, double brownianAmount){
        position = new Vector2D(x,y);
        velocity = new Vector2D(vx, vy);
        this.rotation = 0;
        this.size = size;
        this.rotationSpeed = 0;
        this.brownianAmount = brownianAmount;
    }

    public void setGraphicComponent(GraphicComponent graphicComponent){
        this.graphicComponent = graphicComponent;
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

    public void draw(){
        graphicComponent.draw(this);
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }

    public double getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getBrownianAmount() {
        return brownianAmount;
    }

    public void setBrownianAmount(double brownianAmount) {
        this.brownianAmount = brownianAmount;
    }

    public double getLifetime() {
        return lifetime;
    }

    public void setLifetime(double lifetime) {
        this.lifetime = Math.max(lifetime, 1);
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = Math.max(life, 0);
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
}
