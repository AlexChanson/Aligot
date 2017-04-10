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
    private double correctionAmount = 0.3;
    private int iterrations=10;

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

        for (int i = 0; i < iterrations; i++){
            for (Pair<RigidBody, RigidBody> pair: pairs){
                positionOffset = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
                distance = positionOffset.norm();
                collisionTreshold = pair.getLeft().getRadius() + pair.getRight().getRadius();
                overlap = distance - collisionTreshold;

                if ( distance <= collisionTreshold ){
                    unitOffset = positionOffset.getNormalized();
                    correctionOffset = unitOffset.multiply(overlap).multiply(correctionAmount);

                    double totalFriction = pair.getLeft().getFriction()*pair.getRight().getFriction();
                    double scalar = pair.getRight().getAppliedForce().scalarProduct(unitOffset);
                    pair.getRight().applyForce(
                            unitOffset.multiply(
                                    -scalar).add(
                                            pair.getRight().getVelocity().getOpposite().multiply(totalFriction*pair.getRight().getMass())
                            )
                    );

                    scalar = pair.getLeft().getAppliedForce().scalarProduct(unitOffset);
                    pair.getLeft().applyForce(
                            unitOffset.multiply(
                                    -scalar).add(
                                        pair.getLeft().getVelocity().getOpposite().multiply(totalFriction*pair.getLeft().getMass())
                            )
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
    }

    public int getIterrations() {
        return iterrations;
    }

    public void setIterrations(int iterrations) {
        this.iterrations = iterrations;
    }

    public double getCorrectionAmount() {
        return correctionAmount;
    }

    public void setCorrectionAmount(double correctionAmount) {
        this.correctionAmount = min(max(correctionAmount, 0.1), 0.9);
    }
}
