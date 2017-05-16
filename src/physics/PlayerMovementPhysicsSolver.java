package physics;

import core.Player;

import java.util.ArrayList;

/**
 * Created by ben on 16/05/17.
 */
public class PlayerMovementPhysicsSolver extends PhysicSolver {
    private Player player;
    private Vector2D appliedForce;

    public PlayerMovementPhysicsSolver( Player player){
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Vector2D getAppliedForce() {
        return appliedForce;
    }

    public void setAppliedForce(Vector2D appliedForce) {
        this.appliedForce = appliedForce;
    }

    @Override
    public void compute(Simulator sim, double dt) {

        RigidBody body = player.getRigidBody();

        body.applyForce(this.appliedForce);
    }
}
