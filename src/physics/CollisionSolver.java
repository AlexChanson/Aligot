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
        double totalFriction;
        double totalRestitution;
        double impulse;

        Vector2D positionOffset;
        Vector2D correctionOffset;
        Vector2D unitOffset;
        Vector2D finalSpeedVector;
        Vector2D relativeSpeed; // B - A
        Vector2D AVel;
        Vector2D BVel;
        Vector2D tangent;

        for (int i = 0; i < iterrations; i++){
            for (Pair<RigidBody, RigidBody> pair: pairs){ // we are searching for all possible pairs of object colliding
                positionOffset = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
                distance = positionOffset.norm();
                collisionTreshold = pair.getLeft().getRadius() + pair.getRight().getRadius(); // the treshold is the sum of radiuses
                overlap = distance - collisionTreshold; // how much the objects have penetrated each other

                if ( distance <= collisionTreshold ){ // collision detected
                    unitOffset = positionOffset.getNormalized();
                    tangent = new Vector2D(unitOffset); // used to calculate tangent friction
                    tangent.rotate90(true);


                    totalRestitution = pair.getLeft().getRestitution()*pair.getRight().getRestitution();

                    AVel = pair.getLeft().getVelocity();
                    BVel = pair.getRight().getVelocity();
                    relativeSpeed = BVel.minus(AVel);
                    correctionOffset = unitOffset.multiply(overlap).multiply(correctionAmount*0.5);

                    if (relativeSpeed.scalarProduct(unitOffset) < 0){
                        // impulse calculation
                        impulse = (-(1+totalRestitution)*relativeSpeed.scalarProduct(unitOffset))/((1/pair.getLeft().getMass())+(1/pair.getRight().getMass()));
                        if ( !pair.getLeft().getStaticObject()){
                            finalSpeedVector = pair.getLeft().getVelocity().minus(unitOffset.multiply(impulse/pair.getLeft().getMass()));
                            pair.getLeft().setVelocity(finalSpeedVector);
                            pair.getLeft().applyForce(pair.getLeft().getAppliedForce().projectOn(unitOffset).getOpposite());
                        }

                        if ( !pair.getRight().getStaticObject()){
                            finalSpeedVector = pair.getRight().getVelocity().add(unitOffset.multiply(impulse/pair.getRight().getMass()));
                            pair.getRight().setVelocity(finalSpeedVector);
                            pair.getRight().applyForce(pair.getRight().getAppliedForce().projectOn(unitOffset));
                        }
                    }



                    totalFriction = pair.getLeft().getFriction()*pair.getRight().getFriction();
                    double scalar = pair.getRight().getAppliedForce().scalarProduct(tangent);

                    pair.getRight().applyForce(
                            unitOffset.multiply(
                                    -scalar).add(
                                            pair.getRight().getVelocity().getOpposite().multiply(totalFriction*pair.getRight().getMass())
                            )
                    );

                    scalar = pair.getLeft().getAppliedForce().scalarProduct(tangent);
                    pair.getLeft().applyForce(
                            unitOffset.multiply(
                                    -scalar).add(
                                        pair.getLeft().getVelocity().getOpposite().multiply(totalFriction*pair.getLeft().getMass())
                            )
                    );

                    if (!pair.getLeft().getStaticObject()){
                        pair.getLeft().setPosition(pair.getLeft().getPosition().add(correctionOffset.multiply(10/pair.getLeft().getMass())));
                    }
                    if (!pair.getRight().getStaticObject()) {
                        pair.getRight().setPosition(pair.getRight().getPosition().add(correctionOffset.getOpposite().multiply(10/pair.getRight().getMass())));
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
