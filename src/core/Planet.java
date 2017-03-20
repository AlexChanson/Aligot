package core;

import physics.RigidBody;

public class Planet {
    private RigidBody core;
    private String texture;
    private String type;

    public Planet(RigidBody core, String texture) {
        this.core = core;
        this.texture = texture;
    }

    public Planet(RigidBody core, String texture, String type) {
        this.core = core;
        this.texture = texture;
        this.type = type;
    }

    public RigidBody getCore() {
        return core;
    }

    public void setCore(RigidBody core) {
        this.core = core;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
