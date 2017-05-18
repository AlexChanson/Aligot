package core.solvers;

import core.Event;
import core.solvers.Solver;
import graphics.Window;
import org.lwjgl.glfw.GLFW;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ben on 18/05/17.
 */
public class RestartKeySolver extends Solver implements KeyboardListener {
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
    public void handleKeyEvent(long window, int key, int scancode, int action, int mods){
        if(action == GLFW.GLFW_PRESS) {
            switch (key) {
                case GLFW.GLFW_KEY_N:
                    engine.throwEvent(new Event("RESTART_GAME", 0));
                    break;
            }
        }
    }
}
