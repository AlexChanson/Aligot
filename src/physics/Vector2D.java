package physics;

import java.lang.Math;

/**
 * Created by ben on 14/03/17.
 * Vector2D is the mathematical vector with two coordinates
 */
public class Vector2D {
    double x;
    double y;

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param other vector to be copied
     */
    public Vector2D(Vector2D other){
        this.x = other.x;
        this.y = other.y;
    }

    /**
     *
     * @return a new vector with (0,0) as coordinates
     */
    public static Vector2D getNull(){ return new Vector2D(0,0); }

    /**
     *
     * @param norm norm of the vector to be created
     * @param angle angle on the unit circle in radians
     * @return a Vector2D created in the polar plane
     */
    public static Vector2D createFromAngle(double norm, double angle){
        return new Vector2D(norm*Math.cos(angle), norm*Math.sin(angle));
    }

    /**
     *
     * @return a real, mathematically: ||u||
     */
    public double norm(){
        return Math.sqrt(x*x+y*y);
    }

    /**
     *
     * @return return the norm squared, use when the square root operation isn't needed
     */
    public double normSquared(){
        return x*x+y*y;
    }

    /**
     *
     * @param other the compared vector
     * @return the distance between the two vectors, seen as points from the origin
     */
    public double distanceTo(Vector2D other){
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     *
     * @param other the compared vector
     * @return the distance squared between the two vectors, seen as points from the origin.
     */
    public double distanceToSquared(Vector2D other){
        return (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
    }

    public Vector2D add(double x, double y){
        return new Vector2D(this.x + x, this.y + y);
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D minus(double x, double y){
        return new Vector2D(this.x - x, this.y - y);
    }

    public Vector2D minus(Vector2D other){
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     *
     * @return the angle on the unit circle in radiants
     */
    public double angle(){
        return Math.atan2(this.y, this.x);
    }

    /**
     *
     * @return the angle on the unit circle in degrees
     */
    public double angleDegree(){
        return 180*Math.atan2(this.y, this.x)/Math.PI;
    }

    /**
     *
     * @param other vector to compare to
     * @return the angle between the two vectors in radiants
     */
    public double angleBetween(Vector2D other){
        double angle = Math.atan2(this.y, this.x);
        double otherAngle = Math.atan2(other.y, other.x);

        return angle-otherAngle;
    }

    public double angleBetweenAbs(Vector2D other){
        double angle = Math.atan2(this.y, this.x);
        double otherAngle = Math.atan2(other.y, other.x);

        return Math.abs(angle-otherAngle);
    }

    /**
     *
     * @param scale
     * @return the new vector scaled accordingly
     */
    public Vector2D multiply(double scale){
        return new Vector2D(this.x*scale, this.y*scale);
    }

    /**
     *
     * @param scale
     * @return a new vector with each coordinate divided by the scale
     */
    public Vector2D Divide(double scale){
        return new Vector2D(this.x/scale, this.y/scale);
    }

    /**
     *
     * @param angle angle used to rotate in radiants, positive goes is the opposite rotation of a clock's
     */
    public void rotate(double angle){
        double x = this.x;
        this.x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.y =      x * Math.sin(angle) + this.y * Math.cos(angle);
    }

    public void rotate90(boolean b){
        double x = this.x;
        if (b){
            this.x = -this.y;
            this.y =       x;
        }
        else{
            this.x = this.y;
            this.y =     -x;
        }
    }

    /**
     *
     * @return a new vector with flipped coordinates
     */
    public Vector2D getOpposite(){
        return new Vector2D(-this.x, -this.y);
    }

    /**
     * divide vector by his norm
     */
    public void normalize(){
        double norm = this.norm();
        this.x /= norm;
        this.y /= norm;
    }

    /**
     *
     * @return return a new unit vector with the same angle but a length of 1
     */
    public Vector2D getNormalized(){
        double norm = this.norm();
        return new Vector2D(this.x/norm, this.y/norm);
    }

    /**
     *
     * @param other
     * @return the mathematical scalar product
     */
    public double scalarProduct( Vector2D other ){
        return this.x*other.x + this.y*other.y;
    }

    public Vector2D projectOn(Vector2D other){
        return other.getNormalized().multiply(other.scalarProduct(this));
    }

    /**
     *
     * @return a formatted string in the form "x: (x)
     *                                         y: (y)"
     */
    public String toString(){
        return "x: " + this.x + "\ny: " + this.y;
    }
}
