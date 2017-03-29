package physics;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulator {
    private ArrayList<RigidBody> bodies;
    private ArrayList<PhysicSolver> physicSolvers;

    public Simulator(){
        this.bodies = new ArrayList<>();
        this.physicSolvers = new ArrayList<>();
    }

    public void removeBody(RigidBody body){
        this.bodies.remove(body);
    }

    public void addBody(RigidBody body){
        if (!bodies.contains(body)) // prevent adding the same boy more than once
            this.bodies.add(body);
    }

    public void addSolver(PhysicSolver fs){
        physicSolvers.add(fs);
    }

    public void step(double dt){
        physicSolvers.forEach((solver) -> solver.compute(this)); // for each solver, compute its effect

        for (RigidBody body: this.bodies) {
            if (!body.getStaticObject()){

                // update all physics properties, order is important
                body.updateAcceleration();
                body.updateVelocity(dt);
                body.updatePosition(dt);

                body.resetAppliedForces(); // set forces applied to object to 0
            }
        }
    }

    public ArrayList<RigidBody> getBodies(){ return bodies; }

}
