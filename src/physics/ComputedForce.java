package physics;

import utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by ben on 19/03/17.
 */
public abstract class ComputedForce {

    public static ArrayList<Pair<RigidBody, RigidBody>> getCombination(ArrayList<RigidBody> bodies){
        ArrayList<Pair<RigidBody, RigidBody>> returnedPairs = new ArrayList<>();
        for ( int i = 0; i<bodies.size(); ++i){
            for ( int j = i+1; j<bodies.size(); ++j){
                returnedPairs.add(new Pair<RigidBody, RigidBody>(bodies.get(i), bodies.get(j)) );
            }
        }

        return returnedPairs;
    }

    public abstract HashMap<RigidBody, Vector2D> computeForces(Simulator sim);
}
