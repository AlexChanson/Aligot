package physics;

import utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ben on 21/03/17.
 */
public class NewtonGravitationSolver extends PhysicSolver {
    double gravitationalConstant;

    public NewtonGravitationSolver(double gravitationalConstant){
        this.gravitationalConstant = gravitationalConstant;
    }

    @Override
    public void compute(Simulator sim) {
        ArrayList<Pair<RigidBody, RigidBody>> pairs = NewtonGravitationSolver.getCombination(sim.getBodies()); // get all the possible pairs without repetition of the same element
        pairs.removeIf((pair) -> !pair.getLeft().getAttractive() && !pair.getRight().getAttractive()); //keeps only the pairs with at least one attractive rigidbody
        //System.out.println(pairs.size());

        Vector2D computedForce;
        double attraction;
        for ( Pair<RigidBody, RigidBody> pair : pairs ){
            computedForce = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
            if(computedForce.normSquared() > 0.01){
                attraction = this.gravitationalConstant*pair.getLeft().mass*pair.getRight().mass/computedForce.normSquared();
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
