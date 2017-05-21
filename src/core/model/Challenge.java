package core.model;

import java.util.HashSet;

public class Challenge{
    private int difficulty;
    public static int TARGET_SIZE = 10; //Diameter
    private int shots;
    private HashSet<double[]> targets;

    public Challenge(int difficulty, int shots, HashSet<double[]> targets) {
        this.difficulty = difficulty;
        this.shots = shots;
        this.targets = targets;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public HashSet<double[]> getTargets() {
        return targets;
    }

    public void setTargets(HashSet<double[]> targets) {
        this.targets = targets;
    }
}
