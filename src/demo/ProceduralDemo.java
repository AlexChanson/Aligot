package demo;

import core.Planet;
import core.Spawn;
import generator.LevelGen;
import graphics.Window;
import physics.Vector2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class ProceduralDemo {
    private static HashSet<Planet> stuffToRender = new HashSet<>();
    public static void main(String[] args) {
        Window.init("Space Shooter");

        glfwSetKeyCallback(Window.getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_E && action == GLFW_PRESS){
                LevelGen lvl = new LevelGen((long) (Math.random()*1000), LevelGen.LARGE, 7);
                stuffToRender = lvl.create().getPlanets();
                }
        });
        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            stuffToRender.forEach(stuffToRender -> {
                if(stuffToRender instanceof Spawn)
                    Window.drawCircle((int)stuffToRender.getRigidBody().getPosition().getX(),
                            (int)stuffToRender.getRigidBody().getPosition().getY(),
                            (int) stuffToRender.getRigidBody().getSize(), 255,50,50);
                else
                    Window.drawCircle((int)stuffToRender.getRigidBody().getPosition().getX(),
                            (int)stuffToRender.getRigidBody().getPosition().getY(),
                            (int) stuffToRender.getRigidBody().getSize(), 50,255,50);
            });


            Window.swapBuffers();
            glfwPollEvents();

        }
        glfwTerminate();
    }
}