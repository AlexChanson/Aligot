package core.solvers;

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
        System.out.println(yoff);
    }
}
