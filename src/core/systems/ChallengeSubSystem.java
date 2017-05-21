package core.systems;

import core.Event;
import core.model.Challenge;
import core.model.Projectile;
import physics.Vector2D;
import son.SoundPlayer;
import utility.Pair;
import java.util.ArrayList;

public class ChallengeSubSystem extends SubSystem{
    private ArrayList<double[]> targets;
    private int score;

    @Override
    public void initialize() {
        targets = new ArrayList<>();
        if(engine.getLevel().getChallenge() != null){
            targets = new ArrayList<>();
            targets.addAll(engine.getLevel().getChallenge().getTargets());
        }
    }

    @Override
    protected void processEvent(Event event) {
        if (event.type.equals("TICK")){
            ArrayList<Pair<Projectile, double[]>> toProcess = new ArrayList<>();
            for (double[] target :
                    targets) {
                Vector2D tpos = new Vector2D(target[0], target[1]);
                for (Projectile p :
                        engine.getProjectiles()) {
                    if (tpos.distanceTo(p.getRigidBody().getPosition()) < Challenge.TARGET_SIZE){
                        SoundPlayer.play("target");
                        toProcess.add(new Pair<>(p, target));
                    }
                }
            }
            toProcess.forEach(pair -> {
                score += 1;
                engine.getProjectiles().remove(pair.getLeft());
                engine.getPhysicsEngine().removeBody(pair.getLeft().getRigidBody());
                if (engine.getProjectiles().size() == 0)
                    engine.throwEvent(new Event("REMOVED_LAST_PROJECTILE"));
                targets.remove(pair.getRight());
                if (targets.size() == 0)
                    engine.throwEvent(new Event("LAST_TARGET_DOWN"));
                engine.getLevel().getChallenge().getTargets().remove(pair.getRight());
            });
        }
    }

    public int getScore() {
        return score;
    }
}
