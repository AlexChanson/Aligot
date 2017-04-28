package core.solvers;

import core.Event;
import graphics.Window;
import org.lwjgl.glfw.GLFW;

public class PlayerMovementSolver extends Solver implements KeyboardListener{

    @Override
    public void initialize() {
        try {
            Window.getKeyboardListeners().add(this);
            System.out.println("Player Movement Solver : Init OK");
        }catch (NullPointerException e){
            System.out.println("Player Movement Solver : Init FAILED");
        }
    }


    @Override
    public void handleKeyEvent(long window, int key, int scancode, int action, int mods) {
        if(action == GLFW.GLFW_PRESS) {
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
