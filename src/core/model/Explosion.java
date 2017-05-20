package core.model;

import physics.Vector2D;

public class Explosion {
    private Vector2D position;
    private double radius;
    private int damage;

    public Explosion(Vector2D position, double radius, int damage) {
        this.position = position;
        this.radius = radius;
        this.damage = damage;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
