package utility;


import core.model.Challenge;
import core.model.Level;

import java.util.ArrayList;

public class Challenges {
    private static final ArrayList<Level> challenges;

    static {
        challenges = Loader.loadAll(Level.class, "challenges");
    }

    public static ArrayList<Level> get(){
            ArrayList<Level> temp = new ArrayList<>(challenges.size());
            challenges.forEach(challenge -> {
                temp.add(new Level(challenge.getName(), challenge.getInfo(), challenge.getBgTexture(), challenge.getPlanets(), challenge.getMapSize(), new Challenge(
                        challenge.getChallenge().getDifficulty(), challenge.getChallenge().getShots(), challenge.getChallenge().getTargets()
                )));
            });
            return temp;
    }
}
