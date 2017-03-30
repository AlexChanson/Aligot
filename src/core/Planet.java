package core;

import physics.RigidBody;

public class Planet extends GameObject{
    private String type;

    public Planet(RigidBody rigidBody, String texture, String type) {
        super(rigidBody, texture);
        this.type = type;
    }
}
