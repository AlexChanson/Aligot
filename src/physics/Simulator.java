package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Simulator {
    private ArrayList<RigidBody> bodies;
    private ArrayList<ForceSolver> forceSolvers;
    private HashMap<RigidBody, Vector2D> forces;

    public Simulator(){
        this.bodies = new ArrayList<>();
        this.forceSolvers = new ArrayList<>();
        this.forces =  new HashMap<>();
    }

    public void removeBody(RigidBody body){
        this.bodies.remove(body);
    }

    public void addBody(RigidBody body){
        this.bodies.add(body);
    }

    public void addSolver(ForceSolver fs){
        forceSolvers.add(fs);
    }

    public void step(double dt){
        forceSolvers.forEach((value) -> forces.putAll(ForceSolver.combineForces(forces, value.computeForces(this))));
        for (RigidBody body: this.bodies) {
            if (forces.containsKey(body) ){
                body.updateAcceleration(forces.get(body));
            }
            body.updateVelocity(dt);
            body.updatePosition(dt);
        }
        forces.clear();
    }

    public ArrayList<RigidBody> getBodies(){ return bodies; }

}
