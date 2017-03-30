package demo;

import core.Planet;
import generator.LevelGen;
import graphics.Window;
import physics.Vector2D;

import java.util.ArrayList;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class ProceduralTests {
    public static ArrayList<Planet> stuffToRender = new ArrayList<>();
    public static void main(String[] args) {
        Window.init("Space Shooter");

        glfwSetKeyCallback(Window.getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_E && action == GLFW_PRESS){
                LevelGen lvl = new LevelGen((long) (Math.random()*1000), LevelGen.LARGE, 7);
                lvl.create();
                stuffToRender = lvl.worlds;
            }
        });
        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            stuffToRender.forEach(stuffToRender -> {
                Window.drawCircle((int)stuffToRender.getRigidBody().getPosition().getX(),
                        (int)stuffToRender.getRigidBody().getPosition().getY(),
                        (int) stuffToRender.getRigidBody().getSize(), 128,64,128);
            });


            Window.swapBuffers();
            glfwPollEvents();

        }
        glfwTerminate();
    }
}