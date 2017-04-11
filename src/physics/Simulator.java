package physics;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulator {
    private ArrayList<RigidBody> bodies;            // simulated rigid bodies
    private ArrayList<PhysicSolver> physicSolvers;  // solvers of the simulation, they implements the method compute()
    private double maxSpeed;                        // cap the speed so the simulation doesn't explode

    /**
     * returns a Simulator object. It has a list of rigid bodies and a list of PhysicSolver that acts on the rigidbodies
     */
    public Simulator(){
        this.bodies = new ArrayList<>();
        this.physicSolvers = new ArrayList<>();
        this.maxSpeed = 5000;
    }

    /**
     *
     * @param body body to remove from the simulation
     */
    public void removeBody(RigidBody body){
        this.bodies.remove(body);
    }

    /**
     *
     * @param body add a body to the simulation
     */
    public void addBody(RigidBody body){
        if (!bodies.contains(body)) // prevent adding the same boy more than once
            this.bodies.add(body);
    }

    /**
     *
     * @param fs add a solver that implements the method compute() to modify the properties of the simulated rigid bodies
     */
    public void addSolver(PhysicSolver fs){
        physicSolvers.add(fs);
    }

    /**
     *
     * @param dt time elapsed since last method call, smaller values are more accurate
     */
    public void step(double dt){
        physicSolvers.forEach((solver) -> solver.compute(this, dt)); // for each solver, compute its effect

        for (RigidBody body: this.bodies) {
            if (!body.getStaticObject()){

                // update all physics properties, order is important
                body.updateAcceleration();
                body.updateVelocity(dt);
                // check for max speed and cap if needed
                if (body.getVelocity().norm() > this.maxSpeed){
                    body.setVelocity(body.getVelocity().getNormalized().multiply(this.maxSpeed));
                }
                body.updatePosition(dt);

                body.resetAppliedForces(); // set forces applied to object to 0
            }
        }
    }

    /**
     *
     * @return the simulated bodies
     */
    public ArrayList<RigidBody> getBodies(){ return bodies; }

    /**
     *
     * @return speed cap
     */
    public double getMaxSpeed() {
        return maxSpeed;
    }

    /**
     *
     * @param maxSpeed used to change speed cap
     */
    public void setMaxSpeed(double maxSpeed) {
        if (maxSpeed > 0 || maxSpeed == -1)
            this.maxSpeed = maxSpeed;
    }
}
