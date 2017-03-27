package demo;

import physics.RigidBody;
import physics.Simulator;
import physics.Vector2D;

public class PhysicsDemo {

    public static void main(String[] args){
        Simulator sim = new Simulator();
        RigidBody added = new RigidBody(new Vector2D(0,1), 1, 1);
        added.setVelocity(new Vector2D(0,1));
        sim.addBody(added);
        added = new RigidBody(new Vector2D(0,1), 1, 1);
        sim.addBody(added);
        for (int i = 0; i<10; ++i){
            for ( RigidBody body: sim.getBodies()){
                System.out.println(body.toString());
            }
            sim.step(1);
        }
    }
}
