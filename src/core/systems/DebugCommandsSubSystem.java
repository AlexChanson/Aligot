package core.systems;

import core.Event;
import core.model.Player;
import gamelauncher.Game;
import org.lwjgl.glfw.GLFW;
import physics.Vector2D;

/**
 * Created by ben on 18/05/17.
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
            case "KEY_PRESSED":
                switch ((int)event.data){
                    case GLFW.GLFW_KEY_C:
                        engine.nextTurn();
                        break;
                    case GLFW.GLFW_KEY_UP:
                        Player player = engine.getActivePlayer();
                        player.getRigidBody().setVelocity(Vector2D.createFromAngle(50, Math.PI-player.getRotation()));
                        break;
                }
                break;
        }
    }
}
