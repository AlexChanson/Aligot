package demo;

import graphics.Window;
import physics.NewtonGravitationSolver;
import physics.RigidBody;
import physics.Simulator;
import physics.Vector2D;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class MainFrame {
    public static void main(String[] args) {
        Window.init("Space Shooter");

        int i = 0;
        Simulator sim = new Simulator();
        sim.addSolver(new NewtonGravitationSolver(-6.67e-11));
        RigidBody body1 = new RigidBody(new Vector2D(400,400), 12, 83.6);
        RigidBody body2 = new RigidBody(new Vector2D(800,400), 6, 5.9722e17);

        body1.setVelocity(new Vector2D(0,300));

        body1.setAttractive(true);
        body2.setAttractive(true);

        body2.setStaticObject(true);

        sim.addBody(body1);
        sim.addBody(body2);
        glfwSetKeyCallback(Window.getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_E && action == GLFW_PRESS){
                RigidBody rb = new RigidBody(new Vector2D(900,900), 1, 10e10);
                rb.setVelocity(Vector2D.getNull());
                rb.setAttractive(true);
                rb.setStaticObject(true);
                System.out.println("KEY PRESSED");
                sim.addBody(rb);
            }
        });
        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);


            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);
            sim.step(0.1);
            Window.drawSprite("sputnik.jpg", (int)body1.getPosition().getX(), (int)body1.getPosition().getY(), -i, 0.05f);
            Window.drawSprite("earth.jpg", (int)body2.getPosition().getX(), (int)body2.getPosition().getY(), i, 0.1f);
            Window.swapBuffers();

            glfwPollEvents();

            i++;
        }
        glfwTerminate();
    }
}