package demo;

import graphics.Window;
import physics.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class PhysicDemo {
    public static void main(String[] args) {
        Window.init("Space Shooter");

        int i = 0;
        Simulator sim = new Simulator();
        sim.setMaxSpeed(5000);
        sim.addSolver(new NewtonGravitationSolver(6.67e-11));
        sim.addSolver(new AirDampingSolver(0.01, 0.02));
        sim.addSolver(new CollisionSolver());
        RigidBody body1 = new RigidBody(new Vector2D(400,400), 20, 83.6);
        RigidBody body2 = new RigidBody(new Vector2D(800,400), 140, 5.9722e17);

        body1.setVelocity(new Vector2D(0,300));

        body1.setAttractive(false);
        body2.setAttractive(true);

        body2.setStaticObject(true);

        sim.addBody(body1);
        sim.addBody(body2);

        RigidBody bh = new RigidBody(new Vector2D(900,900), 1, 10e16);
        bh.setVelocity(Vector2D.getNull());
        bh.setAttractive(true);
        bh.setStaticObject(true);
        glfwSetKeyCallback(Window.getWindow(), (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_E && action == GLFW_PRESS){
                sim.addBody(bh);
                System.out.println("KEY PRESSED");

            }
        });
        while (Window.shouldClose()) {
            glClear(GL_COLOR_BUFFER_BIT);
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(0, Window.getWidth(), Window.getHeight(), 0, -1, 1);
            glMatrixMode(GL_MODELVIEW);

            sim.step(0.025);
            //Window.drawSprite("earth.jpg", (int)body2.getPosition().getX(), (int)body2.getPosition().getY(), i, 0.1f);
            //Window.drawSprite("sputnik.jpg", (int)body1.getPosition().getX(), (int)body1.getPosition().getY(), (float) (body1.getVelocity().angleDegree()+120), 0.05f);

            Window.drawCircle((int)body2.getPosition().getX(), (int)body2.getPosition().getY(), (int)body2.getRadius(),255, 0, 0);
            Window.drawCircle((int)body1.getPosition().getX(), (int)body1.getPosition().getY(), (int)body1.getRadius(),0, 255, 0);
            Vector2D pos = body1.getPosition();
            int forceX = (int) ((pos.getX()+body1.getAcceleration().getX()*0.3));
            int forceY = (int) ((pos.getY()+body1.getAcceleration().getY()*0.3));

            Window.drawLine((int)pos.getX(), (int)pos.getY(), forceX, forceY, 10,0, 255,0);

            Window.swapBuffers();
            glfwPollEvents();

            i++;
        }
        glfwTerminate();
    }
}