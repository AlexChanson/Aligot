package physics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ben on 18/03/17.
 */
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

    public void step(double dt){
        forceSolvers.forEach((value) -> forces.putAll(ForceSolver.combineForces(forces, value.computeForces(this))));
        for (RigidBody body: this.bodies) {
            if (forces.containsKey(body) ){
                body.updateAcceleration(forces.get(body), dt);
            }
            body.updateVelocity(dt);
            body.updatePosition(dt);
        }
        forces.clear();
    }

    public ArrayList<RigidBody> getBodies(){ return bodies; }

    public static void main(String[] args){
        Simulator sim = new Simulator();
        RigidBody added = new RigidBody(new Vector2D(0,1), 1, 1);
        added.velocity = new Vector2D(0,1);
        sim.addBody(added);
        added = new RigidBody(new Vector2D(0,1), 1, 1);
        sim.addBody(added);
        for (int i = 0; i<10; ++i){
            for ( RigidBody body: sim.bodies){
                System.out.println(body.toString());
            }
            sim.step(1);
        }
    }


}
