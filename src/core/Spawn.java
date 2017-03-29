package core;


import physics.Vector2D;

public class Spawn {
    private Vector2D position;

    public Spawn(Vector2D position) {
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }
}
