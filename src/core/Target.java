package core;

import physics.RigidBody;

public class Target extends GameObject {
    private boolean isSpawn = false;

    public Target(RigidBody rigidBody, String texture) {
        super(rigidBody, texture);
    }

    public boolean isSpawn(){
        return isSpawn;
    }

    public void setSpawn(boolean spawn) { isSpawn = spawn; }

    public void setAsSpawn(){
        isSpawn = true;
    }

    public static Target spawn(RigidBody rigidBody, String texture) {
        Target target = new Target(rigidBody, texture);
        target.setAsSpawn();
        return target;
    }
}
