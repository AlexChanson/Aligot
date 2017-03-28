package physics;

import utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ben on 21/03/17.
 */
public class NewtonGravitationSolver extends ForceSolver {
    double gravitationalConstant;

    public NewtonGravitationSolver(double gravitationalConstant){
        this.gravitationalConstant = gravitationalConstant;
    }

    @Override
    public HashMap<RigidBody, Vector2D> computeForces(Simulator sim) {
        ArrayList<Pair<RigidBody, RigidBody>> pairs = NewtonGravitationSolver.getCombination(sim.getBodies()); // get all the possible pairs without repetition of the same element
        pairs.removeIf((pair) -> !pair.getLeft().attractive); //keeps only the pairs with left rigidbody attractive
        HashMap<RigidBody, Vector2D> forces = new HashMap<>();
        //System.out.println(pairs.size());

        Vector2D computedForce;
        double attraction;
        for ( Pair<RigidBody, RigidBody> pair : pairs ){
            computedForce = pair.getLeft().position.minus(pair.getRight().position);
            if(computedForce.normSquared() == 0.0)
                System.out.println("Ben divided by 0 :/");
            attraction = this.gravitationalConstant*pair.getLeft().mass*pair.getRight().mass/computedForce.normSquared();
            computedForce.normalize();
            computedForce = computedForce.multiply(attraction);
            if ( pair.getRight().attractive ){
                forces.put(pair.getLeft(), computedForce);
            }
            forces.put(pair.getRight(), computedForce.getOpposite());
        }

        return forces;
    }
}
