package physics;

import java.util.ArrayList;


public class AirDampingSolver extends PhysicSolver {
    private double linearDamping;
    private double quadraticDamping;

    /**
     * Returns a PhysicSolver object that slows objects according to their speed
     * @param linear is the coefficient of air damping proportional to velocity
     * @param quadratic is the coefficient of air damping proportional to the square of the velocity
     */
    public AirDampingSolver(double linear, double quadratic){
        linearDamping = linear;
        quadraticDamping = quadratic;
    }

    /**
     *
     * @param sim takes a Simulator object to compute the different modifications applied to the rigidbodies
     * apply a force according to his speed to each object
     */
    @Override
    public void compute(Simulator sim, double dt) {
        ArrayList<RigidBody> bodies = sim.getBodies();
        bodies.forEach((body) -> body.applyForce(body.getVelocity().multiply(linearDamping).getOpposite().
                add(body.getVelocity().multiply(quadraticDamping).multiply(body.getVelocity().norm()).getOpposite()) )  );
    }
}
