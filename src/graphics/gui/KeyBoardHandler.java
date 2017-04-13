package graphics.gui;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Created by Christopher on 13/04/2017.
 */
public class KeyBoardHandler extends GLFWKeyCallback{
    public static boolean[] keys = new boolean[65536];

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        keys[key] = action != GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
}
