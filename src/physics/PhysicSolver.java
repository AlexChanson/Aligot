package physics;

import utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by ben on 19/03/17.
 */
public abstract class PhysicSolver {

    /**
     * @param bodies the list of rigidbodies to arrange in every possible pairs
     * @return combine bodies in pair without repetition, the order isn't important
     *               there is only AB or BA but not both
     */
    public static ArrayList<Pair<RigidBody, RigidBody>> getCombination(ArrayList<RigidBody> bodies){
        ArrayList<Pair<RigidBody, RigidBody>> returnedPairs = new ArrayList<>();
        for ( int i = 0; i<bodies.size(); ++i){
            for ( int j = i+1; j<bodies.size(); ++j){
                returnedPairs.add(new Pair<RigidBody, RigidBody>(bodies.get(i), bodies.get(j)) );
            }
        }

        return returnedPairs;
    }

    /**
     *
     * @param sim takes a Simulator object to compute the different modifications applied to the rigidbodies
     *            in the simulator
     */
    public abstract void compute(Simulator sim);
}
