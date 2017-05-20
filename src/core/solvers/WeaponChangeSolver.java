package core.solvers;

import core.Event;
import graphics.Window;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;

public class WeaponChangeSolver extends Solver implements GLFWScrollCallbackI{
    @Override
    public void initialize() {
        glfwSetScrollCallback(Window.getWindow(), this);
    }

    @Override
    public void invoke(long window, double xoff, double yoff) {
        int n = (int) yoff;
        if (n >= 1){
            engine.throwEvent(new Event("CHANGE_WEAPON", 1));
        } else if (n <= -1){
            engine.throwEvent(new Event("CHANGE_WEAPON", -1));
        }
    }
}
