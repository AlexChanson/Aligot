package core;

import physics.RigidBody;

public class Planet extends GameObject{
    private String type;
    private boolean isSpawn = false;

    public Planet(RigidBody rigidBody, String texture, String type) {
        super(rigidBody, texture);
        this.type = type;
    }

    public void setAsSpawn(){
        isSpawn = true;
    }

    public boolean isSpawn(){
        return isSpawn;
    }

    public static Planet spawn(RigidBody rigidBody, String texture){
        Planet planet = new Planet(rigidBody, texture, "solid");
        planet.setAsSpawn();
        return planet;
    }
}
