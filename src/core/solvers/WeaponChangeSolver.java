package core.solvers;


import core.Event;
import org.lwjgl.glfw.GLFW;

public class WeaponChangeSolver extends Solver implements KeyboardListener{
    @Override
    public void initialize() {

    }

    @Override
    public void handleKeyEvent(long window, int key, int scancode, int action, int mods) {
            if(action == GLFW.GLFW_PRESS){
                switch (key){

                }
            }
    }
}
