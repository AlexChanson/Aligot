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

    public void setSpawn(boolean spawn) {
        isSpawn = spawn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Planet spawn(RigidBody rigidBody, String texture){
        Planet planet = new Planet(rigidBody, texture, "rock");
        planet.setAsSpawn();
        return planet;
    }
}
