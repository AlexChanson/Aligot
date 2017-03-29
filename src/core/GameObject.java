package core;

import graphics.Texture;
import physics.RigidBody;

public abstract class GameObject {
    protected RigidBody rigidBody;
    protected String texture;
    protected int z;

    public GameObject() {
    }

    public GameObject(RigidBody rigidBody, String texture) {
        this.rigidBody = rigidBody;
        this.texture = texture;
    }

    public RigidBody getRigidBody() {
        return rigidBody;
    }

    public void setRigidBody(RigidBody rigidBody) {
        this.rigidBody = rigidBody;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
