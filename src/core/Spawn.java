package core;


import physics.RigidBody;

public class Spawn extends Planet{

    public Spawn(RigidBody rigidBody, String texture) {
        super(rigidBody, texture, "solid");
    }
}
