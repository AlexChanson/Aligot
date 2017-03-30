package demo;

import core.Planet;
import core.Spawn;
import generator.LevelGen;
import graphics.TextTexture;
import graphics.Window;
import org.lwjgl.glfw.GLFWKeyCallback;
import physics.NewtonGravitationSolver;
import physics.RigidBody;
import physics.Simulator;
import physics.Vector2D;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class ProceduralDemo {
    private static HashSet<Planet> stuffToRender = new HashSet<>();
    private static Simulator simulator = new Simulator();
    private static RigidBody projectile;

    public static void main(String[] args) {
        Window.init("Demo Generateur Procedural");
        simulator.addSolver(new NewtonGravitationSolver(6.67e-11));
        glfwSetKeyCallback(Window.getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_E && action == GLFW_PRESS){
                LevelGen lvl = new LevelGen((long) (Math.random()*100000), LevelGen.LARGE, 7);
                stuffToRender = lvl.create().getPlanets();
                simulator.getBodies().clear();
                stuffToRender.forEach(stuff -> simulator.getBodies().add(stuff.getRigidBody()));
                }
            if (key == GLFW_KEY_A && action == GLFW_PRESS) {
                RigidBody projectile = new RigidBody(new Vector2D(100, 100), 10, 10e4);
                projectile.setAttractive(true);
                projectile.setVelocity(new Vector2D(100,50));
                simulator.addBody(projectile);
            }
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                Window.exit();
                glfwTerminate();
            }
        });

        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            simulator.step(0.001);
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
            if(projectile != null)
                Window.drawCircle((int)projectile.getPosition().getX(), (int)projectile.getPosition().getY(), 20, 0, 0, 255);

            Window.swapBuffers();
            glfwPollEvents();

        }
        glfwTerminate();
    }
}