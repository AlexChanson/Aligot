package demo;

import gamelauncher.GameStart;
import graphics.Window;
import physics.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;


public class Game implements GameStart {
    @Override
    public void start(int screenHeight, int screenWidth, boolean fullscreen, String firstPlayerName, String secondPlayerName) {
        Window.init("Aligot", fullscreen);
        Window.setHeight(screenHeight);
        Window.setWidth(screenWidth);

        int i = 0;
        Simulator sim = new Simulator();
        sim.setMaxSpeed(5000);
        sim.addSolver(new NewtonGravitationSolver(6.67e-11));
        sim.addSolver(new AirDampingSolver(0.01, 0.02));
        sim.addSolver(new CollisionSolver());
        RigidBody body1 = new RigidBody(new Vector2D(400,440), 10, 40);
        RigidBody body2 = new RigidBody(new Vector2D(800,400), 200, 2e17);
        body1.setRestitution(0.95);
        body2.setRestitution(0.95);
        body1.setFriction(0.2);
        body2.setFriction(0.2);
        body2.setAttractive(true);

        body1.setVelocity(new Vector2D(200,200));
        body2.setVelocity(new Vector2D(0,0));


        sim.addBody(body1);
        sim.addBody(body2);

        while (Window.shouldClose()) {
            Window.loopStart();

            sim.step(0.025);
            //Window.drawSprite("earth.jpg", (int)body2.getPosition().getX(), (int)body2.getPosition().getY(), i, 0.1f);
            //Window.drawSprite("sputnik.jpg", (int)body1.getPosition().getX(), (int)body1.getPosition().getY(), (float) (body1.getVelocity().angleDegree()+120), 0.05f);

            Window.drawCircle((int)body2.getPosition().getX(), (int)body2.getPosition().getY(), (int)body2.getRadius(),255, 0, 0);
            Window.drawCircle((int)body1.getPosition().getX(), (int)body1.getPosition().getY(), (int)body1.getRadius(),0, 255, 0);
            Vector2D pos = body1.getPosition();
            int forceX = (int) ((pos.getX()+body1.getAcceleration().getX()*0.03));
            int forceY = (int) ((pos.getY()+body1.getAcceleration().getY()*0.03));

            Window.drawLine((int)pos.getX(), (int)pos.getY(), forceX, forceY, 3,255, 0,0);
            Window.drawLine((int)pos.getX(), (int)pos.getY(), (int) (pos.getX()+body1.getAcceleration().getX()/30),
                    (int) (pos.getY()+body1.getAcceleration().getY()/30), 3,255, 255,0);
            Window.drawLine((int)pos.getX(), (int)pos.getY(), (int) (pos.getX()+body1.getVelocity().getX()/5),
                    (int) (pos.getY()+body1.getVelocity().getY()/5), 3,0, 255,0);
            Window.loopEnd();

            i++;
        }
        glfwTerminate();
    }
}
