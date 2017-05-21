package core.systems;

import core.Event;
import core.model.Player;
import org.lwjgl.glfw.GLFW;

/**
 * @author Ben Crulis
 * @handles KEY_PRESSED_CONTINUOUS
 */
public class PlayerAimingSubSystem extends SubSystem {

    @Override
    public void initialize() {

    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("KEY_PRESSED_CONTINUOUS")){
            Player player = engine.getActivePlayer();

            switch ((int)event.data){
                case GLFW.GLFW_KEY_UP:
                    player.setLocalWeaponOrientation(player.getLocalWeaponOrientation()+0.5);
                    break;
                case GLFW.GLFW_KEY_DOWN:
                    player.setLocalWeaponOrientation(player.getLocalWeaponOrientation()-0.5);
                    break;
            }
        }
    }
}
