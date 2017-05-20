package core.solvers;

import core.Event;
import graphics.Window;
import org.lwjgl.glfw.GLFW;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ben on 20/05/17.
 */
public class FiringSolver extends Solver implements KeyboardListener {
    private final static Logger LOGGER = Logger.getLogger(PlayerMovementSolver.class.getName());

    @Override
    public void initialize() {
        try {
            Window.getKeyboardListeners().add(this);
            LOGGER.log(Level.INFO, "Init OK");
        }catch (NullPointerException e){
            LOGGER.log(Level.WARNING, "Init FAILED");
        }
    }

    @Override
    public void handleKeyEvent(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW.GLFW_PRESS) {
            switch (key) {
                case GLFW.GLFW_KEY_SPACE:
                    engine.throwEvent(new Event("STARTED_CHARGING_WEAPON", 0));
                    break;
            }
        } else if (action == GLFW.GLFW_RELEASE){
            switch (key) {
                case GLFW.GLFW_KEY_SPACE:
                    engine.throwEvent(new Event("FINISHED_CHARGING_WEAPON", 0));
                    break;

            }
        }
    }
}
