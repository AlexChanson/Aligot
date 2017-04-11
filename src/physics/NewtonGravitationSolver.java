package physics;

import utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ben on 21/03/17.
 */
public class NewtonGravitationSolver extends PhysicSolver {
    private double gravitationalConstant;

    public NewtonGravitationSolver(double gravitationalConstant){
        this.gravitationalConstant = gravitationalConstant;
    }

    /**
     * Apply a force calculated according to Newton's law of Gravity on each rigid body.
     * Only rigid bodies with attractive set to true attract their pairs according to their mass
     * @param sim takes a Simulator object to compute the different modifications applied to the rigidbodies
     */
    @Override
    public void compute(Simulator sim, double dt) {
        ArrayList<Pair<RigidBody, RigidBody>> pairs = NewtonGravitationSolver.getCombination(sim.getBodies()); // get all the possible pairs without repetition of the same element
        pairs.removeIf((pair) -> !pair.getLeft().getAttractive() && !pair.getRight().getAttractive()); //keeps only the pairs with at least one attractive rigidbody
        //System.out.println(pairs.radius());

        Vector2D computedForce;
        double attraction;
        for ( Pair<RigidBody, RigidBody> pair : pairs ){
            computedForce = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
            if(computedForce.normSquared() > 0.01){         // prevents calculating force for objects too close from each other
                attraction = this.gravitationalConstant*pair.getLeft().mass*pair.getRight().mass/computedForce.normSquared(); // Newton's Law of Gravitation
                computedForce.normalize();
                computedForce = computedForce.multiply(attraction);

                // apply force only if the body attracts
                if ( pair.getRight().getAttractive() ){
                    pair.getLeft().applyForce(computedForce);
                }
                if ( pair.getLeft().getAttractive() ){
                    pair.getRight().applyForce(computedForce.getOpposite());
                }
            }
        }
    }
}
