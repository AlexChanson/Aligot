package graphics.gui;

import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 * Created by Christopher on 13/04/2017.
 */
public class MouseHandler extends GLFWMouseButtonCallback {
    public void invoke(long window, int posX, int posY, int i) {
        System.out.println("Button clicked");
    }
}
