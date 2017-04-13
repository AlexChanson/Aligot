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
    public void compute(Simulator sim, double dt) {
        ArrayList<Pair<RigidBody, RigidBody>> pairs = PhysicSolver.getCombination(sim.getBodies());

        double distance;
        double collisionTreshold;
        double overlap;
        double totalMass;
        double massDiff;
        double totalFriction;
        double AVelDouble;
        double BVelDouble;
        double totalRestitution;

        Vector2D positionOffset;
        Vector2D correctionOffset;
        Vector2D unitOffset;
        Vector2D finalSpeedVector;
        Vector2D AVel;
        Vector2D BVel;
        Vector2D tangent;

        for (int i = 0; i < iterrations; i++){
            for (Pair<RigidBody, RigidBody> pair: pairs){
                positionOffset = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
                distance = positionOffset.norm();
                collisionTreshold = pair.getLeft().getRadius() + pair.getRight().getRadius();
                overlap = distance - collisionTreshold;

                if ( distance <= collisionTreshold ){
                    unitOffset = positionOffset.getNormalized();
                    tangent = new Vector2D(unitOffset);
                    tangent.rotate90(true);

                    correctionOffset = unitOffset.multiply(overlap).multiply(correctionAmount);
                    totalMass = pair.getLeft().getMass()+pair.getRight().getMass();
                    massDiff = pair.getLeft().getMass()-pair.getRight().getMass();
                    totalRestitution = pair.getLeft().getRestitution()*pair.getRight().getRestitution();

                    AVel = pair.getLeft().getVelocity();
                    BVel = pair.getRight().getVelocity();
                    AVelDouble = AVel.norm();
                    BVelDouble = BVel.norm();

                    // impulse calculation
                    // A.v = (A.u * (A.m - B.m) + (2 * B.m * B.u)) / (A.m + B.m)
                    // B.v = (B.u * (B.m - A.m) + (2 * A.m * A.u)) / (A.m + B.m)

                    if ( unitOffset.scalarProduct(pair.getLeft().getVelocity()) > 0 && !pair.getLeft().getStaticObject()){
                        double value = totalRestitution*(massDiff*AVelDouble+2*pair.getRight().getMass()*BVelDouble)/totalMass;
                        finalSpeedVector = AVel.projectOn(tangent).add(unitOffset.multiply(value));
                        pair.getLeft().setVelocity(finalSpeedVector);
                    }

                    if ( unitOffset.scalarProduct(pair.getRight().getVelocity()) < 0 && !pair.getRight().getStaticObject()){
                        double value = totalRestitution*(-massDiff*BVelDouble+2*pair.getLeft().getMass()*AVelDouble)/totalMass;
                        finalSpeedVector = BVel.projectOn(tangent).add(unitOffset.multiply(value));
                        pair.getRight().setVelocity(finalSpeedVector);
                    }

                    totalFriction = pair.getLeft().getFriction()*pair.getRight().getFriction();
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
                        collisionListener.handleCollision(pair, distance);
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

    public void registerCollisionListener(CollisionListener collisionListener){
        collisionListeners.add(collisionListener);
    }
}
