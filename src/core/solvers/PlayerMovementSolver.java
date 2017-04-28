package core.solvers;

import core.Event;
import graphics.Window;
import org.lwjgl.glfw.GLFW;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerMovementSolver extends Solver implements KeyboardListener{
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
        if(action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
            switch (key) {
                case GLFW.GLFW_KEY_LEFT:
                case GLFW.GLFW_KEY_A:
                    engine.throwEvent(new Event("PLAYER_MOVEMENT", 0));
                    break;
                case GLFW.GLFW_KEY_RIGHT:
                case GLFW.GLFW_KEY_D:
                    engine.throwEvent(new Event("PLAYER_MOVEMENT", 1));
                    break;
            }
        }
    }


}
