package physics;

import core.GraphicsEngine;
import utility.Pair;

import java.util.ArrayList;

import static java.lang.Math.abs;
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

        double distance = 0;
        double collisionTreshold = 0;
        double overlap = 0;
        double totalFriction = 0;
        double totalRestitution = 0;
        double impulse = 0;

        Vector2D positionOffset;
        Vector2D correctionOffset;
        Vector2D unitOffset = Vector2D.getNull();
        Vector2D finalSpeedVector;
        Vector2D relativeSpeed = Vector2D.getNull(); // B - A
        Vector2D AVel;
        Vector2D BVel;
        Vector2D tangent = Vector2D.getNull();


        for (Pair<RigidBody, RigidBody> pair: pairs){ // we are searching for all possible pairs of object colliding
            positionOffset = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
            distance = positionOffset.norm();
            collisionTreshold = pair.getLeft().getRadius() + pair.getRight().getRadius(); // the treshold is the sum of radiuses
            overlap = distance - collisionTreshold; // how much the objects have penetrated each other

            if ( distance <= collisionTreshold ){ // collision detected
                positionOffset = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
                unitOffset = positionOffset.getNormalized();
                for (int i = 0; i < iterrations; i++){
                    positionOffset = pair.getRight().getPosition().minus(pair.getLeft().getPosition());
                    tangent = new Vector2D(unitOffset); // used to calculate tangent friction
                    tangent.rotate90(true);


                    totalRestitution = min(pair.getLeft().getRestitution(), pair.getRight().getRestitution());

                    AVel = pair.getLeft().getVelocity();
                    BVel = pair.getRight().getVelocity();
                    relativeSpeed = BVel.minus(AVel);
                    correctionOffset = unitOffset.multiply(overlap).multiply(correctionAmount*0.5);

                    if (!pair.getLeft().getStaticObject()){
                        pair.getLeft().setPosition(pair.getLeft().getPosition().add(
                                correctionOffset.multiply(min(1,10/pair.getLeft().getMass()))));
                    }
                    if (!pair.getRight().getStaticObject()) {
                        pair.getRight().setPosition(pair.getRight().getPosition().minus(
                                correctionOffset.multiply(min(1,10/pair.getRight().getMass()))));
                    }


                }
                double scalarNormalSpeed = relativeSpeed.scalarProduct(unitOffset);

                if (scalarNormalSpeed < 0){
                    // impulse calculation
                    impulse = (-(1+totalRestitution)*relativeSpeed.scalarProduct(unitOffset))/
                            ((1/pair.getLeft().getMass())+(1/pair.getRight().getMass()));
                    if ( !pair.getLeft().getStaticObject()){
                        finalSpeedVector = pair.getLeft().getVelocity().minus(unitOffset.multiply(impulse/pair.getLeft().getMass()));
                        pair.getLeft().setVelocity(finalSpeedVector);
                    }

                    if ( !pair.getRight().getStaticObject()){
                        finalSpeedVector = pair.getRight().getVelocity().add(unitOffset.multiply(impulse/pair.getRight().getMass()));
                        pair.getRight().setVelocity(finalSpeedVector);
                    }
                }

                totalFriction = (pair.getLeft().getFriction()*pair.getRight().getFriction());

                pair.getRight().setVelocity(pair.getRight().getVelocity().add(
                        pair.getRight().getVelocity().projectOn(tangent).multiply(totalFriction).getOpposite()));
                pair.getLeft().setVelocity(pair.getLeft().getVelocity().add(
                        pair.getLeft().getVelocity().projectOn(tangent).multiply(totalFriction).getOpposite()));

                for ( CollisionListener collisionListener : collisionListeners){
                    collisionListener.handleCollision(pair, distance);
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
