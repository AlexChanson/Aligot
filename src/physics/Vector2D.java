package physics;

import java.lang.Math;

/**
 * Created by ben on 14/03/17.
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

    public Vector2D(Vector2D other){
        this.x = other.x;
        this.y = other.y;
    }

    public static Vector2D createFromAngle(double norm, double angle){
        return new Vector2D(norm*Math.cos(angle), norm*Math.sin(angle));
    }

    public double norm(){
        return Math.sqrt(x*x+y*y);
    }

    public double normSquared(){
        return x*x+y*y;
    }

    public double distanceTo(Vector2D other){
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    public double distanceToSquared(Vector2D other){
        return (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
    }

    public Vector2D add(double x, double y){
        return new Vector2D(this.x + x, this.y + y);
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public double angle(){
        return Math.acos(this.x/this.norm());
    }

    public double angleDegree(){
        return 180*Math.atan(this.y/this.x)/Math.PI;
    }

    public double angleBetween(Vector2D other){
        double angle = Math.atan(this.y/this.x);
        double otherAngle = Math.atan(other.y/other.x);

        return angle-otherAngle;
    }

    public double angleBetweenAbs(Vector2D other){
        double angle = Math.atan(this.y/this.x);
        double otherAngle = Math.atan(other.y/other.x);

        return Math.abs(angle-otherAngle);
    }

    public Vector2D multiply(double scale){
        return new Vector2D(this.x*scale, this.y*scale);
    }

    public Vector2D Divide(double scale){
        return new Vector2D(this.x/scale, this.y/scale);
    }

    public void rotate(double angle){
        this.x = this.x * Math.cos(angle) - this.y * Math.sin(angle);
        this.y = this.x * Math.sin(angle) + this.y * Math.cos(angle);
    }

    public void rotate90(boolean b){
        if (b){
            this.x = -this.y;
            this.y = this.x;
        }
        else{
            this.x = this.y;
            this.y = -this.x;
        }
    }

    public Vector2D getOpposite(){
        return new Vector2D(-this.x, -this.y);
    }

    public void normalize(){
        double norm = this.norm();
        this.x /= norm;
        this.y /= norm;
    }

    public Vector2D getNormalized(){
        double norm = this.norm();
        return new Vector2D(this.x/norm, this.y/norm);
    }

    public double scalarProduct( Vector2D other ){
        return this.x*other.x + this.y*other.y;
    }

    public void printCoordinates(){
        System.out.print("x: ");
        System.out.println(this.x);
        System.out.print("y: ");
        System.out.println(this.y);
    }
}
