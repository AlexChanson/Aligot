package demo;

import physics.NewtonGravitationSolver;
import physics.RigidBody;
import physics.Simulator;
import physics.Vector2D;

public class PhysicsDemo {

    public static void main(String[] args){
        Simulator sim = new Simulator();
        RigidBody added = new RigidBody(new Vector2D(0,1), 1, 1);
        added.setAttractive(true);
        added.setVelocity(new Vector2D(0,1));
        sim.addBody(added);
        added = new RigidBody(new Vector2D(1,1), 1, 1);
        added.setAttractive(true);
        sim.addBody(added);
        sim.addSolver(new NewtonGravitationSolver(6.67e-11));
        for (int i = 0; i<2; ++i){
            for ( RigidBody body: sim.getBodies()){
                System.out.println(body.toString());
            }
            sim.step(0.01);
        }
    }
}
