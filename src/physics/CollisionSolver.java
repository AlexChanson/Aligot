package physics;

import utility.Pair;

import java.util.ArrayList;

import static java.lang.Math.min;
import static jdk.nashorn.internal.objects.NativeMath.max;

/**
 * Created by ben on 30/03/17.
 */
public class CollisionSolver extends PhysicSolver {
    private ArrayList<CollisionListener> collisionListeners;
    private double correctionAmount = 0.01;

    public CollisionSolver(){
        collisionListeners = new ArrayList<>();
    }

    @Override
    public void compute(Simulator sim) {
        ArrayList<Pair<RigidBody, RigidBody>> pairs = PhysicSolver.getCombination(sim.getBodies());

        double distance;
        double collisionTreshold;
        double overlap;
        Vector2D positionOffset;
        Vector2D correctionOffset;
        Vector2D unitOffset;
        for (Pair<RigidBody, RigidBody> pair: pairs){
            positionOffset = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
            distance = positionOffset.norm();
            collisionTreshold = pair.getLeft().getSize() + pair.getRight().getSize();
            overlap = distance - collisionTreshold;

            if ( distance <= collisionTreshold ){
                unitOffset = positionOffset.getNormalized();
                correctionOffset = unitOffset.multiply(overlap).multiply(correctionAmount);

                double totalFriction = pair.getLeft().getFriction()*pair.getRight().getFriction();
                double scalar = pair.getRight().getAppliedForce().scalarProduct(unitOffset);
                pair.getRight().applyForce(
                        pair.getRight().getAppliedForce()
                                .add(unitOffset.multiply(
                                        scalar
                                    )
                                )
                                .add(pair.getLeft().getVelocity().multiply(totalFriction).getOpposite())
                );

                scalar = pair.getLeft().getAppliedForce().scalarProduct(unitOffset);
                pair.getLeft().applyForce(
                        pair.getLeft().getAppliedForce()
                                .add(unitOffset.multiply(
                                        scalar
                                        )
                                ).getOpposite()
                                .add(pair.getLeft().getVelocity().multiply(totalFriction).getOpposite())
                );


                if (!pair.getLeft().getStaticObject()){
                    pair.getLeft().setPosition(pair.getLeft().getPosition().add(correctionOffset));
                }
                if (!pair.getRight().getStaticObject()) {
                    pair.getRight().setPosition(pair.getRight().getPosition().add(correctionOffset.getOpposite()));
                }

                for ( CollisionListener collisionListener : collisionListeners){
                    collisionListener.notifyCollision(pair, distance);
                }
            }
        }
    }

    public double getCorrectionAmount() {
        return correctionAmount;
    }

    public void setCorrectionAmount(double correctionAmount) {
        this.correctionAmount = min(max(correctionAmount, 0.01), 0.9);
    }
}
