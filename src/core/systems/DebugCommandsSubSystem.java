package core.systems;

import core.Event;
import core.GraphicsEngine;
import core.model.Player;
import core.model.Projectile;
import org.lwjgl.glfw.GLFW;
import physics.Vector2D;

/**
 * @handles RESTART_GAME, KEY_PRESSED_CONTINUOUS, KEY_PRESSED
 * @emits FIRE, REMOVED_LAST_PROJECTILE
 */
public class DebugCommandsSubSystem extends SubSystem {
    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        switch (event.type){
            case "RESTART_GAME":
                engine.putPlayersOnSpawns();
                break;
            case "KEY_PRESSED_CONTINUOUS":
                switch ((int)event.data){
                    case GLFW.GLFW_KEY_F:
                        Player player = engine.getActivePlayer();
                        player.getRigidBody().setVelocity(Vector2D.createFromAngle(50, Math.PI+Math.PI*player.getRotation()/180));
                        break;

                }
                break;
            case "KEY_PRESSED":
                switch ((int)event.data){
                    case GLFW.GLFW_KEY_C:
                        engine.nextTurn();
                        break;
                    case GLFW.GLFW_KEY_F3:
                        GraphicsEngine.debugDisplay = !GraphicsEngine.debugDisplay;
                        break;
                    case GLFW.GLFW_KEY_T:
                        engine.throwEvent(new Event("FIRE", 200.0));
                        break;
                    case GLFW.GLFW_KEY_Y:
                        for (Projectile projectile : engine.getProjectiles()){
                            engine.getPhysicsEngine().removeBody(projectile.getRigidBody());
                        }
                        engine.getProjectiles().clear();
                        engine.throwEvent(new Event("REMOVED_LAST_PROJECTILE"));
                        break;
                }
                break;
        }
    }
}
